package com.demo.service;

import com.demo.model.Favorite;
import com.demo.model.Movie;
import com.demo.repository.FavoriteRepository;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import com.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;


    public List<Favorite> findFavoriteMovies(Long userId) {

        return favoriteRepository.findByUser_IdAndMovieIsNotNull(userId);
    }

    public boolean toggleMovie(User user, Long movieId) {

        Optional<Favorite> existing = favoriteRepository
                .findByUser_IdAndMovieId(user.getId(), movieId);

        // Opción 1: es que ya existe y lo quitamos de favorito:
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return false;
        }

        // Opción 2: no existe y lo creamos como favorito
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        favoriteRepository.save(Favorite.builder().movie(movie).user(user).build());
        return true;
    }



}
