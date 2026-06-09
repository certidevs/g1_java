package com.demo.repository;

import com.demo.model.Session;
import com.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Para admin: buscar tickets vendidos por fecha y hora
    List<Ticket> findByPurchaseTime(LocalDateTime purchaseTime);

    List<Ticket> findBySession_Id(Long sessionId);
    List<Ticket> findByUser_IdOrderByPurchaseTime(Long id);

    Long countByUser_Id(Long id);
    Long countBySession_IdAndPurchaseTimeIsNotNull(Long sessionId);



    @Query("""
      SELECT COALESCE(SUM(o.price), 0.0) FROM Ticket o 
      WHERE o.user.id = :userId
""")
    double calculateTotalMoneySpentByUserId(Long userId);

    @Query("SELECT DISTINCT t.session FROM Ticket t WHERE t.status = 'FINISHED'")
    List<Session> findSessionsFromPurchasedTickets();

    @Query("SELECT DISTINCT t.session FROM Ticket t WHERE t.user.id = :userId AND t.purchaseTime IS NOT NULL")
    List<Session> findSessionsFromPurchasedTicketsByUser(@Param("userId") Long userId);

   // Query con filtros
    @Query("""
    SELECT t
    FROM Ticket t
    WHERE t.active = true
    AND (:sessionId IS NULL OR t.session.id = :sessionId)
    AND (:sessionId IS NULL OR t.status = com.demo.model.enums.TicketStatus.FINISHED)
    AND (:title IS NULL OR LOWER(t.session.movie.title)
         LIKE LOWER(CONCAT('%', :title, '%')))
    AND (:price IS NULL OR t.price <= :price)
    AND (:startOfDay IS NULL OR t.purchaseTime >= :startOfDay)
    AND (:endOfDay IS NULL OR t.purchaseTime <= :endOfDay)
""")
    List<Ticket> filterTickets(
            @Param("sessionId") Long sessionId,
            @Param("title") String title,
            @Param("price") Double price,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("""
    SELECT t
    FROM Ticket t
    WHERE t.user.id = :userId
    AND (:sessionId IS NULL OR t.session.id = :sessionId)
    AND (:title IS NULL OR LOWER(t.session.movie.title)
         LIKE LOWER(CONCAT('%', :title, '%')))
    AND (:price IS NULL OR t.price <= :price)
    AND (:startOfDay IS NULL OR t.purchaseTime >= :startOfDay)
    AND (:endOfDay IS NULL OR t.purchaseTime <= :endOfDay)
    ORDER BY t.purchaseTime DESC
""")

    List<Ticket> filterTicketsByUser(
            @Param("userId") Long userId,
            @Param("sessionId") Long sessionId,
            @Param("title") String title,
            @Param("price") Double price,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );




}