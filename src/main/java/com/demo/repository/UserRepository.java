package com.demo.repository;

import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Registro
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    //Login
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}