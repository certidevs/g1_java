package com.demo.model;

import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.model.enums.MovieStatus;
import com.demo.model.enums.Section;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<Genre> genreSet = EnumSet.noneOf(Genre.class);

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MinAge minAge = MinAge.ALL;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus = MovieStatus.COMING_SOON;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Section section = Section.BILLBOARD;

    private Integer duration;
    private String trailerUrl;
    private String imageUrl;

    @Column (length = 500)
    private String sinopsis;

    @ManyToOne
    @JoinColumn(name = "directorId")
    private Director director;

    @Transient
    private Double avgRating;

    @Transient
    private Long countReviews;
}
