package com.demo.controller.api;



import com.demo.model.Movie;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
