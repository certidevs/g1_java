package com.demo.controller;

import com.demo.dto.PaymentForm;
import com.demo.model.Session;
import com.demo.model.Ticket;
import com.demo.model.User;
import com.demo.model.enums.Role;
import com.demo.model.enums.TicketStatus;
import com.demo.repository.*;
import com.demo.service.QrService;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Controller
public class TicketController {
     // Inyectar TicketRepository
    private final TicketRepository ticketRepository;
    private final TicketLineRepository ticketLineRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final QrService qrService;

    //GetMapping para ticket-list
    @GetMapping("tickets")
    public String ticketsList(
            Model model,
            @RequestParam(required = false) Long sessionId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) LocalDate purchaseDate,
            @AuthenticationPrincipal User user){
        // ADMIN
        if (user != null && user.getRole() == Role.ROLE_ADMIN) {

            LocalDateTime startOfDay = purchaseDate != null ? purchaseDate.atStartOfDay() : null;
            LocalDateTime endOfDay = purchaseDate != null ? purchaseDate.atTime(23, 59, 59) : null;

            List<Ticket> tickets = ticketRepository.filterTickets(sessionId, title, price, startOfDay, endOfDay);

            model.addAttribute("tickets", tickets); //usa la variable filtrada
            model.addAttribute("sessions", ticketRepository.findSessionsFromPurchasedTickets());
//            model.addAttribute("sessions", sessionRepository.findAll());

        } else if (user != null) {
            model.addAttribute("tickets",
                    ticketRepository.findByUser_IdOrderByPurchaseTime(user.getId()));
        } else {
            model.addAttribute("tickets", ticketRepository.findAll());
        }

        return "tickets/ticket-list";
    }


    // detail
    @GetMapping("tickets/{id}")
    public String ticketDetail(@PathVariable Long id, Model model) throws WriterException, IOException {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        model.addAttribute("ticket", ticket);

        // IVA del 10% para entradas de cine en España
        Double iva = ticket.getPrice() * 0.10;
        Double precioSinIva = ticket.getPrice() - iva;

        model.addAttribute("iva", iva);
        model.addAttribute("precioSinIva", precioSinIva);

        // Generamos el QR y lo mandamos a la vista
        String qrBase64 = qrService.generarQr(ticket.getId());
        model.addAttribute("qrCode", qrBase64);

        if (ticket.getUser() == null) {
            model.addAttribute("countUserTickets", 0);
            model.addAttribute("totalMoneyUserSpent", 0);
        } else {
            model.addAttribute("ticketLines", ticketLineRepository.findByTicket_Id(id));
            model.addAttribute("countUserTickets",
                    ticketRepository.countByUser_Id(ticket.getUser().getId()));
            model.addAttribute("totalMoneyUserSpent",
                    ticketRepository.calculateTotalMoneySpentByUserId(ticket.getUser().getId()));
        }

        List<Ticket> tickets = ticketRepository.findBySession_Id(ticket.getSession().getId());
        model.addAttribute("tickets", tickets);

        model.addAttribute("paymentForm", new PaymentForm());

        return "tickets/ticket-detail";
    }

    @GetMapping("tickets/deactivate/{id}")
    public String ticketDesactivate(@PathVariable Long id, Model model){
        Optional <Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()){
            Ticket ticket = ticketOptional.get();
            ticket.setActive(false);
            ticketRepository.save(ticket);
        }
        return "redirect:/tickets";
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
        model.addAttribute("users", userRepository.findAll());
        return "tickets/ticket-form";
    }

    // Get editTicket
    @GetMapping("tickets/edit/{id}")
    public String editTicket(@PathVariable Long id, Model model){
        model.addAttribute("ticket", ticketRepository.findById(id).orElseThrow());
       // model.addAttribute("language" , Language.values());
        model.addAttribute("projections", sessionRepository.findAll());
        model.addAttribute("users", userRepository.findAll());

        return "tickets/ticket-form";
    }

    // Get editTicket
    @GetMapping("tickets/buy/{id}")
    public String buyTicket(@PathVariable Long id, Model model){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        model.addAttribute("ticket", ticket);
        model.addAttribute("projections", sessionRepository.findAll());
        model.addAttribute("users", userRepository.findAll());

        // foodRepository.findAll
        return "tickets/ticket-form";
    }

    @GetMapping("tickets/qr-scan/{id}")
    public String scanQr(@PathVariable Long id, Model model) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();

        if (!ticket.getActive()) {
            model.addAttribute("mensaje", "❌ Ticket desactivado");
            return "tickets/qr-result";
        }

        if (ticket.getCurrentUses() >= ticket.getMaxUses()) {
            model.addAttribute("mensaje", "❌ Este ticket ya fue usado");
            return "tickets/qr-result";
        }

        ticket.setCurrentUses(ticket.getCurrentUses() + 1);
        ticketRepository.save(ticket);
        model.addAttribute("mensaje", "✅ Acceso permitido");
        return "tickets/qr-result";
    }

    // Post saveTicket
    @PostMapping("tickets")
    public String saveTicket(
            @ModelAttribute Ticket ticket,
            @AuthenticationPrincipal User user) {

        // usuario comprador
        if (user != null && user.getRole() != Role.ROLE_ADMIN) {
            ticket.setUser(user);
        }
//        ticket.setPurchaseTime(LocalDateTime.now());
        // RECARGAR SESSION COMPLETA DESDE LA BD
        Long sessionId = ticket.getSession().getId();

        Session session = sessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        // asignar session completa al ticket
        ticket.setSession(session);
        // calcular precio
        Double precioBase = session.getRoom().getPrice();

        Double precioComida =
                ticket.getPriceCombo() != null ? ticket.getPriceCombo() : 0.0;
        Double precioTotal = precioBase + precioComida;
        ticket.setPrice(precioTotal);
        ticketRepository.save(ticket);

        return "redirect:/tickets/" + ticket.getId();
    }


    // @PostMapping
    @PostMapping("tickets/{id}/finish")
    public String finish(@PathVariable Long id
                         ,@ModelAttribute PaymentForm paymentForm
    ) {
        Ticket ticket =  ticketRepository.findById(id).orElseThrow();


        ticket.setStatus(TicketStatus.FINISHED);
        ticket.setCardOwner(paymentForm.getCardOwner());
        ticket.setCardNumber(paymentForm.getCardNumber());
        ticket.setCardExpirationDate(paymentForm.getCardExpirationDate());
        ticket.setCardCode(paymentForm.getCardCode());

        // finalizar compra
        ticket.setPurchaseTime(LocalDateTime.now());

        ticketRepository.save(ticket);
        return "redirect:/tickets/" + id;
    }



}