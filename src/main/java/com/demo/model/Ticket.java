package com.demo.model;

import com.demo.model.enums.TicketType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatRow;
    private Integer seatNumber;
    private Double price;
    //private LocalDateTime sessionTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Builder.Default
    private LocalDateTime purchaseTime = LocalDateTime.now();

//    @DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME)
//    private LocalDateTime creationDate = LocalDateTime.now();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TicketType ticketType = TicketType.STANDARD;

   @ToString.Exclude
   @ManyToOne
    private Session session;

//    @Transient  // No se persiste en la base de datos
//    private Long sessionId;




}
