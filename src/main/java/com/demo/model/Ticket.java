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
    private Double price; // session.room.price  + extra comida - descuento
    //private LocalDateTime session-Time;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime purchaseTime; // se asigna en el momento de la compra y asignacion de usuario

    // comida sin necesidad de crear entidad:
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
