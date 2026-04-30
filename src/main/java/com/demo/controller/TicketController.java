package com.demo.controller;

import com.demo.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@AllArgsConstructor
@Controller
public class TicketController {
     // Inyectar TicketRepository
    private final TicketRepository ticketRepository;

    //GetMapping para ticket-list
    @GetMapping("tickets")
    public String tickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "tickets/ticket-list";
    }

    // detail
    @GetMapping("tickets/{id}")
    public String ticketDetail(@PathVariable Long id, Model model){

        model.addAttribute("tickets", ticketRepository.findAll());
        return "tickets/ticket-detail";
    }






}