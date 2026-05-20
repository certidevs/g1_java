package com.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import java.util.Optional;

@Configuration
public class SecurityConfig  {
    //PAra cifrar password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // securityFilterChain para proteger acceso a rutas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Para proteger las rutas
        http.authorizeHttpRequests(
                auth -> auth

                        // rutas públicas tanto GET como POST
                        .requestMatchers("/login", "/register", "/css/**",
                                "/images/**", "/webjars/**").permitAll()

                        // separado de una en una
                        .requestMatchers(HttpMethod.GET, "/movies").permitAll()
                        .requestMatchers(HttpMethod.GET, "/movies/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/movies").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/movies/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/movies/edit/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/rooms").permitAll()
                        .requestMatchers(HttpMethod.GET, "/rooms/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rooms").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/rooms/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/rooms/edit/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/rooms/deactivate/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/sessions").permitAll()
                        .requestMatchers(HttpMethod.GET, "/sessions/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/sessions").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/edit/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/deactivate/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reviews").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/new").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/edit/*").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/delete/*").hasRole("ADMIN")

                        // solo user normal, no admin
//                .requestMatchers(HttpMethod.GET, "/tickets").hasRole("USER")
//                .requestMatchers(HttpMethod.GET, "/tickets/new").hasRole("USER")
//                .requestMatchers(HttpMethod.POST, "/tickets/*").hasRole("USER")

                        // todos los roles
                        .requestMatchers("/tickets", "/tickets/*").authenticated()

                        // lo demás autenticado si o si
                        .anyRequest().authenticated()
        );

        http.formLogin(form ->
                form.loginPage("/login")
                        .defaultSuccessUrl("/movies", true)
                        .permitAll()
        );

        return http.build();
    }
}
