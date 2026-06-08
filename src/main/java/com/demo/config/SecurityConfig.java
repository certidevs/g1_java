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

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // h2 usa iframes
        //Para proteger las rutas
        http.authorizeHttpRequests(
                auth -> auth

                        // rutas públicas tanto GET como POST
                        .requestMatchers("/login", "/register", "/uploads/**",
                                "/css/**", "/images/**", "/webjars/**").permitAll()

                        // separado de una en una

                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/index").permitAll()

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
                        .requestMatchers(HttpMethod.POST, "/sessions").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/edit/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/deactivate/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/*").permitAll()


                        .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reviews").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/new").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/edit/*").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/delete/*").hasRole("ADMIN")

                        // hecho por jose
                        .requestMatchers(HttpMethod.GET, "/tickets").authenticated()
                        .requestMatchers(HttpMethod.GET, "/tickets/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/tickets").authenticated()
                        .requestMatchers(HttpMethod.GET, "/tickets/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/tickets/edit/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/tickets/delete/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/tickets/buy/*").permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // lo demás autenticado si o si
                        .anyRequest().authenticated()
        );

//        ejecuta un forward interno al recurso indicado: el servidor renderiza otra cosa pero la URL del navegador se queda con la original
//        http.exceptionHandling(exception -> exception.accessDeniedPage("/movies"));
        // este sí redirecciona
        http.exceptionHandling(ex -> ex
                .accessDeniedHandler((request, response, e) ->
                        response.sendRedirect(request.getContextPath() + "/movies"))
        );
        http.formLogin(form ->
                form.loginPage("/login")
                        .defaultSuccessUrl("/movies", true)
                        .permitAll()
        );

        return http.build();
    }
}
