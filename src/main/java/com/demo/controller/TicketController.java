package com.demo.controller;

import com.demo.model.Ticket;
import com.demo.model.enums.Language;
import com.demo.model.enums.TicketType;
import com.demo.repository.MovieRepository;
import com.demo.repository.SessionRepository;
import com.demo.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Controller
public class TicketController {
     // Inyectar TicketRepository
    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;

    //GetMapping para ticket-list
    @GetMapping("tickets")
    public String ticketsList(Model model){
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
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
        // En caso de que no exista lo envía a session
        return "redirect:/sessions";
    }

    // Get newTicket
    @GetMapping("tickets/new")
    public String newTicket(Model model){
        // añadir objeto product vacio para rellenarlo desde el formulario
        model.addAttribute("ticket", new Ticket());
        // datos para el ticketTypes
        model.addAttribute("ticketTypes", TicketType.values());
        //model.addAttribute("language" , Language.values());
        model.addAttribute("projections", sessionRepository.findAll());
        return "tickets/ticket-form";
    }
    // Get editTicket
    @GetMapping("tickets/edit/{id}")
    public String editTicket(@PathVariable Long id, Model model){
        model.addAttribute("ticket", ticketRepository.findById(id).orElseThrow());
        model.addAttribute("ticketTypes", TicketType.values());
       // model.addAttribute("language" , Language.values());
        model.addAttribute("projections", sessionRepository.findAll());
        return "tickets/ticket-form";
    }
    // Post saveTicket
    @PostMapping("tickets")
    public String saveTicket(@ModelAttribute Ticket ticket){
        ticketRepository.save(ticket);
        return "redirect:/tickets/" + ticket.getId();
    }






}