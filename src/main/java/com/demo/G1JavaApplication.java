package com.demo;

import com.demo.model.Movie;
import com.demo.model.enums.Director;
import com.demo.model.enums.Genre;
import com.demo.model.enums.MinAge;
import com.demo.repository.MovieRepository;
import com.demo.repository.RoomRepository;
import com.demo.repository.SessionRepository;
import com.demo.repository.TicketRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.EnumSet;

//test
@SpringBootApplication
public class G1JavaApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(G1JavaApplication.class, args);

        MovieRepository movieRepository = context.getBean(MovieRepository.class);
        RoomRepository RoomRepository = context.getBean(RoomRepository.class);
        SessionRepository sessionRepository = context.getBean(SessionRepository.class);
        TicketRepository ticketRepository = context.getBean(TicketRepository.class);

        Movie movie1 = new Movie();
        movie1.setTitle("Drive");
        movie1.setGenres(EnumSet.of(Genre.CRIME, Genre.ACTION));
        movie1.setDirector(Director.UNSPECIFIED);
        movie1.setReleaseYear(2012);
        movie1.setMinAge(MinAge.AGE_16);
        movie1.setDirector(Director.UNSPECIFIED);
        movie1.setDuration(140);
        movieRepository.save(movie1);

        System.out.println(movie1);
    }

}
