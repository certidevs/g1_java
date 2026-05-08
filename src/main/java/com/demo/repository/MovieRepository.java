package com.demo.repository;

import com.demo.model.Movie;
import com.demo.model.enums.Director;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreSet(Set<Genre> genres);

    List<Movie> findByDirector(Director director);

    List<Movie> findByMinAge(MinAge minAge);


}