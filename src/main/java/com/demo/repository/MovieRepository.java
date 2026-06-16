package com.demo.repository;

import com.demo.dto.MovieStatsDTO;
import com.demo.model.Director;
import com.demo.model.Movie;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.model.enums.MovieStatus;
import com.demo.model.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreSet(Set<Genre> genres);

    List<Movie> findByDirector(Director director);

    List<Movie> findByMinAge(MinAge minAge);

    List<Movie> findBySection(Section section);

    List<Movie> findByReleaseDateBetweenAndActiveTrue(LocalDate start, LocalDate end);

    @Query("""
        SELECT m from Movie m
        left join m.director d
        WHERE m.active =true
        AND (:directorName IS NULL OR d.name = :directorName)
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


    // con DTO
//    @Query("""
//        SELECT new com.demo.dto.MovieStatsDTO(m, count(distinct r), avg(r.rating), count( distinct t))
//        from Movie m
//        left join Review r on r.movie = m
//        left join Ticket t on t.session.movie = m and t.purchaseTime is not null
//        group by m
//    """)
//    List<MovieStatsDTO> findActiveFilteringWithStatsDTO();

    @Query("""
        SELECT new com.demo.dto.MovieStatsDTO(
            m,
            (select count(r) from Review r where r.movie = m), 
            (select avg(r.rating) from Review r where r.movie = m),
            (select count(t) from Ticket t where t.session.movie = m and t.purchaseTime is not null),
            (select coalesce(sum(t.price),0) from Ticket t where t.session.movie = m and t.purchaseTime is not null)
            )
        from Movie m
    """)
    List<MovieStatsDTO> findActiveFilteringWithStatsDTOSubselect();

}