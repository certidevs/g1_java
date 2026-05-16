package com.demo.controller;

import com.demo.model.Session;
import com.demo.model.Ticket;
import com.demo.repository.MovieRepository;
import com.demo.repository.SessionRepository;
import com.demo.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public String ticketsList(
            Model model,
            @RequestParam(required = false) Long sessionId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double price){

        List<Ticket> tickets =
                ticketRepository.filterTickets(sessionId, title, price);

        model.addAttribute("tickets", tickets);
        model.addAttribute("sessions", sessionRepository.findAll()); // Agregar sessiones
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
    // TODO RequestParam sessionId
    @GetMapping("tickets/new")
    public String newTicket(Model model, @RequestParam (required = false) Long sessionId){
        // añadir objeto producto vacio para rellenarlo desde el formulario
        Ticket ticket = Ticket.builder().build();
        if(sessionId != null){
            Session session = sessionRepository.findById(sessionId).orElseThrow();
            ticket.setSession(session);
            ticket.setPrice(session.getRoom().getPrice());
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("projections", sessionRepository.findAll());
        return "tickets/ticket-form";
    }
    // Get editTicket
    @GetMapping("tickets/edit/{id}")
    public String editTicket(@PathVariable Long id, Model model){
        model.addAttribute("ticket", ticketRepository.findById(id).orElseThrow());
       // model.addAttribute("language" , Language.values());
        model.addAttribute("projections", sessionRepository.findAll());
        return "tickets/ticket-form";
    }
    // Post saveTicket
    // El usuario compra el ticket
    @PostMapping("tickets")
    public String saveTicket(
            @ModelAttribute Ticket ticket){
        ticket.setPurchaseTime(LocalDateTime.now());
        // TODO recalculate total price
        // TODO no hace falta guardarlo en base de datos pero sí mostrarlo al usuario en el HTML para que vea el total
       Double precioBase = ticket.getSession().getRoom().getPrice();
       Double precioComida = ticket.getPriceCombo() != null ? ticket.getPriceCombo() : 0.0;
       Double precioTotal = precioBase + precioComida;
       ticket.setPrice(precioTotal);
       ticketRepository.save(ticket);
        return "redirect:/tickets/" + ticket.getId();
    }






}