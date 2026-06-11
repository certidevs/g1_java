package com.demo.controller.api;



import com.demo.model.Movie;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

// http://localhost:8080/swagger-ui/index.html
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
public class MovieRestController {

    private MovieRepository movieRepository;

    @GetMapping
    List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @GetMapping("{id}")
    public Movie findOne(@PathVariable Long id) {
        return movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie " + id + " not found")
        );
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        if (movie.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie ID must be null");
        }
        Movie saved = movieRepository.save(movie);
        //        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        return ResponseEntity.created(URI.create("/api/v1/movies/" + saved.getId())).body(saved);
    }

    // actualizar movie
    @PutMapping("{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody Movie movie) {
        Movie existing = movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie " + id + " not found")
        );
        existing.setTitle(movie.getTitle());
        existing.setReleaseDate(movie.getReleaseDate());
        existing.setDirector(movie.getDirector());
        existing.setSection(movie.getSection());
        existing.setImageUrl(movie.getImageUrl());
        existing.setActive(movie.getActive());
        existing.setGenreSet(movie.getGenreSet());
        existing.setTrailerUrl(movie.getTrailerUrl());
        // como alternativa se podría usar DTOs y mappers
        // existing.setStartDate(restaurant.getStartDate()); // conservar fecha original

        return ResponseEntity.ok(movieRepository.save(existing));
    }
}