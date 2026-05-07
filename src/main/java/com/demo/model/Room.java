package com.demo.model;

import com.demo.model.enums.ScreenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer capacity; // número de butacas

    // private String screenType; // tipo de pantalla o formato de proyección

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ScreenType screentype = ScreenType.STANDARD;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;
}


