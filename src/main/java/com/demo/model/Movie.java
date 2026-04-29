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
@Builder
@ToString
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Director director = Director.UNKNOWN;

    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    private MinAge minAge = MinAge.ALL;

    private Integer duration;
    private String trailerUrl;
    private String imageUrl;

    @Column (length = 500)
    private String sinopsis;

    @ToString.Exclude
    @ManyToOne
    private Session session;

}
