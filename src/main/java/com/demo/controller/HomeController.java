package com.demo.controller;

import com.demo.model.Movie;
import com.demo.model.Session;
import com.demo.model.enums.ScreenType;
import com.demo.model.enums.Section;
import com.demo.repository.MovieRepository;
import com.demo.repository.RoomRepository;
import com.demo.repository.SessionRepository;
import com.demo.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;

    // Constructor explícito sin Lombok
    public HomeController(MovieRepository movieRepository, SessionRepository sessionRepository,
                          RoomRepository roomRepository, TicketRepository ticketRepository) {
        this.movieRepository = movieRepository;
        this.sessionRepository = sessionRepository;
        this.roomRepository = roomRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        System.out.println("HomeController.index() llamado");

        List<Movie> moviesAll = movieRepository.findActiveFiltering(
                null, null, null, null, null, Section.BILLBOARD
        );

        if (moviesAll == null) {
            moviesAll = List.of();
        }

        Collections.shuffle(moviesAll);
        List<Movie> moviesRandom4 = moviesAll.stream().limit(4).collect(Collectors.toList());
        model.addAttribute("movies", moviesRandom4);

        LocalDateTime now = LocalDateTime.now();
        List<Session> upcoming = sessionRepository.findAll().stream()
                .filter(s -> Boolean.TRUE.equals(s.getActive()))
                .filter(s -> s.getStartTime() != null && s.getStartTime().isAfter(now))
                .sorted(Comparator.comparing(Session::getStartTime))
                .limit(6)
                .collect(Collectors.toList());

        model.addAttribute("projections", upcoming);

        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = now.toLocalDate().atTime(LocalTime.MAX);
        List<Session> todaySessions = sessionRepository.findByStartTimeBetweenAndActiveTrue(startOfDay, endOfDay);
        model.addAttribute("todaySessionsCount", todaySessions.size());

        LocalDate today = LocalDate.now();
        List<Movie> weekReleases = movieRepository.findByReleaseDateBetweenAndActiveTrue(
                today.minusWeeks(1), today
        );
        model.addAttribute("weekReleasesCount", weekReleases.size());

        Long vipRooms = roomRepository.countByScreentypeAndActiveTrue(ScreenType.VIP);
        model.addAttribute("vipRoomsCount", vipRooms);

//        List<Session> featured = sessionRepository.findAll().stream()
//                .filter(s -> Boolean.TRUE.equals(s.getActive()))
//                .filter(s -> s.getStartTime() != null)
//                .filter(s -> s.getStartTime().isAfter(now) && s.getStartTime().isBefore(now.plusHours(2)))
//                .filter(s -> {
//                    long sold = ticketRepository.findBySession_Id(s.getId()).stream()
//                            .filter(t -> t.getPurchaseTime() != null)
//                            .count();
//                    return sold < s.getRoom().getCapacity() * 0.5;
//                })
//                .sorted(Comparator.comparing(Session::getStartTime))
//                .limit(1)
//                .collect(Collectors.toList());
//
//        if (!featured.isEmpty()) {
//            Session featuredSession = featured.get(0);
//            model.addAttribute("featuredSession", featuredSession);
//            long soldTickets = ticketRepository.findBySession_Id(featuredSession.getId()).stream()
//                    .filter(t -> t.getPurchaseTime() != null)
//                    .count();
//            model.addAttribute("availableSeats", featuredSession.getRoom().getCapacity() - soldTickets);
//        } else {
//            model.addAttribute("featuredSession", null);
//        }

        List<Session> sessions = sessionRepository.findAll();

        Session featuredSession = null;

        for (Session session : sessions) {

            if (!Boolean.TRUE.equals(session.getActive())) {
                continue;
            }

            if (session.getStartTime() == null) {
                continue;
            }

            if (session.getStartTime().isBefore(now)
                    || session.getStartTime().isAfter(now.plusHours(2))) {
                continue;
            }

            long soldTickets =
                    ticketRepository.countBySession_IdAndPurchaseTimeIsNotNull(session.getId());

            if (soldTickets >= session.getRoom().getCapacity() * 0.5) {
                continue;
            }

            if (featuredSession == null
                    || session.getStartTime().isBefore(featuredSession.getStartTime())) {

                featuredSession = session;
            }
        }

        model.addAttribute("featuredSession", featuredSession);

        if (featuredSession != null) {

            long soldTickets =
                    ticketRepository.countBySession_IdAndPurchaseTimeIsNotNull(featuredSession.getId());

            long availableSeats =
                    featuredSession.getRoom().getCapacity() - soldTickets;

            model.addAttribute("availableSeats", availableSeats);
        }

        return "index";
    }
}
