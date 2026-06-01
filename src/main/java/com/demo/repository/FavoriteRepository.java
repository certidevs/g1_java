package com.demo.repository;

import com.demo.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_IdAndMovieIsNotNull(Long id);

    Optional<Favorite> findByUser_IdAndMovieId(Long userId, Long movieId);



}