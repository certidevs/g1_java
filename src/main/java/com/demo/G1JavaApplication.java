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

        Director directorOld2 = new Director();
        directorOld2.setName("Curtiz");

        Director directorOld3 = new Director();
        directorOld3.setName("Reed");

        Director directorOld4 = new Director();
        directorOld4.setName("Lumet");

        directorRepository.saveAll(
                List.of(director1, director2, director3, director4, director5,  director6, director7, director8,
                        directorOld1,  directorOld2, directorOld3, directorOld4)
        );

        Movie movie1 = new Movie();
        movie1.setTitle("Super Mario Galaxy");
        movie1.setGenreSet(EnumSet.of(Genre.COMEDY, Genre.ANIMATION));
        movie1.setDirector(director1);
        movie1.setReleaseDate(LocalDate.of(2026, 4, 1));
        movie1.setMinAge(MinAge.AGE_7);
        movieService.updateStatusByDate(movie1);
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
        movieService.updateStatusByDate(movie2);
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
        movieService.updateStatusByDate(movie3);
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
        movieService.updateStatusByDate(movie4);
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
        movieService.updateStatusByDate(movie5);
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
        movieService.updateStatusByDate(movie6);
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
        movieService.updateStatusByDate(movie7);
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
        movieService.updateStatusByDate(movie8);
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

        Movie movieOld2 = new Movie();
        movieOld2.setTitle("Casablanca");
        movieOld2.setGenreSet(EnumSet.of(Genre.DRAMA, Genre.ROMANCE));
        movieOld2.setDirector(directorOld2);
        movieOld2.setReleaseDate(LocalDate.of(1946, 12, 19));
        movieOld2.setMinAge(MinAge.AGE_16);
        movieService.updateStatus(movieOld2, MovieStatus.VOTED);
        movieOld2.setDuration(102);
        movieOld2.setTrailerUrl("https://www.youtube.com/embed/nqlROp1TIgo");
        movieOld2.setImageUrl("/uploads/Casablanca.jpg");
        movieOld2.setSinopsis("Un cínico expatriado norteamericano, dueño de un café, se debate entre ayudar " +
                "o no a su antigua amante y a su marido fugitivo a escapar de los nazis en el Marruecos francés.");

        Movie movieOld3 = new Movie();
        movieOld3.setTitle("El tercer hombre");
        movieOld3.setGenreSet(EnumSet.of(Genre.THRILLER, Genre.MYSTERY));
        movieOld3.setDirector(directorOld3);
        movieOld3.setReleaseDate(LocalDate.of(1950, 4, 8));
        movieOld3.setMinAge(MinAge.AGE_12);
        movieService.updateStatus(movieOld3, MovieStatus.VOTED);
        movieOld3.setDuration(104);
        movieOld3.setTrailerUrl("https://www.youtube.com/embed/zEZPP5-sJGE");
        movieOld3.setImageUrl("/uploads/ElTercerHombre.jpg");
        movieOld3.setSinopsis("El escritor de novelas pulp Holly Martins viaja a la sombría Viena de la posguerra " +
                "para investigar la misteriosa muerte de un viejo amigo, Harry Lime.");

        Movie movieOld4 = new Movie();
        movieOld4.setTitle("12 hombres sin piedad");
        movieOld4.setGenreSet(EnumSet.of(Genre.CRIME, Genre.DRAMA));
        movieOld4.setDirector(directorOld4);
        movieOld4.setReleaseDate(LocalDate.of(1958, 2, 3));
        movieOld4.setMinAge(MinAge.AGE_12);
        movieService.updateStatus(movieOld4, MovieStatus.VOTED);
        movieOld4.setDuration(96);
        movieOld4.setTrailerUrl("https://www.youtube.com/embed/hiyJZP-MlxM");
        movieOld4.setImageUrl("/uploads/12HombresSinPiedad.jpg");
        movieOld4.setSinopsis("Un miembro del jurado trata de evitar un error judicial obligando al resto del jurado " +
                "a reconsiderar las pruebas.");

        movieRepository.saveAll(
                List.of(movie1, movie2, movie3, movie4, movie5,  movie6, movie7, movie8,
                        movieOld1, movieOld2, movieOld3, movieOld4)
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
        r6.setCapacity(65);
        r6.setPrice(8.99);
        r6.setScreentype(ScreenType.STANDARD);

        Room r7 = new Room();
        r7.setName("Sala 7");
        r7.setCapacity(75);
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

        List<Movie> peliculas = List.of(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movieOld1);

        for (Movie pelicula : peliculas) {
            for (int dia = 0; dia < 5; dia++) {   // hoy (0), mañana (1)... hasta 5 días
                sessionRepository.save(Session.builder().movie(pelicula).startTime(LocalDateTime.now().plusDays(dia).withHour(
                        14).withMinute(0)).language(Language.ESP).room(r1).numAds(3).build());
                sessionRepository.save(Session.builder().movie(pelicula).startTime(LocalDateTime.now().plusDays(dia).withHour(
                        17).withMinute(45)).language(Language.VOSE).room(r2).numAds(3).build());
                sessionRepository.save(Session.builder().movie(pelicula).startTime(LocalDateTime.now().plusDays(dia).withHour(
                        19).withMinute(0)).language(Language.VO).room(r2).numAds(3).build());
                sessionRepository.save(Session.builder().movie(pelicula).startTime(LocalDateTime.now().plusDays(dia).withHour(
                        22).withMinute(30)).language(Language.ESP).room(r3).numAds(3).build());
                sessionRepository.save(Session.builder().movie(pelicula).startTime(LocalDateTime.now().plusDays(dia).withHour(
                        23).withMinute(0)).language(Language.VOSE).room(r4).numAds(3).build());
            }
        }

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

        Movie superMario = movieRepository.findAll().stream()
                .filter(m -> "Super Mario Galaxy".equalsIgnoreCase(m.getTitle()))
                .findFirst()
                .orElseGet(() -> {
                    Movie m = new Movie();
                    m.setTitle("Super Mario Galaxy");
                    m.setActive(Boolean.TRUE);
                    m.setSinopsis("A wonderful space adventure with Mario.");
                    m.setReleaseDate(LocalDate.of(2024, 12, 1));
                    m.setDuration(95);
                    m.setImageUrl("/uploads/SuperMarioGalaxy.jpg");
                    return movieRepository.save(m);
                });

        // Buscamos o creamos la película "Hokum" (mala)
        Movie hokum = movieRepository.findAll().stream()
                .filter(m -> "Hokum".equalsIgnoreCase(m.getTitle()))
                .findFirst()
                .orElseGet(() -> {
                    Movie m = new Movie();
                    m.setTitle("Hokum");
                    m.setActive(Boolean.TRUE);
                    m.setSinopsis("A controversial low-rated title.");
                    m.setReleaseDate(LocalDate.of(2025, 1, 1));
                    m.setDuration(80);
                    m.setImageUrl("/uploads/Hokum.jpg");
                    return movieRepository.save(m);
                });

        java.util.Random rnd = new java.util.Random();

        // Rellenar reseñas para Super Mario Galaxy con ratings elevados (cercanos a 5)
        for (int i = 1; i <= 12; i++) {
            int r;
            int pick = rnd.nextInt(100);
            if (pick < 70) r = 5; // 70%  -> 5
            else if (pick < 90) r = 4; // 20% -> 4
            else r = 3; // 10% -> 3

            Review rev = Review.builder()
                    .title("Reseña " + i + " - Super Mario Galaxy")
                    .description("Gran película, disfrutable y llena de nostalgia.")
                    .rating(r)
                    .movie(superMario)
                    .build();
            reviewRepository.save(rev);
        }

        // Rellenar reseñas para Hokum con ratings bajos (cercanos a 1)
        for (int i = 1; i <= 12; i++) {
            int r;
            int pick = rnd.nextInt(100);
            if (pick < 70) r = 1; // 70% -> 1
            else if (pick < 90) r = 2; // 20% -> 2
            else r = 3; // 10% -> 3

            Review rev = Review.builder()
                    .title("Reseña " + i + " - Hokum")
                    .description("No recomendada: mala ejecución y trama pobre.")
                    .rating(r)
                    .movie(hokum)
                    .build();
            reviewRepository.save(rev);
        }
    }

}
