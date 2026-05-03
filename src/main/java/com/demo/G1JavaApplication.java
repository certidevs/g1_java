package com.demo;

import com.demo.model.Movie;
import com.demo.model.Room;
import com.demo.model.Session;
import com.demo.model.enums.*;
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
        RoomRepository roomRepository = context.getBean(RoomRepository.class);
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


        Room r1 = new Room();
        r1.setName("Sala 1");
        r1.setCapacity(100);
        r1.setScreentype(ScreenType.IMAX);

        Room r2 = new Room();
        r2.setName("Sala 2");
        r2.setCapacity(60);
        r2.setScreentype(ScreenType.STANDARD);

        Room r3 = new Room();
        r3.setName("Sala 3");
        r3.setCapacity(30);
        r3.setScreentype(ScreenType.THREE_D);

        Room r4 = new Room();
        r4.setName("Sala VIP");
        r4.setCapacity(20);
        r4.setScreentype(ScreenType.VIP);

        // Guardar en la base de datos
        roomRepository.saveAll(List.of(r1, r2, r3, r4));

        //Datos de SESIONES con las peliculas
        //De lunes a viernes hay 2 sesiones por pelicula, sabado y domingo 3 sesiones por pelicula
        // l1s1m1 --> Lunes (1 semana) Sesion 1 Movie 1
        // l1s2m1 --> Lunes (1 semana) Sesion 2 Movie 1
        Session l1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 6, 17, 30)).price(7.0).language(Language.ESP).room(r2).numAds(3).build());
        Session l1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 6, 20, 45)).price(7.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session m1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 7, 17, 30)).price(7.0).language(Language.ESP).room(r2).numAds(3).build());
        Session m1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 7, 20, 45)).price(7.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session x1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 8, 17, 30)).price(5.0).language(Language.ESP).room(r2).numAds(3).build());
        Session x1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 8, 20, 45)).price(5.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session j1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 9, 17, 30)).price(7.0).language(Language.ESP).room(r2).numAds(3).build());
        Session j1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 9, 20, 45)).price(7.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session v1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 10, 12, 40)).price(7.0).language(Language.ESP).room(r3).numAds(3).build());
        Session v1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 10, 18, 25)).price(9.0).language(Language.VOSE).room(r2).numAds(4).build());
        Session sa1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 11, 12, 40)).price(9.0).language(Language.ESP).room(r2).numAds(4).build());
        Session sa1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 11, 16, 30)).price(10.5).language(Language.VOSI).room(r2).numAds(4).build());
        Session sa1s3m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 11, 21, 00)).price(10.5).language(Language.VOSE).room(r2).numAds(4).build());
        Session su1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 12, 12, 40)).price(9.0).language(Language.ESP).room(r2).numAds(4).build());
        Session su1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 12, 16, 30)).price(10.5).language(Language.VOSI).room(r1).numAds(4).build());
        Session su1s3m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 12, 21, 00)).price(10.5).language(Language.VOSE).room(r4).numAds(4).build());
        Session l2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 13, 17, 30)).price(7.0).language(Language.ESP).room(r2).numAds(3).build());
        Session l2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 13, 20, 45)).price(7.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session m2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 14, 17, 30)).price(7.0).language(Language.ESP).room(r2).numAds(3).build());
        Session m2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 14, 20, 45)).price(7.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session x2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 15, 17, 30)).price(5.0).language(Language.ESP).room(r2).numAds(3).build());
        Session x2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 15, 20, 45)).price(5.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session j2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 16, 17, 30)).price(7.0).language(Language.ESP).room(r2).numAds(3).build());
        Session j2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 16, 20, 45)).price(7.0).language(Language.VOSE).room(r2).numAds(3).build());
        Session v2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 17, 12, 40)).price(7.0).language(Language.ESP).room(r3).numAds(3).build());
        Session v2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 17, 18, 25)).price(9.0).language(Language.VOSE).room(r2).numAds(4).build());

        sessionRepository.saveAll(List.of(l1s1m1,l1s2m1,m1s1m1,m1s2m1,x1s1m1,x1s2m1,j1s1m1,j1s2m1,v1s1m1,v1s2m1,sa1s1m1,sa1s2m1, sa1s3m1,su1s1m1,su1s2m1,su1s3m1,l2s1m1,l2s2m1,m2s1m1,m2s2m1,x2s1m1,x2s2m1,j2s1m1,j2s2m1,v2s1m1,v2s2m1));




    }

}
