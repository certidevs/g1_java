package com.demo.config;

import com.demo.dto.RegisterForm;
import com.demo.model.Favorite;
import com.demo.model.Movie;
import com.demo.model.User;
import com.demo.model.enums.Genre;
import com.demo.model.enums.Role;
import com.demo.repository.DirectorRepository;
import com.demo.repository.FavoriteRepository;
import com.demo.repository.MovieRepository;
import com.demo.repository.ReviewRepository;
import com.demo.model.Review;
import com.demo.repository.UserRepository;
import com.demo.service.FavoriteService;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.EnumSet;

/*
Clase para insertar datos demo en el entorno de desarrollo
 */
@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
  //  private final DirectorRepository directorRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        // opcion usando el service:
        User user = userService.register(RegisterForm.builder()
                .username("user")
                .email("user@gmail.com")
                .password("user")
                .passwordConfirm("user")
                .build());

        // opcion usando directamente repository
        User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ROLE_ADMIN)
                .active(true)
                .imageUrl("/uploads/ollama.png")
                .build());

        // session
        // startTime LocalDateTime.now()
        // startTime LocalDateTime.now().plusDays(1)
        // startTime LocalDateTime.now().plusDays(2)
        // startTime LocalDateTime.now().plusDays(3)


        // Crear una movie
        Movie movie = new Movie();
        movie.setTitle("Película para fav");
        movie.setActive(Boolean.TRUE);
        movie.setSinopsis("Descripción de la película para fav");
        movie.setReleaseDate(LocalDate.of(2026, 4, 24));
       // movie.setDirector(directorRepository.findById(1L).orElse(null)); no se como llamar al director desde aqui we
        movie.setDuration(120);
        movie.setGenreSet(EnumSet.of(Genre.ADVENTURE, Genre.COMEDY));
        movie.setImageUrl("/uploads/ollama.png");
        movieRepository.save(movie);

        favoriteRepository.save(Favorite.builder().movie(movie).user(user).build());

        // guardarlos como favoritos de un usuario
        // así los podemos mostrar en el user-detail.html

        // ------ Insertar reseñas de ejemplo para dos películas ------
        // Buscamos o creamos la película "Super Mario Galaxy" (buena)
//        Movie superMario = movieRepository.findAll().stream()
//                .filter(m -> "Super Mario Galaxy".equalsIgnoreCase(m.getTitle()))
//                .findFirst()
//                .orElseGet(() -> {
//                    Movie m = new Movie();
//                    m.setTitle("Super Mario Galaxy");
//                    m.setActive(Boolean.TRUE);
//                    m.setSinopsis("A wonderful space adventure with Mario.");
//                    m.setReleaseDate(LocalDate.of(2024, 12, 1));
//                    m.setDuration(95);
//                    m.setImageUrl("/uploads/SuperMarioGalaxy.jpg");
//                    return movieRepository.save(m);
//                });
//
//        // Buscamos o creamos la película "Hokum" (mala)
//        Movie hokum = movieRepository.findAll().stream()
//                .filter(m -> "Hokum".equalsIgnoreCase(m.getTitle()))
//                .findFirst()
//                .orElseGet(() -> {
//                    Movie m = new Movie();
//                    m.setTitle("Hokum");
//                    m.setActive(Boolean.TRUE);
//                    m.setSinopsis("A controversial low-rated title.");
//                    m.setReleaseDate(LocalDate.of(2025, 1, 1));
//                    m.setDuration(80);
//                    m.setImageUrl("/uploads/Hokum.jpg");
//                    return movieRepository.save(m);
//                });
//
//        // Crear otro usuario para variar autores de reseñas
//        User reviewer = userRepository.findByUsername("reviewer").orElseGet(() ->
//                userRepository.save(User.builder()
//                        .username("reviewer")
//                        .email("reviewer@gmail.com")
//                        .password(passwordEncoder.encode("reviewer"))
//                        .role(Role.ROLE_USER)
//                        .active(true)
//                        .build())
//        );
//
//        java.util.Random rnd = new java.util.Random();
//
//        // Rellenar reseñas para Super Mario Galaxy con ratings elevados (cercanos a 5)
//        for (int i = 1; i <= 12; i++) {
//            int r;
//            int pick = rnd.nextInt(100);
//            if (pick < 70) r = 5; // 70%  -> 5
//            else if (pick < 90) r = 4; // 20% -> 4
//            else r = 3; // 10% -> 3
//
//            Review rev = Review.builder()
//                    .title("Reseña " + i + " - Super Mario Galaxy")
//                    .description("Gran película, disfrutable y llena de nostalgia.")
//                    .rating(r)
//                    .movie(superMario)
//                    .user(rnd.nextBoolean() ? user : reviewer)
//                    .build();
//            reviewRepository.save(rev);
//        }
//
//        // Rellenar reseñas para Hokum con ratings bajos (cercanos a 1)
//        for (int i = 1; i <= 12; i++) {
//            int r;
//            int pick = rnd.nextInt(100);
//            if (pick < 70) r = 1; // 70% -> 1
//            else if (pick < 90) r = 2; // 20% -> 2
//            else r = 3; // 10% -> 3
//
//            Review rev = Review.builder()
//                    .title("Reseña " + i + " - Hokum")
//                    .description("No recomendada: mala ejecución y trama pobre.")
//                    .rating(r)
//                    .movie(hokum)
//                    .user(rnd.nextBoolean() ? user : reviewer)
//                    .build();
//            reviewRepository.save(rev);
//        }


    }
}
