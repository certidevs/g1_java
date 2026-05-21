package com.demo.repository;

import com.demo.model.TicketLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketLineRepository extends JpaRepository<TicketLine, Long> {

    List<TicketLine> findByTicket_Id(Long id);

    Optional<TicketLine> findByTicket_IdAndId(Long ticketId, Long lineId);

    @Query("""
        SELECT SUM(tl.quantity * tl.ticket.price)
        FROM TicketLine tl where tl.ticket.id = ?1
       """)
    Double calculateTotalPrice(Long ticketId);

}