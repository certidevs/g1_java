package com.demo.model;

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
    private Double price; // session.room.price + extra comida - descuento


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime purchaseTime;  // se asigna al momento de la compra y asignación del usuario

    // comida sin necesidad de crear una entidad nueva
    private Double priceCombo; // 5, 7, 10
//    private Double priceCombo1;
//    private Double priceCombo2;
//    private Double priceCombo3;

//    @Builder.Default
//    @Enumerated(EnumType.STRING)
//    private TicketType ticketType = TicketType.STANDARD;

   @ToString.Exclude
   @ManyToOne
    private Session session;

//    @Transient  // No se persiste en la base de datos
//    private Long sessionId;




}
