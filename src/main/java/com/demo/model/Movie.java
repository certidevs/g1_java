package com.demo.model;

import com.demo.model.enums.Director;
import com.demo.model.enums.MinAge;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Director director = Director.UNKNOWN;

    private Integer year; // LocalDate.now().getYear();

    @Enumerated(EnumType.STRING)
    private MinAge minAge = MinAge.ALL;

    private Integer duration; // Duration.ofMinutes();
    private String trailerUrl;
    private String imageUrl;
}
