package com.demo.repository;

import com.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Para buscar tickets vendidos por pelicula
    List<Ticket> findBySession_Movie_Id(Long id);

    // Para buscar tickets vendidos por fecha y hora
    List<Ticket> findByPurchaseTime(LocalDateTime purchaseTime);

//    @Query("""
//        SELECT SUM(ol.quantity * ol.ticket.price)
//        FROM Room ol where ol.ticket.id = ?1
//       """)
//    Double calculateTotalPrice(Long ticketId);



}