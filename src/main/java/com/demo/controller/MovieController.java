package com.demo.controller;

import com.demo.model.Movie;
import com.demo.model.Review;
import com.demo.model.Room;
import com.demo.model.Session;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.model.enums.MovieStatus;
import com.demo.model.enums.Section;
import com.demo.repository.DirectorRepository;
import com.demo.repository.MovieRepository;
import com.demo.repository.ReviewRepository;
import com.demo.repository.SessionRepository;
import com.demo.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@AllArgsConstructor
@Controller
public class MovieController {
    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;
    private final DirectorRepository directorRepository;
    private final ReviewRepository reviewRepository;
    private final FileService fileService;


    @GetMapping("movies")
    public String moviesList(
            Model model,
            @RequestParam(required = false) String directorName,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MinAge minAge,
            @RequestParam(required = false) MovieStatus movieStatus,
            @RequestParam(required = false) Section section
    ) {
        if (section == null) {
            return "redirect:/movies?section=BILLBOARD";
        }

        List<Movie> movies = movieRepository.findActiveFiltering(directorName, genre, title, minAge, movieStatus, section);

        model.addAttribute("movies", movies);
        model.addAttribute("numMovies", movies.size());
        model.addAttribute("title", "Películas");
        model.addAttribute("minAges", MinAge.values());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("moviesStatus", MovieStatus.values());
        model.addAttribute("sections", Section.values());
        model.addAttribute("section", section.name());

        return "movies/movie-list";
    }

    @GetMapping("movies/billboard")
    public String billboard() {
        return "redirect:/movies?section=BILLBOARD";
    }

    @GetMapping("movies/flux")
    public String flux() {
        return "redirect:/movies?section=FLUX";
    }


    @GetMapping("movies/{id}")
    public String movie(Model model, @PathVariable Long id, @RequestParam(required = false) LocalDate date) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        model.addAttribute("movie", movie);
        model.addAttribute("selectedDate", date);

        List<Review> reviews = reviewRepository.findByMovie_IdOrderByCreationDateDesc(movie.getId());
        model.addAttribute("reviews", reviews);

        List<LocalDate> availableDates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 5; i++) {
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

    @GetMapping("movies/deactivate/{id}")
    public String movieDeactivate(@PathVariable Long id, Model model){
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isPresent()){
            Movie movie = movieOptional.get();
            movie.setActive(false);
            movieRepository.save(movie);
        }
        return "redirect:/movies";
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("minAges", MinAge.values());
        model.addAttribute("moviesStatus", MovieStatus.values());
        model.addAttribute("sections", Section.values());
        return "movies/movie-form";
    }

    @GetMapping("movies/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieRepository.findById(id).orElseThrow());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("minAges", MinAge.values());
        model.addAttribute("moviesStatus", MovieStatus.values());
        model.addAttribute("sections", Section.values());
        return "movies/movie-form";
    }

    @PostMapping("movies")
    public String saveMovie(
            @ModelAttribute Movie movie,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        String imageUrl = fileService.store(imageFile);
        if (imageUrl != null)
            movie.setImageUrl(imageUrl);
        movieRepository.save(movie);
        return "redirect:/movies/" + movie.getId();
    }
}
