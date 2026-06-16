package com.demo.controller;

import com.demo.model.Review;
import com.demo.model.User;
import com.demo.model.enums.Role;
import com.demo.repository.MovieRepository;
import com.demo.repository.ReviewRepository;
import com.demo.repository.UserRepository;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @GetMapping("reviews")
    public String reviews(Model model, @AuthenticationPrincipal User currentUser) {
        List<Review> reviews = reviewRepository.findAllByOrderByCreationDateDesc();
        if (reviews == null) {
            reviews = Collections.emptyList();
        }
        model.addAttribute("reviews", reviews);

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users != null ? users : Collections.emptyList());

        model.addAttribute("currentUser", currentUser);
        return "reviews/review-list";
    }

    @GetMapping("reviews/new")
    public String newReview(
            @RequestParam(required = false) Long movieId,
            Model model,
            @AuthenticationPrincipal User currentUser,
            RedirectAttributes redirectAttributes) {

        // Verificar que el usuario esté autenticado
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para escribir una reseña.");
            return "redirect:/login";
        }

        Review review = new Review();

        // Si se proporciona movieId, buscar la película y asignarla
        if (movieId != null) {
            Optional<com.demo.model.Movie> movieOpt = movieRepository.findById(movieId);
            if (movieOpt.isPresent()) {
                review.setMovie(movieOpt.get());
            } else {
                redirectAttributes.addFlashAttribute("error", "Película no encontrada.");
                return "redirect:/movies";
            }
        }

        model.addAttribute("review", review);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users != null ? users : Collections.emptyList());
        model.addAttribute("currentUser", currentUser);
        return "reviews/review-form";
    }


    @GetMapping("reviews/{id}")
    public String review(Model model, @PathVariable Long id, @AuthenticationPrincipal User currentUser, RedirectAttributes redirectAttributes){
        Optional<Review> opt = reviewRepository.findById(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Reseña no encontrada.");
            return "redirect:/reviews";
        }
        model.addAttribute("review", opt.get());
        model.addAttribute("currentUser", currentUser);
        return "reviews/review-detail";
    }

    @GetMapping("reviews/edit/{id}")
    public String editReview(Model model, @PathVariable Long id, @AuthenticationPrincipal User currentUser, RedirectAttributes redirectAttributes) {
        Optional<Review> opt = reviewRepository.findById(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Reseña no encontrada.");
            return "redirect:/reviews";
        }
        Review review = opt.get();

        // Seguridad: solo el autor o ADMIN puede editar
        Long ownerId = review.getUser() != null ? review.getUser().getId() : null;
        boolean isOwner = ownerId != null && currentUser != null && ownerId.equals(currentUser.getId());
        boolean isAdmin = currentUser != null && currentUser.getRole() == Role.ROLE_ADMIN;

        if (!isOwner && !isAdmin) {
            redirectAttributes.addFlashAttribute("error", "No autorizado para editar esta reseña.");
            return "redirect:/reviews";
        }

        model.addAttribute("review", review);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users != null ? users : Collections.emptyList());
        model.addAttribute("currentUser", currentUser);
        return "reviews/review-form";
    }

    @PostMapping("reviews")
    public String saveReview(
            @Valid @ModelAttribute Review review,
            BindingResult bindingResult,
            @AuthenticationPrincipal User currentUser,
            RedirectAttributes redirectAttributes) {

        // Comprobación crítica: asegurarse de que hay usuario autenticado
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para crear una reseña.");
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "reviews/review-form";
        }

        // Si es edición (id no nulo) comprobamos autoría; si es nueva, asignamos el usuario autenticado
        if (review.getId() != null) {
            Optional<Review> opt = reviewRepository.findById(review.getId());
            if (opt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Reseña no encontrada.");
                return "redirect:/reviews";
            }
            var persisted = opt.get();

            // si el persisted.user es null tratamos como no autorizado
            Long ownerId = persisted.getUser() != null ? persisted.getUser().getId() : null;

            boolean isOwner = ownerId != null && ownerId.equals(currentUser.getId());
            boolean isAdmin = currentUser.getRole() == Role.ROLE_ADMIN;

            if (!isOwner && !isAdmin) {
                redirectAttributes.addFlashAttribute("error", "No autorizado para modificar esta reseña.");
                return "redirect:/reviews";
            }

            // asegurarnos de no cambiar el propietario al guardar
            review.setUser(persisted.getUser());
        } else {
            // creación nueva -> asignar autor (currentUser ya fue comprobado antes que no es null)
            review.setUser(currentUser);
        }

        if (review.getMovie() != null && review.getMovie().getId() != null) {
            Optional<com.demo.model.Movie> movieOpt = movieRepository.findById(review.getMovie().getId());
            if (movieOpt.isPresent()) {
                review.setMovie(movieOpt.get());
            }
        }

        reviewRepository.save(review);
        redirectAttributes.addFlashAttribute("message", "¡Reseña guardada exitosamente!");
        return "redirect:/reviews";
    }
}
