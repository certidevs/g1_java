package com.demo.controller;

import com.demo.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@Controller
public class TicketController {
     // Inyectar TicketRepository
    private final TicketRepository ticketRepository;

    //GetMapping para tickets
    @GetMapping("tickets")
    public String tickets(Model model) {
        return "";
    }






}