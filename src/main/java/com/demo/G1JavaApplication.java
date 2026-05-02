package com.demo;

import com.demo.model.Movie;
import com.demo.model.Session;
import com.demo.model.enums.Director;
import com.demo.model.enums.Genre;
import com.demo.model.enums.Language;
import com.demo.model.enums.MinAge;
import com.demo.repository.MovieRepository;
import com.demo.repository.RoomRepository;
import com.demo.repository.SessionRepository;
import com.demo.repository.TicketRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

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
        movie1.setTitle("Super Mario Galaxy");
        movie1.setGenres(EnumSet.of(Genre.COMEDY, Genre.ANIMATION));
        movie1.setDirector(Director.HORVATH);
        movie1.setReleaseYear(2026);
        movie1.setMinAge(MinAge.AGE_7);
        movie1.setDuration(98);
        movie1.setTrailerUrl("https://youtu.be/ipzEY7c7it8");
        movie1.setImageUrl("https://m.media-amazon.com/images/M/MV5BMDg5MjRkNWEtYmU1Mi00MTExLTk5MDQtY2RiMWVkZWNiOThjXkEyXkFqcGc@._V1_.jpg");
        movie1.setSinopsis("Mario y Luigi ayudan a la Princesa Peach en el Reino Champiñón y conocen a un nuevo aliado: Yoshi. Cuando una celebración desencadena una aventura galáctica, los hermanos viajan al espacio para detener a Bowser Jr. y proteger a Rosalina.");

        Movie movie2 = new Movie();
        movie2.setTitle("El diablo viste de Prada 2");
        movie2.setGenres(EnumSet.of(Genre.COMEDY, Genre.DRAMA));
        movie2.setDirector(Director.FRANKEL);
        movie2.setReleaseYear(2026);
        movie2.setMinAge(MinAge.AGE_7);
        movie2.setDuration(119);
        movie2.setTrailerUrl("https://youtu.be/IQD3qh7te2o");
        movie2.setImageUrl("https://m.media-amazon.com/images/M/MV5BOGEyNGU5YzItY2VhMy00NTRmLWI5MmYtZDYxZTczMjcwN2E3XkEyXkFqcGc@._V1_.jpg");
        movie2.setSinopsis("Cuando Miranda Priestly está por retirarse, " +
                "se reencuentra con Andy Sachs para enfrentarse a su exasistente convertida en rival: Emily Charlton.");

        Movie movie3 = new Movie();
        movie3.setTitle("Michael");
        movie3.setGenres(EnumSet.of(Genre.DRAMA, Genre.DOCUMENTARY));
        movie3.setDirector(Director.FUQUA);
        movie3.setReleaseYear(2026);
        movie3.setMinAge(MinAge.AGE_12);
        movie3.setDuration(127);
        movie3.setTrailerUrl("https://youtu.be/iVJaujA54Y8");
        movie3.setImageUrl("https://m.media-amazon.com/images/M/MV5BYmMzNTNkOGYtYWViYy00ZmI2LWJjZDQtODhhNTA4NmRjNmE0XkEyXkFqcGc@._V1_.jpg");
        movie3.setSinopsis("Película biográfica sobre el rey del pop, Michael Jackson. " +
                "Retratará al cantante desde sus inicios hasta su trágico fallecimiento en 2009.");

        Movie movie4 = new Movie();
        movie4.setTitle("La familia Benetón + 2");
        movie4.setGenres(EnumSet.of(Genre.COMEDY, Genre.SPANISH));
        movie4.setDirector(Director.MAZON);
        movie4.setReleaseYear(2026);
        movie4.setMinAge(MinAge.ALL);
        movie4.setDuration(87);
        movie4.setTrailerUrl("https://youtu.be/O9kD-WhRNus");
        movie4.setImageUrl("https://m.media-amazon.com/images/M/MV5BYTFlNmEyOTItYjI4NS00MDA1LWFkNjItMWU4NTM5ODFjOTNjXkEyXkFqcGc@._V1_.jpg");
        movie4.setSinopsis("Cuando Toni Benetón cree que tiene todo bajo control en su entrañable y divertida " +
                "familia multicultural, la llegada de dos nuevos bebés pondrá patas arriba la casa y a toda la familia. " +
                "Los problemas crecen pero las aventuras también.");

        movieRepository.saveAll(List.of(movie1, movie2, movie3, movie4));
    }

}
