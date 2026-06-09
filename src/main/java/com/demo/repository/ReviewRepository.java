package com.demo.repository;

import com.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie_Title(String title);

    List<Review> findByMovie_IdOrderByCreationDateDesc(Long movieId);

    long countByUser_Id(Long id);
    List<Review> findByUser_Id(Long id);

    @Query("""
        select AVG(r.rating) from Review r WHERE
            r.rating is not null and
            r.movie is not null and
            r.movie.id = :movieId
    """)
    Double calculateAverageRatingByMovieId(@Param("movieId") Long movieId);
    long countByMovie_Id(Long id);
}