package com.demo.controller;

import com.demo.model.Movie;
import com.demo.model.Room;
import com.demo.model.Session;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.model.enums.MovieStatus;
import com.demo.repository.DirectorRepository;
import com.demo.repository.MovieRepository;
import com.demo.repository.SessionRepository;
import com.demo.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
public class MovieController {
    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;
    private final DirectorRepository directorRepository;
    private final MovieService movieService;


    @GetMapping("movies")
    public String moviesList(
            Model model,
            @RequestParam(required = false) String directorName,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MinAge minAge,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false)MovieStatus movieStatus
            ) {
        List<Movie> movies = movieRepository.findActiveFiltering(directorName, genre, title, minAge, releaseYear, movieStatus);

        model.addAttribute("movies", movies);
        model.addAttribute("numMovies", movies.size());
        model.addAttribute("title", "Películas");
        model.addAttribute("minAges", MinAge.values());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("moviesStatus", MovieStatus.values());

        return "movies/movie-list";
    }


    @GetMapping("movies/{id}")
    public String movie(Model model, @PathVariable Long id, @RequestParam(required = false) LocalDate date) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        model.addAttribute("movie", movie);
        model.addAttribute("selectedDate", date);

        List<LocalDate> availableDates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 15; i++) {
            availableDates.add(today.plusDays(i));
        }
        model.addAttribute("availableDates", availableDates);

        if (date != null) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            List<Session> projections = sessionRepository.findByMovie_IdAndStartTimeBetween(id, start, end);

            Map<Room, List<Session>> projectionsByRoom = new LinkedHashMap<>();
            for (Session session : projections) {
                projectionsByRoom
                        .computeIfAbsent(session.getRoom(), room -> new ArrayList<>())
                        .add(session);
            }

            model.addAttribute("projections", projections);
            model.addAttribute("projectionsByRoom", projectionsByRoom);
        }

        return "movies/movie-detail";
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("minAges", MinAge.values());
        return "movies/movie-form";
    }

    @GetMapping("movies/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieRepository.findById(id).orElseThrow());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("minAges", MinAge.values());
        return "movies/movie-form";
    }

    @PostMapping("movies")
    public String saveMovie(@ModelAttribute Movie movie,
                            @RequestParam String directorName) {
        movieService.createMovie(movie, directorName);
        return "redirect:/movies/" + movie.getId();
    }
}
