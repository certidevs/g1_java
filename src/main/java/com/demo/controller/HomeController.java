package com.demo.controller;

import com.demo.model.Movie;
import com.demo.model.Session;
import com.demo.model.enums.Section;
import com.demo.repository.MovieRepository;
import com.demo.repository.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;

    // Constructor explícito sin Lombok
    public HomeController(MovieRepository movieRepository, SessionRepository sessionRepository) {
        this.movieRepository = movieRepository;
        this.sessionRepository = sessionRepository;
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

        model.addAttribute("sessions", upcoming);

        return "index";
    }
}
