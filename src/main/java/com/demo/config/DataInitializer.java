package com.demo.config;

import com.demo.dto.RegisterForm;
import com.demo.model.Favorite;
import com.demo.model.Movie;
import com.demo.model.User;
import com.demo.model.enums.Genre;
import com.demo.model.enums.Role;
import com.demo.repository.FavoriteRepository;
import com.demo.repository.MovieRepository;
import com.demo.repository.UserRepository;
import com.demo.service.FavoriteService;
import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/*
Clase para insertar datos demo en el entorno de desarrollo
 */
@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
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
        movie.setDuration(120);
        movie.setGenreSet(EnumSet.of(Genre.ADVENTURE, Genre.COMEDY));
        movie.setImageUrl("/uploads/ollama.png");
        movieRepository.save(movie);

        favoriteRepository.save(Favorite.builder().movie(movie).user(user).build());

        // guardarlos como favoritos de un usuario
        // así los podemos mostrar en el user-detail.html


    }
}
