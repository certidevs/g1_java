package com.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        return http.build();
    }
}
