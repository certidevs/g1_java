package com.demo.controller;

import com.demo.model.Ticket;
import com.demo.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


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

        // Filtrar por id y cargar en el modelo
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        //Comprobamos si existe
        if(ticketOptional.isPresent()){

            Ticket ticket = ticketOptional.get();
            model.addAttribute("ticket", ticket);

            return "tickets/ticket-detail";
        }

       // model.addAttribute("tickets", ticketRepository.findAll());
        // En caso de que no exista lo envia a movie
        return "redirect:/movies";
    }






}