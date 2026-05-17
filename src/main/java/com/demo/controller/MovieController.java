package com.demo.controller;

import com.demo.model.Movie;
import com.demo.model.enums.Director;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.repository.MovieRepository;
import com.demo.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
public class MovieController {
    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;

    @GetMapping("movies")
    public String moviesList(
            Model model,
            @RequestParam(required = false) Director director,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MinAge minAge,
            @RequestParam(required = false) Integer releaseYear
    ){
        List<Movie> movies = movieRepository.findActiveFiltering(director, genre, title, minAge, releaseYear);
        model.addAttribute("movies", movies);
        model.addAttribute("numMovies", movies.size());
        model.addAttribute("title", "Lista de películas");
        model.addAttribute("minAges", MinAge.values());
        model.addAttribute("genres", Genre.values());
        return "movies/movie-list";
    }


    @GetMapping("movies/{id}")
    public String movie(Model model, @PathVariable Long id){
        model.addAttribute("movie", movieRepository.findById(id).orElseThrow());
        model.addAttribute("projections", sessionRepository.findByMovie_Id(id));
        return "movies/movie-detail";
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", Director.values());
        model.addAttribute("minAges", MinAge.values());
        return "movies/movie-form";
    }

    @GetMapping("movies/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieRepository.findById(id).orElseThrow());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", Director.values());
        model.addAttribute("minAges", MinAge.values());
        return "movies/movie-form";
    }

    @PostMapping("movies")
    public String saveMovie(@ModelAttribute Movie movie) {
        System.out.println("Película guardada: " + movie);
        movieRepository.save(movie);
        return "redirect:/movies/" + movie.getId();
    }
}
