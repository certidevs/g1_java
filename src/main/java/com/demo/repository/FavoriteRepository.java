package com.demo.repository;

import com.demo.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_IdAndMovieIsNotNull(Long id);

    Optional<Favorite> findByUser_IdAndMovieId(Long userId, Long movieId);

    // verificar si una película ya es favorito y así decidir mostrarlo diferente en la UI
    @Query("""
    select f.movie.id from Favorite f
        where f.user.id = :userId
        and f.movie IS NOT NULL
    """)
    Set<Long> findMovieIdsByUserId(@Param("userId") Long userId);

}