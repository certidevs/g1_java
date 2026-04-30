package com.demo.controller;

import com.demo.model.enums.Language;
import com.demo.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class SessionController {
    //Aqui agregar el getmapping y demas
    private final SessionRepository sessionRepository;

    //Estoy intentando traer todas las sesiones
    @GetMapping("sessions")
    public String sessions(Model model){
        model.addAttribute("sessions", sessionRepository.findAll());
        return "sessions/session-list";
    }
    //Estoy intentando traer las sessiones que tiene esa pelicula.
    @GetMapping("sessions/{id}")
    public String sessionDetail(@PathVariable Long id, Model model){
        model.addAttribute("session", sessionRepository.findByMovie_Id(id));
        return "sessions/session-detail";
    }
    //Estoy intentando traer las sesiones de las peliculas que tienen UN lenguaje especifico, por ejemplo VOSE
    @GetMapping("sessions/{Languege}")
    public String sessionsByLanguage(Model model, @PathVariable Language Languege){
        model.addAttribute("sessionsLanguaje", sessionRepository.findByLanguage(Languege));
        return "sessions/session-list";
    }
}
