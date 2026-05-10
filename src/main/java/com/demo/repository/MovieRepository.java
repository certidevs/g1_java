package com.demo.repository;

import com.demo.model.Movie;
import com.demo.model.enums.Director;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreSet(Set<Genre> genres);

    List<Movie> findByDirector(Director director);

    List<Movie> findByMinAge(MinAge minAge);

    @Query("""
        SELECT m from Movie m
        WHERE m.active =true
        AND (:director IS NULL OR m.director = :director)
        AND (:genre IS NULL OR :genre MEMBER OF m.genreSet)
        AND (:title IS NULL OR :title = '' OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:minAge IS NULL OR m.minAge <= :minAge)
        AND (:releaseYear IS NULL OR m.releaseYear = :releaseYear)
    """)
    List<Movie> findActiveFiltering(
            @Param("director") Director director,
            @Param("genre") Genre genre,
            @Param("title") String title,
            @Param("minAge") MinAge minAge,
            @Param("releaseYear") Integer releaseYear
    );
}