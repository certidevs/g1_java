package com.demo.service;

import com.demo.dto.RegisterForm;
import com.demo.dto.UserStats;
import com.demo.model.User;
import com.demo.model.enums.Role;
import com.demo.repository.ReviewRepository;
import com.demo.repository.TicketRepository;
import com.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;
    private final TicketRepository ticketRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new UsernameNotFoundException("Usuario no encontrado con username: " + username);
        }
    }

    public User register(RegisterForm form) {

        if (userRepository.existsByUsername(form.getUsername()))
            throw new IllegalArgumentException("El nombre de usuario ya existe");

        if (userRepository.existsByEmail(form.getEmail()))
            throw new IllegalArgumentException("El correo electrónico ya existe");

        if (! form.getPassword().equals(form.getPasswordConfirm()))
            throw new IllegalArgumentException("Las contraseñas no coinciden");

//        if (! form.getAcceptTermsAndConditions())
//            throw new IllegalArgumentException("Obligatorio aceptar términos y condiciones de la plataforma.");


        User user = new User();
        user.setUsername(form.getUsername()); // el username se podría generar automáticamente en base al email
        user.setEmail(form.getEmail());
        // user.setPassword(form.getPassword()); // texto plano sin cifrar  admin
        user.setPassword(passwordEncoder.encode(form.getPassword())); // password cifrada con bcrypt
        user.setRole(Role.ROLE_USER); // por defecto no asignamos rol de admin a usuarios nuevos
        user.setActive(true);
        return userRepository.save(user); // guarda el usuario en base de datos
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public UserStats findStatsById(Long id) {
        return new UserStats(
                reviewRepository.countByUser_Id(id),
                reviewRepository.findByUser_Id(id),
                ticketRepository.countByUser_Id(id),
                ticketRepository.findByUser_IdOrderByPurchaseTime(id),
                ticketRepository.calculateTotalMoneySpentByUserId(id)
        );
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        if (userRepository.existsByEmail(user.getEmail()))
            throw new IllegalArgumentException("El correo ya existe");
//        if (user.getPassword() == null || user.getPassword().isBlank())
//            throw new IllegalArgumentException("La contraseña es obligatoria");
        if (!StringUtils.hasText(user.getPassword()))
            throw new IllegalArgumentException("La contraseña es obligatoria");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User userForm) {
        User userDB = findById(userForm.getId());
        Optional<User> userOpt = userRepository.findByUsername(userForm.getUsername());
        if (userOpt.isPresent() && !userOpt.get().getId().equals(userDB.getId()))
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        userRepository.findByEmail(userForm.getEmail())
                .filter(user -> !user.getId().equals(userForm.getId()))
                .ifPresent(user -> {
                    throw new IllegalArgumentException("El email de usuario ya existe");
                });
        userDB.setUsername(userForm.getUsername());
        userDB.setEmail(userForm.getEmail());
        userDB.setRole(userForm.getRole());
        userDB.setImageUrl(userForm.getImageUrl());
        // TODO un admin podría desactivarse a sí mismo, hay que impedirlo lanzando Illegal
        userDB.setActive(userForm.getActive());
        if (StringUtils.hasText(userForm.getPassword()))
            userDB.setPassword(passwordEncoder.encode(userForm.getPassword()));

        return userRepository.save(userDB); // guardamos el usuario actualizado en base de datos
    }
}
