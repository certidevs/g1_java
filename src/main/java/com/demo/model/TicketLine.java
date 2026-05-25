package com.demo.model;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "ticket_lines")
public class TicketLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ToString.Exclude
    @ManyToOne
    private Ticket ticket;

    /*
    TODO si se quiere comprar varios accesos en el mismo ticket con el mismo QR
    private String seatRow;
    private Integer seatNumber;
     */
    // TODO
//    @ToString.Exclude
//    @ManyToOne
//    private Food food;




}
