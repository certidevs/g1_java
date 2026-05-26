package com.demo.service;

import com.demo.model.Director;
import com.demo.model.Movie;
import com.demo.model.enums.MovieStatus;
import com.demo.model.enums.Section;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Movie> movieSection(Section section) {
        return movieRepository.findBySection(section);
    }

    public Movie updateStatus(Movie movie, MovieStatus newStatus) {
        movie.setMovieStatus(newStatus);

        if (newStatus == MovieStatus.IN_VOTING || newStatus == MovieStatus.VOTED) {
            movie.setSection(Section.FLUX);
        } else {
            movie.setSection(Section.BILLBOARD);
        }

        return movieRepository.save(movie);
    }
}