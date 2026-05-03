package com.demo.controller;

import com.demo.model.Session;
import com.demo.model.enums.Language;
import com.demo.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class SessionController {
    //Aqui inyectar los repository que se necesitaran
    private final SessionRepository sessionRepository;
    private final MovieController movieController;
    private final RoomController roomController;

    //[OK]Todas las sesiones
    //Este es el CONTROLADOR
    @GetMapping("sessions")
    public String sessions(Model model){
        //Cargamos los datos en el MODELO
        List<Session> cinemaFuntions = sessionRepository.findAll();
        model.addAttribute("cinemaFunctions", cinemaFuntions);
        //Retornamos a la VISTA
        return "sessions/session-list";
    }

    //El detalle de la session
    //Las sesiones que tienen UN lenguaje especifico, por ejemplo VOSE
    //Las sesiones que tengan UN tipo de pantalla por ejemplo 3D



}
