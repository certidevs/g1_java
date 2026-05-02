package com.demo.controller;

import com.demo.model.Movie;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping("movies")
    public String moviesList(Model model){
        List<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        model.addAttribute("numMovies", movies.size());
        model.addAttribute("title", "Lista de películas");
        return "movies/movie-list";
    }


    @GetMapping("movies/{id}")
    public String order(Model model, @PathVariable Long id){
        model.addAttribute("movie", movieRepository.findById(id).orElseThrow());
        return "movies/movie-detail";
    }
}
