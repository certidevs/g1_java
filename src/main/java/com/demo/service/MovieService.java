package com.demo.service;

import com.demo.model.Movie;
import com.demo.model.enums.MovieStatus;
import com.demo.model.enums.Section;
import com.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public void updateStatusByDate(Movie movie) {
        if (movie.getReleaseDate() == null) return;

        if (movie.getSection() == Section.FLUX) return;

        LocalDate today = LocalDate.now();
        LocalDate release = movie.getReleaseDate();
        LocalDate endDate = release.plusMonths(1);
        LocalDate lastWeek = endDate.minusWeeks(1);
        LocalDate preWeek = release.minusWeeks(1);

        MovieStatus newStatus;

        if (today.isBefore(preWeek)) {
            newStatus = MovieStatus.COMING_SOON;
        } else if (today.isBefore(release)) {
            newStatus = MovieStatus.PRE_SALES;
        } else if (!today.isAfter(release.plusDays(3))) {
            newStatus = MovieStatus.NEW_RELEASE;
        } else if (today.isBefore(lastWeek)) {
            newStatus = MovieStatus.NOW_SHOWING;
        } else if (!today.isAfter(endDate)) {
            newStatus = MovieStatus.LAST_DAYS;
        } else {
            newStatus = MovieStatus.FINISHED;
        }

        updateStatus(movie, newStatus);
    }
}