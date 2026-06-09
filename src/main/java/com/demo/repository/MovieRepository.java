package com.demo.repository;

import com.demo.model.Director;
import com.demo.model.Movie;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.model.enums.MovieStatus;
import com.demo.model.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreSet(Set<Genre> genres);

    List<Movie> findByDirector(Director director);

    List<Movie> findByMinAge(MinAge minAge);

    List<Movie> findBySection(Section section);

    @Query("""
        SELECT m from Movie m
        WHERE m.active =true
        AND (:directorName IS NULL OR m.director.name = :directorName)
        AND (:genre IS NULL OR :genre MEMBER OF m.genreSet)
        AND (:title IS NULL OR :title = '' OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:minAge IS NULL OR m.minAge = :minAge)
        AND (:movieStatus IS NULL OR m.movieStatus = :movieStatus)
        AND (:section IS NULL OR m.section = :section)
    """)
    List<Movie> findActiveFiltering(
            @Param("directorName") String directorName,
            @Param("genre") Genre genre,
            @Param("title") String title,
            @Param("minAge") MinAge minAge,
            @Param("movieStatus") MovieStatus movieStatus,
            @Param("section") Section section
    );

    @Query("""
        SELECT m from Movie m
        WHERE m.active = false
        AND (:section IS NULL OR m.section = :section)
    """)
    List<Movie> findInactiveFiltering(
            @Param("section") Section section
    );
}