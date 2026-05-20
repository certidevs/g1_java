package com.demo.controller;

import com.demo.model.Movie;
import com.demo.model.Room;
import com.demo.model.Session;
import com.demo.model.Ticket;
import com.demo.model.enums.Language;
import com.demo.model.enums.ScreenType;
import com.demo.repository.MovieRepository;
import com.demo.repository.RoomRepository;
import com.demo.repository.SessionRepository;
import com.demo.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class SessionController {
    //Aqui inyectar los repository que se necesitaran
    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;

    //[OK]Todas las sesiones
    //Este es el CONTROLADOR
    @GetMapping("sessions")
    public String sessions(
            Model model,
            @RequestParam(required = false) Language language,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) ScreenType screentype
    ) {
        List<Session> cinemaFunctions = sessionRepository.findActiveFiltering(language, title, screentype);
        model.addAttribute("cinemaFunctions", cinemaFunctions);
        model.addAttribute("numSessions", cinemaFunctions.size());
        model.addAttribute("title", "Lista de funciones");
        return "sessions/session-list";
    }
//Necesitamos cargar los tickets
    @GetMapping("sessions/{id}")
    public String sessionDetail(@PathVariable Long id, Model model){

        Optional <Session> sessionOptional = sessionRepository.findById(id);
        if (sessionOptional.isPresent()){
            Session session = sessionOptional.get();
            model.addAttribute("proyeccion", session);
            // TODO
            model.addAttribute("tickets", ticketRepository.findBySession_Id(id));
            return "sessions/session-detail";
        }
        else {
            return "redirect:/sessions";
        }
    }
    //Deberiamos crearle un atributo active en la Entity
    @GetMapping("sessions/deactivate/{id}")
    public String sessionDeactivate(@PathVariable Long id, Model model){
        Optional <Session> sessionOptional = sessionRepository.findById(id);
        if (sessionOptional.isPresent()){
            Session session = sessionOptional.get();
            session.setActive(false);
            sessionRepository.save(session);
        }
        return "redirect:/sessions";
    }

    //El detalle de la session
    //Las sesiones que tienen UN lenguaje especifico, por ejemplo VOSE
    //Las sesiones que tengan UN tipo de pantalla por ejemplo 3D

    //REEMPLAZAR SESSION
    @GetMapping("sessions/edit/{id}")
    public String editSession(@PathVariable Long id, Model model){
        model.addAttribute("proyeccion", sessionRepository.findById(id).orElseThrow());
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("languages", Language.values());
        return"sessions/session-form";
    }
    //REEMPLAZAR SESSION
    @GetMapping("sessions/new")
    public String createSessions(Model model){
        model.addAttribute("proyeccion", new Session());
        model.addAttribute("languages", Language.values());
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
    return "sessions/session-form";
    }
    //NO USAR SESSION
    @PostMapping("sessions")
    public String saveSession(@ModelAttribute Session session){
        sessionRepository.save(session);

        int cols = 10;
        int rows = session.getRoom().getCapacity() / 10;
        for (int i = 0; i < rows; i++){
            String row = String.valueOf((char) ('A' + i));
            for(int j = 1; j <= cols; j++){
                ticketRepository.save(Ticket.builder()
                        .seatRow(row)
                        .seatNumber(j)
                        .price(session.getRoom().getPrice())
                        .session(session).build());
            }
        }
        return "redirect:/sessions/" + session.getId();
    }


}
