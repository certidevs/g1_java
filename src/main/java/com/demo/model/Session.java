package com.demo.model;

import com.demo.model.enums.Language;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
   //private Double price; // 5 euros
    private Integer numAds;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Language language = Language.ESP;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
}
