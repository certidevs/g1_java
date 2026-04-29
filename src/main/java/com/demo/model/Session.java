package com.demo.model;

import com.demo.model.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private Double price;
    private Integer numAds;

    @Enumerated(EnumType.STRING)
    private Language language = Language.ESP;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Movie movie;
}
