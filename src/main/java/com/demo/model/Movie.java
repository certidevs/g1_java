package com.demo.model;

import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.model.enums.MovieStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

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

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<Genre> genreSet = EnumSet.noneOf(Genre.class);

    private LocalDate releaseDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MinAge minAge = MinAge.ALL;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus = MovieStatus.NOW_SHOWING;

    private Integer duration;
    private String trailerUrl;
    private String imageUrl;

    @Column (length = 500)
    private String sinopsis;

    @ManyToOne
    @JoinColumn(name = "directorId")
    private com.demo.model.Director director;
}
