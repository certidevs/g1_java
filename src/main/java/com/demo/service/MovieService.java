package com.demo.service;

import com.demo.model.Director;
import com.demo.model.Movie;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final DirectorService directorService;

    public Movie createMovie(Movie movie, String directorName) {
        Director director = directorService.findOrCreate(directorName);
        movie.setDirector(director);
        return movieRepository.save(movie);
    }
}