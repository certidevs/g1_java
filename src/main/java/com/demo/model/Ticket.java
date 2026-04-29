package com.demo.model;

import com.demo.model.enums.TicketType;
import jakarta.persistence.*;
import lombok.*;

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

    @Builder.Default
    private LocalDateTime purchaseTime = LocalDateTime.now();


    @Enumerated(EnumType.STRING)
    private TicketType ticketType = TicketType.STANDARD;

   @ToString.Exclude
   @ManyToOne
    private Session session;





}
