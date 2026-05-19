package com.demo;

import com.demo.model.*;
import com.demo.model.enums.*;
import com.demo.repository.*;
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

        DirectorRepository directorRepository = context.getBean(DirectorRepository.class);
        MovieRepository movieRepository = context.getBean(MovieRepository.class);
        RoomRepository roomRepository = context.getBean(RoomRepository.class);
        SessionRepository sessionRepository = context.getBean(SessionRepository.class);
        TicketRepository ticketRepository = context.getBean(TicketRepository.class);
        ReviewRepository reviewRepository = context.getBean(ReviewRepository.class);

        Director director1 = new Director();
        director1.setName("Horvath");

        Director director2 = new Director();
        director2.setName("Frankel");

        Director director3 = new Director();
        director3.setName("Fuqua");

        Director director4 = new Director();
        director4.setName("Mazon");

        Director director5 = new Director();
        director5.setName("McQuoid");

        directorRepository.saveAll(List.of(director1, director2, director3, director4, director5));

        Movie movie1 = new Movie();
        movie1.setTitle("Super Mario Galaxy");
        movie1.setGenreSet(EnumSet.of(Genre.COMEDY, Genre.ANIMATION));
        movie1.setDirector(director1);
        movie1.setReleaseYear(2026);
        movie1.setMinAge(MinAge.AGE_7);
        movie1.setDuration(98);
        movie1.setTrailerUrl("https://www.youtube.com/embed/ipzEY7c7it8");
        movie1.setImageUrl(
                "https://m.media-amazon.com/images/M/MV5BNTRkODc0MmItYzQ2NS00MGU3LTkwMTUtMGRmZjMwODNiNmI0XkEyXkFqcGc@._V1_.jpg"
        );
        movie1.setSinopsis("Mario y Luigi ayudan a la Princesa Peach en el Reino Champiñón y " +
                "conocen a un nuevo aliado: Yoshi. Cuando una celebración desencadena una aventura galáctica, " +
                "los hermanos viajan al espacio para detener a Bowser Jr. y proteger a Rosalina.");

        Movie movie2 = new Movie();
        movie2.setTitle("El diablo viste de Prada 2");
        movie2.setGenreSet(EnumSet.of(Genre.COMEDY, Genre.DRAMA));
        movie2.setDirector(director2);
        movie2.setReleaseYear(2026);
        movie2.setMinAge(MinAge.AGE_7);
        movie2.setDuration(119);
        movie2.setTrailerUrl("https://www.youtube.com/embed/IQD3qh7te2o");
        movie2.setImageUrl(
                "https://m.media-amazon.com/images/M/MV5BOGEyNGU5YzItY2VhMy00NTRmLWI5MmYtZDYxZTczMjcwN2E3XkEyXkFqcGc@._V1_.jpg"
        );
        movie2.setSinopsis("Cuando Miranda Priestly está por retirarse, " +
                "se reencuentra con Andy Sachs para enfrentarse a su exasistente convertida en rival: Emily Charlton.");

        Movie movie3 = new Movie();
        movie3.setTitle("Michael");
        movie3.setGenreSet(EnumSet.of(Genre.DRAMA, Genre.DOCUMENTARY));
        movie3.setDirector(director3);
        movie3.setReleaseYear(2026);
        movie3.setMinAge(MinAge.AGE_12);
        movie3.setDuration(127);
        movie3.setTrailerUrl("https://www.youtube.com/embed/iVJaujA54Y8");
        movie3.setImageUrl(
                "https://m.media-amazon.com/images/M/MV5BYmMzNTNkOGYtYWViYy00ZmI2LWJjZDQtODhhNTA4NmRjNmE0XkEyXkFqcGc@._V1_.jpg"
        );
        movie3.setSinopsis("Película biográfica sobre el rey del pop, Michael Jackson. " +
                "Retratará al cantante desde sus inicios hasta su trágico fallecimiento en 2009.");

        Movie movie4 = new Movie();
        movie4.setTitle("La familia Benetón + 2");
        movie4.setGenreSet(EnumSet.of(Genre.COMEDY, Genre.SPANISH));
        movie4.setDirector(director4);
        movie4.setReleaseYear(2026);
        movie4.setMinAge(MinAge.ALL);
        movie4.setDuration(87);
        movie4.setTrailerUrl("https://www.youtube.com/embed/O9kD-WhRNus");
        movie4.setImageUrl(
                "https://m.media-amazon.com/images/M/MV5BZDFiYmZhNjEtNDJlMS00ZDdmLTg3YjktOTFlZjJkY2Q3NGQ5XkEyXkFqcGc@._V1_.jpg"
        );
        movie4.setSinopsis("Cuando Toni Benetón cree que tiene todo bajo control en su entrañable y divertida " +
                "familia multicultural, la llegada de dos nuevos bebés pondrá patas arriba la casa y a toda la familia. " +
                "Los problemas crecen pero las aventuras también.");

        Movie movie5 = new Movie();
        movie5.setTitle("Mortal Kombat II");
        movie5.setGenreSet(EnumSet.of(Genre.FANTASY, Genre.SCI_FI));
        movie5.setDirector(director5);
        movie5.setReleaseYear(2026);
        movie5.setMinAge(MinAge.AGE_16);
        movie5.setDuration(116);
        movie5.setTrailerUrl("https://www.youtube.com/embed/ZDDRv_FsiFs");
        movie5.setImageUrl(
                "https://m.media-amazon.com/images/M/MV5BZDI1ODhkZWYtM2Q2NS00MmI4LTg3NzUtYTM3NTBlYmQ3NzM3XkEyXkFqcGc@._V1_.jpg"
        );
        movie5.setSinopsis("Los guerreros más queridos, incluyendo a Johnny Cage, luchan juntos en un combate definitivo " +
                "contra el malvado Shao Kahn, cuyo reino amenaza con destruir la Tierra y a quienes la protegen.");

        movieRepository.saveAll(List.of(movie1, movie2, movie3, movie4, movie5));


        Room r1 = new Room();
        r1.setName("Sala 1");
        r1.setCapacity(60);
        r1.setPrice(8.99);
        r1.setScreentype(ScreenType.STANDARD);

        Room r2 = new Room();
        r2.setName("Sala 2");
        r2.setCapacity(60);
        r2.setPrice(11.95);
        r2.setScreentype(ScreenType.THREE_D);

        Room r3 = new Room();
        r3.setName("Sala 3");
        r3.setCapacity(90);
        r3.setPrice(14.95);
        r3.setScreentype(ScreenType.IMAX);

        Room r4 = new Room();
        r4.setName("Sala VIP");
        r4.setCapacity(20);
        r4.setPrice(25.95);
        r4.setScreentype(ScreenType.VIP);

        // Guardar en la base de datos
        roomRepository.saveAll(List.of(r1, r2, r3, r4));

        //Datos de SESIONES con las peliculas
        //De lunes a viernes hay 2 sesiones por pelicula, sabado y domingo 3 sesiones por pelicula
        // l1s1m1 --> Lunes (1 semana) Sesion 1 Movie 1
        // l1s2m1 --> Lunes (1 semana) Sesion 2 Movie 1
        Session l1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 6, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session l1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 6, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session m1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 7, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session m1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 7, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session x1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 8, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session x1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 8, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session j1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 9, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session j1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 9, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session v1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 10, 12, 40)).language(Language.ESP).room(r3).numAds(3).build());
        Session v1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 10, 18, 25)).language(Language.VOSE).room(r2).numAds(4).build());
        Session sa1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 11, 12, 40)).language(Language.ESP).room(r2).numAds(4).build());
        Session sa1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 11, 16, 30)).language(Language.VOSI).room(r2).numAds(4).build());
        Session sa1s3m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 11, 21, 00)).language(Language.VOSE).room(r2).numAds(4).build());
        Session su1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 12, 12, 40)).language(Language.ESP).room(r2).numAds(4).build());
        Session su1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 12, 16, 30)).language(Language.VOSI).room(r1).numAds(4).build());
        Session su1s3m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 12, 21, 00)).language(Language.VOSE).room(r4).numAds(4).build());
        Session l2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 13, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session l2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 13, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session m2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 14, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session m2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 14, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session x2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 15, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session x2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 15, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session j2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 16, 17, 30)).language(Language.ESP).room(r2).numAds(3).build());
        Session j2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 16, 20, 45)).language(Language.VOSE).room(r2).numAds(3).build());
        Session v2s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 17, 12, 40)).language(Language.ESP).room(r3).numAds(3).build());
        Session v2s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.of(2026, 4, 17, 18, 25)).language(Language.VOSE).room(r2).numAds(4).build());

        sessionRepository.saveAll(List.of(l1s1m1,l1s2m1,m1s1m1,m1s2m1,x1s1m1,x1s2m1,j1s1m1,j1s2m1,v1s1m1,v1s2m1,sa1s1m1,sa1s2m1, sa1s3m1,su1s1m1,su1s2m1,su1s3m1,l2s1m1,l2s2m1,m2s1m1,m2s2m1,x2s1m1,x2s2m1,j2s1m1,j2s2m1,v2s1m1,v2s2m1));


        // Tickets de prueba
        var ticket1 = Ticket.builder()
                .seatRow("A")
                .seatNumber(1)
                .price(8.99) // precio base de la sala 2
                .session(l1s1m1) // Primera sesión del lunes
                .build();

        var ticket2 = Ticket.builder()
                .seatRow("B")
                .seatNumber(5)
                .price(14.95) // precio base de la sala 2 + 3€ por ser sábado
                .session(sa1s1m1)  // Primera sesión del sábado (más cara)
                .build();

        var ticket3 = Ticket.builder()
                .seatRow("C")
                .seatNumber(10)
                .price(28.95) // precio base sala VIP + 3€ por ser domingo
                .session(su1s2m1)  // Sesión VIP del domingo
                .build();

        ticketRepository.saveAll(List.of(ticket1, ticket2, ticket3));


        Review review1 = Review.builder()
                .description("Te muestra como era la vida de Michael")
                .movie(movie3)
                .title("Amé cada segundo")
                .rating(5)
                .build();

        Review review2 = Review.builder()
                .description("Esperaba mas de esta peli, me voy a ver Michael otra vez")
                .movie(movie3)
                .title("Me dormí")
                .rating(2)
                .build();

        Review review3 = Review.builder()
                .description("Muy recomendada para ver en familia")
                .movie(movie4)
                .title("Me reí hasta no poder más")
                .rating(4)
                .build();

        Review review4 = Review.builder()
                .description("Comedia icónica, pero esperaba un poco más de esta secuela")
                .movie(movie2)
                .title("Entretenida pero no a la altura del original")
                .rating(3)
                .build();

        reviewRepository.saveAll(List.of(review1, review2, review3, review4));
    }

}
