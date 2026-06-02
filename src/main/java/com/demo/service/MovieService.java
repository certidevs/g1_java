package com.demo.service;

import com.demo.model.Movie;
import com.demo.model.enums.MovieStatus;
import com.demo.model.enums.Section;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

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