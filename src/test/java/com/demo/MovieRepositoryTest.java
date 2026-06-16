package com.demo;

import com.demo.model.*;
import com.demo.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        var peli1 = movieRepository.save(Movie.builder().title("peli1").active(true).build());
        var peli2 = movieRepository.save(Movie.builder().title("peli2").active(true).build());
        var peli7 = movieRepository.save(Movie.builder().title("peli3").active(true).build());
        var peli3 = movieRepository.save(Movie.builder().title("peli4").active(true).build());
        var peli4 = movieRepository.save(Movie.builder().title("peli5").active(true).build());
        var peli5 = movieRepository.save(Movie.builder().title("peli6").active(true).build());

        var review1 = reviewRepository.save(Review.builder().rating(3).title("okkkkk").description("okkkkkkkk").movie(peli1).build());
        var review2 = reviewRepository.save(Review.builder().rating(3).title("okkkkk").description("okkkkkkkk").movie(peli1).build());
        var review3 = reviewRepository.save(Review.builder().rating(3).title("okkkkk").description("okkkkkkkk").movie(peli1).build());

        var review4 = reviewRepository.save(Review.builder().rating(5).title("okkkkk").description("okkkkkkkk").movie(peli2).build());
        var review5 = reviewRepository.save(Review.builder().rating(5).title("okkkkk").description("okkkkkkkk").movie(peli2).build());

        var room = roomRepository.save(Room.builder().price(10d).build());
        var session1 = sessionRepository.save(Session.builder().movie(peli1).room(room).build());


        var ticket1 = ticketRepository.save(Ticket.builder().price(10d).session(session1).purchaseTime(LocalDateTime.now()).build());
        var ticket2 = ticketRepository.save(Ticket.builder().price(10d).session(session1).purchaseTime(LocalDateTime.now()).build());
        var ticket3 = ticketRepository.save(Ticket.builder().price(20d).session(session1).purchaseTime(LocalDateTime.now()).build());
        var ticket4 = ticketRepository.save(Ticket.builder().price(10d).session(session1).build());
    }


    @Test
    public void original() {

        var movies = movieRepository.findActiveFiltering(null, null, null, null, null, null);

        for (Movie movie : movies) {

            movie.setAvgRating(
                    reviewRepository.calculateAverageRatingByMovieId(movie.getId())
            );

            movie.setCountReviews(
                    reviewRepository.countByMovie_Id(movie.getId())
            );

        }
        System.out.println(movies);

    }
    @Test
    public void testDTO() {

//        var dtos = movieRepository.findActiveFilteringWithStatsDTO();
//        System.out.println(dtos);

        var dtos = movieRepository.findActiveFilteringWithStatsDTOSubselect();
        System.out.println(dtos);

    }
}
