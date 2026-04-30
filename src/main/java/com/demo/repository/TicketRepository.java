package com.demo.repository;

import com.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Para buscar tickets vendidos por pelicula
    List<Ticket> findBySession_Movie_Id(Long id);

    // Para buscar tickets vendidos por fecha y hora
    List<Ticket> findByPurchaseTime(LocalDateTime purchaseTime);





}