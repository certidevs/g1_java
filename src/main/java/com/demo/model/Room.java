package com.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer capacity; // número de butacas

    private String screenType; // tipo de pantalla o formato de proyección
}


