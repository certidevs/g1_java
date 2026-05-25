package com.demo.model;

import com.demo.service.UserService;
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

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;

   @ToString.Exclude
   @ManyToOne
    private Session session;

//    @Transient  // No se persiste en la base de datos
//    private Long sessionId;


    @ToString.Exclude
    @ManyToOne
    private User user;


    // por defecto false
    // private Boolean qrScanned;

}
