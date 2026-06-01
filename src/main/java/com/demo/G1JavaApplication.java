package com.demo;

import com.demo.model.*;
import com.demo.model.enums.*;
import com.demo.repository.*;
import com.demo.service.MovieService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
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
        MovieService movieService = context.getBean(MovieService.class);

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

        Director director6 = new Director();
        director6.setName("McCarthy");

        Director director7 = new Director();
        director7.setName("Serkis");

        Director director8 = new Director();
        director8.setName("Balda");

        Director directorOld1 = new Director();
        directorOld1.setName("Honda");

        directorRepository.saveAll(
                List.of(director1, director2, director3, director4, director5,  director6, director7, director8,
                        directorOld1)
        );

        Movie movie1 = new Movie();
        movie1.setTitle("Super Mario Galaxy");
        movie1.setGenreSet(EnumSet.of(Genre.COMEDY, Genre.ANIMATION));
        movie1.setDirector(director1);
        movie1.setReleaseDate(LocalDate.of(2026, 4, 1));
        movie1.setMinAge(MinAge.AGE_7);
        movieService.updateStatus(movie1, MovieStatus.NOW_SHOWING);
        movie1.setDuration(98);
        movie1.setTrailerUrl("https://www.youtube.com/embed/ipzEY7c7it8");
        movie1.setImageUrl("/uploads/SuperMarioGalaxy.jpg");
        movie1.setSinopsis("Mario y Luigi ayudan a la Princesa Peach en el Reino Champiñón y " +
                "conocen a un nuevo aliado: Yoshi. Cuando una celebración desencadena una aventura galáctica, " +
                "los hermanos viajan al espacio para detener a Bowser Jr. y proteger a Rosalina.");

        Movie movie2 = new Movie();
        movie2.setTitle("El diablo viste de Prada 2");
        movie2.setGenreSet(EnumSet.of(Genre.COMEDY, Genre.DRAMA));
        movie2.setDirector(director2);
        movie2.setReleaseDate(LocalDate.of(2026, 4, 30));
        movie2.setMinAge(MinAge.AGE_7);
        movieService.updateStatus(movie2, MovieStatus.NOW_SHOWING);
        movie2.setDuration(119);
        movie2.setTrailerUrl("https://www.youtube.com/embed/IQD3qh7te2o");
        movie2.setImageUrl("/uploads/DiabloVistePrada2.jpg");
        movie2.setSinopsis("Cuando Miranda Priestly está por retirarse, " +
                "se reencuentra con Andy Sachs para enfrentarse a su exasistente convertida en rival: Emily Charlton.");

        Movie movie3 = new Movie();
        movie3.setTitle("Michael");
        movie3.setGenreSet(EnumSet.of(Genre.DRAMA, Genre.DOCUMENTARY));
        movie3.setDirector(director3);
        movie3.setReleaseDate(LocalDate.of(2026, 4, 24));
        movie3.setMinAge(MinAge.AGE_12);
        movieService.updateStatus(movie3, MovieStatus.NOW_SHOWING);
        movie3.setDuration(127);
        movie3.setTrailerUrl("https://www.youtube.com/embed/iVJaujA54Y8");
        movie3.setImageUrl("/uploads/Michael.jpg");
        movie3.setSinopsis("Película biográfica sobre el rey del pop, Michael Jackson. " +
                "Retratará al cantante desde sus inicios hasta su trágico fallecimiento en 2009.");

        Movie movie4 = new Movie();
        movie4.setTitle("La familia Benetón + 2");
        movie4.setGenreSet(EnumSet.of(Genre.COMEDY, Genre.SPANISH));
        movie4.setDirector(director4);
        movie4.setReleaseDate(LocalDate.of(2026, 4, 17));
        movie4.setMinAge(MinAge.ALL);
        movieService.updateStatus(movie4, MovieStatus.NOW_SHOWING);
        movie4.setDuration(87);
        movie4.setTrailerUrl("https://www.youtube.com/embed/O9kD-WhRNus");
        movie4.setImageUrl("/uploads/FamiliaBeneton+2.jpg");
        movie4.setSinopsis("Cuando Toni Benetón cree que tiene todo bajo control en su entrañable y divertida " +
                "familia multicultural, la llegada de dos nuevos bebés pondrá patas arriba la casa y a toda la familia. " +
                "Los problemas crecen pero las aventuras también.");

        Movie movie5 = new Movie();
        movie5.setTitle("Mortal Kombat II");
        movie5.setGenreSet(EnumSet.of(Genre.FANTASY, Genre.SCI_FI));
        movie5.setDirector(director5);
        movie5.setReleaseDate(LocalDate.of(2026, 5, 8));
        movie5.setMinAge(MinAge.AGE_16);
        movieService.updateStatus(movie5, MovieStatus.NOW_SHOWING);
        movie5.setDuration(116);
        movie5.setTrailerUrl("https://www.youtube.com/embed/ZDDRv_FsiFs");
        movie5.setImageUrl("/uploads/MortalKombat2.jpg");
        movie5.setSinopsis("Los guerreros más queridos, incluyendo a Johnny Cage, luchan juntos en un combate definitivo " +
                "contra el malvado Shao Kahn, cuyo reino amenaza con destruir la Tierra y a quienes la protegen.");

        Movie movie6 = new Movie();
        movie6.setTitle("Hokum");
        movie6.setGenreSet(EnumSet.of(Genre.HORROR, Genre.THRILLER));
        movie6.setDirector(director6);
        movie6.setReleaseDate(LocalDate.of(2026, 5, 15));
        movie6.setMinAge(MinAge.AGE_18);
        movieService.updateStatus(movie6, MovieStatus.NOW_SHOWING);
        movie6.setDuration(101);
        movie6.setTrailerUrl("https://www.youtube.com/embed/6cHc23cGDIc");
        movie6.setImageUrl("/uploads/Hokum.jpg");
        movie6.setSinopsis("Un escritor de terror visita una posada irlandesa para esparcir las cenizas de sus padres, " +
                "sin saber que se dice que la propiedad está embrujada por una bruja.");

        Movie movie7 = new Movie();
        movie7.setTitle("Rebelión en la granja");
        movie7.setGenreSet(EnumSet.of(Genre.ANIMATION, Genre.COMEDY));
        movie7.setDirector(director7);
        movie7.setReleaseDate(LocalDate.of(2026, 5, 1));
        movie7.setMinAge(MinAge.AGE_7);
        movieService.updateStatus(movie7, MovieStatus.NOW_SHOWING);
        movie7.setDuration(96);
        movie7.setTrailerUrl("https://www.youtube.com/embed/NN6J7l6ulYk");
        movie7.setImageUrl("/uploads/AnimalFarm.jpg");
        movie7.setSinopsis("Alegoría de la Revolución Rusa con animales rebelándose contra granjero buscando libertad. " +
                "Su revuelta es socavada internamente sin que los animales lo sepan.");

        Movie movie8 = new Movie();
        movie8.setTitle("Las ovejas detectives");
        movie8.setGenreSet(EnumSet.of(Genre.ADVENTURE, Genre.COMEDY));
        movie8.setDirector(director8);
        movie8.setReleaseDate(LocalDate.of(2026, 5, 8));
        movie8.setMinAge(MinAge.AGE_7);
        movieService.updateStatus(movie8, MovieStatus.NOW_SHOWING);
        movie8.setDuration(109);
        movie8.setTrailerUrl("https://www.youtube.com/embed/XrDIQGFm5Fw");
        movie8.setImageUrl("/uploads/OvejasDetectives.jpg");
        movie8.setSinopsis("Cada noche, un pastor lee una novela policíaca, imaginando que sus ovejas pueden entenderla. " +
                "Cuando lo encuentran muerto, las ovejas se dan cuenta de que se trata de un asesinato " +
                "y creen saber todo lo necesario para resolverlo.");

        Movie movieOld1 = new Movie();
        movieOld1.setTitle("Gojira");
        movieOld1.setGenreSet(EnumSet.of(Genre.ADVENTURE, Genre.ACTION));
        movieOld1.setDirector(directorOld1);
        movieOld1.setReleaseDate(LocalDate.of(1956, 11, 5));
        movieOld1.setMinAge(MinAge.AGE_12);
        movieService.updateStatus(movieOld1, MovieStatus.VOTED);
        movieOld1.setDuration(96);
        movieOld1.setTrailerUrl("https://www.youtube.com/embed/IVONRrcn9TI");
        movieOld1.setImageUrl("/uploads/Gojira.jpg");
        movieOld1.setSinopsis("Los Estados Unidos hacen una pruebas de armamento nuclear que resultan en la creación de " +
                "una terrible e imparable bestia similar a un dinosaurio.");

        movieRepository.saveAll(
                List.of(movie1, movie2, movie3, movie4, movie5,  movie6, movie7, movie8, movieOld1)
        );


        Room r1 = new Room();
        r1.setName("Sala 1");
        r1.setCapacity(60);
        r1.setPrice(8.99);
        r1.setScreentype(ScreenType.STANDARD);

        Room r2 = new Room();
        r2.setName("Sala 2");
        r2.setCapacity(70);
        r2.setPrice(11.95);
        r2.setScreentype(ScreenType.THREE_D);

        Room r3 = new Room();
        r3.setName("Sala 3");
        r3.setCapacity(90);
        r3.setPrice(14.95);
        r3.setScreentype(ScreenType.IMAX);

        Room r4 = new Room();
        r4.setName("Sala VIP");
        r4.setCapacity(30);
        r4.setPrice(25.95);
        r4.setScreentype(ScreenType.VIP);

        Room r5 = new Room();
        r5.setName("Sala 5");
        r5.setCapacity(50);
        r5.setPrice(20.95);
        r5.setScreentype(ScreenType.SCREEN_X);

        Room r6 = new Room();
        r6.setName("Sala 6");
        r6.setCapacity(60);
        r6.setPrice(8.99);
        r6.setScreentype(ScreenType.STANDARD);

        Room r7 = new Room();
        r7.setName("Sala 7");
        r7.setCapacity(70);
        r7.setPrice(11.95);
        r7.setScreentype(ScreenType.THREE_D);

        Room r8 = new Room();
        r8.setName("Sala 8");
        r8.setCapacity(45);
        r8.setPrice(18.95);
        r8.setScreentype(ScreenType.DOLBY_CINE);

        // Guardar en la base de datos
        roomRepository.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7, r8));

        //Datos de SESIONES con las peliculas
        //De lunes a viernes hay 2 sesiones por pelicula, sabado y domingo 3 sesiones por pelicula
        // l1s1m1 --> Lunes (1 semana) Sesion 1 Movie 1
        // l1s2m1 --> Lunes (1 semana) Sesion 2 Movie 1
        Session l1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.now().withHour(20).withMinute(0)).language(Language.ESP).room(r2).numAds(3).build());
        Session l1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.now().withHour(22).withMinute(0)).language(Language.VOSE).room(r2).numAds(3).build());
        Session m1s1m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.now().plusDays(1).withHour(20).withMinute(0)).language(Language.ESP).room(r2).numAds(3).build());
        Session m1s2m1 = sessionRepository.save(Session.builder().movie(movie1).startTime(LocalDateTime.now().plusDays(1).withHour(22).withMinute(0)).language(Language.VOSE).room(r2).numAds(3).build());
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


        // Sesiones para El diablo viste de Prada 2 (movie2)
        Session m2l1 = sessionRepository.save(Session.builder().movie(movie2).startTime(LocalDateTime.of(2026, 4, 6, 17,0)).language(Language.ESP).room(r1).numAds(3).build());
        Session m2l2 = sessionRepository.save(Session.builder().movie(movie2).startTime(LocalDateTime.of(2026, 4, 6, 20,0)).language(Language.VOSE).room(r1).numAds(3).build());
        Session m2m1 = sessionRepository.save(Session.builder().movie(movie2).startTime(LocalDateTime.of(2026, 4, 7, 18,0)).language(Language.ESP).room(r3).numAds(4).build());
        Session m2sa1 = sessionRepository.save(Session.builder().movie(movie2).startTime(LocalDateTime.of(2026, 4, 11, 16,0)).language(Language.ESP).room(r1).numAds(4).build());
        Session m2sa2 = sessionRepository.save(Session.builder().movie(movie2).startTime(LocalDateTime.of(2026, 4, 11, 19,30)).language(Language.VOSE).room(r1).numAds(4).build());

        // Sesiones para Michael (movie3)
        Session m3l1 = sessionRepository.save(Session.builder().movie(movie3).startTime(LocalDateTime.of(2026, 4, 6, 18,0)).language(Language.ESP).room(r4).numAds(2).build());
        Session m3m1 = sessionRepository.save(Session.builder().movie(movie3).startTime(LocalDateTime.of(2026, 4, 7, 20,30)).language(Language.VOSE).room(r4).numAds(2).build());
        Session m3x1 = sessionRepository.save(Session.builder().movie(movie3).startTime(LocalDateTime.of(2026, 4, 8, 18,0)).language(Language.ESP).room(r1).numAds(3).build());
        Session m3sa1 = sessionRepository.save(Session.builder().movie(movie3).startTime(LocalDateTime.of(2026, 4, 11, 20,0)).language(Language.VOSE).room(r3).numAds(4).build());
        Session m3su1 = sessionRepository.save(Session.builder().movie(movie3).startTime(LocalDateTime.of(2026, 4, 12, 18,30)).language(Language.ESP).room(r4).numAds(4).build());

        // Sesiones para La familia Benetón + 2 (movie4)
        Session m4l1 = sessionRepository.save(Session.builder().movie(movie4).startTime(LocalDateTime.of(2026, 4, 6, 16,0)).language(Language.ESP).room(r1).numAds(5).build());
        Session m4m1 = sessionRepository.save(Session.builder().movie(movie4).startTime(LocalDateTime.of(2026, 4, 7, 16,0)).language(Language.ESP).room(r2).numAds(5).build());
        Session m4sa1 = sessionRepository.save(Session.builder().movie(movie4).startTime(LocalDateTime.of(2026, 4, 11, 12,0)).language(Language.ESP).room(r1).numAds(5).build());
        Session m4sa2 = sessionRepository.save(Session.builder().movie(movie4).startTime(LocalDateTime.of(2026, 4, 11, 15,0)).language(Language.ESP).room(r2).numAds(5).build());
        Session m4su1 = sessionRepository.save(Session.builder().movie(movie4).startTime(LocalDateTime.of(2026, 4, 12, 13,0)).language(Language.ESP).room(r1).numAds(5).build());

        // Sesiones para Mortal Kombat II (movie5)
        Session m5l1 = sessionRepository.save(Session.builder().movie(movie5).startTime(LocalDateTime.of(2026, 4, 6, 21,30)).language(Language.ESP).room(r2).numAds(4).build());
        Session m5m1 = sessionRepository.save(Session.builder().movie(movie5).startTime(LocalDateTime.of(2026, 4, 7, 21,30)).language(Language.VOSE).room(r3).numAds(4).build());
        Session m5x1 = sessionRepository.save(Session.builder().movie(movie5).startTime(LocalDateTime.of(2026, 4, 8, 21,30)).language(Language.ESP).room(r2).numAds(4).build());
        Session m5sa1 = sessionRepository.save(Session.builder().movie(movie5).startTime(LocalDateTime.of(2026, 4, 11, 22,0)).language(Language.VOSE).room(r3).numAds(4).build());
        Session m5su1 = sessionRepository.save(Session.builder().movie(movie5).startTime(LocalDateTime.of(2026, 4, 12, 22,0)).language(Language.ESP).room(r2).numAds(4).build());

        sessionRepository.saveAll(List.of(l1s1m1,l1s2m1,m1s1m1,m1s2m1,x1s1m1,x1s2m1,j1s1m1,j1s2m1,v1s1m1,v1s2m1,sa1s1m1,sa1s2m1, sa1s3m1,su1s1m1,su1s2m1,su1s3m1,l2s1m1,l2s2m1,m2s1m1,m2s2m1,x2s1m1,x2s2m1,j2s1m1,j2s2m1,v2s1m1,v2s2m1,
                m2l1, m2l2, m2m1, m2sa1, m2sa2,
                m3l1, m3m1, m3x1, m3sa1, m3su1,
                m4l1, m4m1, m4sa1, m4sa2, m4su1,
                m5l1, m5m1, m5x1, m5sa1, m5su1));

        // Generar butacas para todas las sesiones
        List<Session> todasLasSesiones = sessionRepository.findAll();

        for (Session ses : todasLasSesiones) {
            int cols = 10;
            int rows = ses.getRoom().getCapacity() / 10;

            for (int f = 0; f < rows; f++) {
                String fila = String.valueOf((char) ('A' + f));
                for (int c = 1; c <= cols; c++) {
                    ticketRepository.save(Ticket.builder()
                            .seatRow(fila)
                            .seatNumber(c)
                            .price(ses.getRoom().getPrice())
                            .session(ses)
                            .build());
                }
            }
        }


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
