package com.demo.service;

import com.demo.model.Favorite;
import com.demo.repository.FavoriteRepository;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;


    public List<Favorite> findFavoriteMovies(Long userId) {
        return favoriteRepository.findByUser_IdAndMovieIsNotNull(userId);
    }


}
