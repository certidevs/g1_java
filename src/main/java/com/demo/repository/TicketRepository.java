package com.demo.repository;

import com.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Para buscar tickets vendidos por pelicula
    List<Ticket> findBySession_Movie_Id(Long id);

    // Para buscar tickets vendidos por fecha y hora
    List<Ticket> findByPurchaseTime(LocalDateTime purchaseTime);


   // Query con filtros
    @Query("""
    SELECT t
    FROM Ticket t
    WHERE (:sessionId IS NULL OR t.session.id = :sessionId)
    AND (:title IS NULL OR LOWER(t.session.movie.title)
         LIKE LOWER(CONCAT('%', :title, '%')))
    AND (:price IS NULL OR t.price <= :price)
""")
    List<Ticket> filterTickets(
            @Param("sessionId") Long sessionId,
            @Param("title") String title,
            @Param("price") Double price
    );


}