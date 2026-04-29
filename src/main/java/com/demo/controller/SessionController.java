package com.demo.controller;

import com.demo.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SessionController {
    //Aqui agregar el getmapping y demas
    private final SessionRepository sessionRepository;

    @GetMapping("sessions")
    public String sessionsList(Model model){
        return "";
    }

}
