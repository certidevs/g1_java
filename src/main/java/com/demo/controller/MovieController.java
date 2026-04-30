package com.demo.controller;

import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@Controller
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping("movies")
    public String moviesList(Model model){
        model.addAttribute("movies",  movieRepository.findAll());
        return "movies/movie-list";
    }


    @GetMapping("movies/{id}")
    public String order(Model model, @PathVariable Long id){
        model.addAttribute("movie", movieRepository.findById(id).orElseThrow());
        return "movies/movie-detail";
    }
}
