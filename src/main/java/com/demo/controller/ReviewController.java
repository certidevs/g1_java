package com.demo.controller;

import com.demo.model.Review;
import com.demo.model.User;
import com.demo.repository.MovieRepository;
import com.demo.repository.ReviewRepository;
import com.demo.repository.UserRepository;
import jakarta.validation.Valid;
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

    // Inyectar el repositorio de reviews
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    //getmapping reviews
    @GetMapping("reviews")
    public String reviews(Model model) {
        model.addAttribute("reviews", reviewRepository.findAllByOrderByCreationDateDesc());
        model.addAttribute("users", userRepository.findAll());
        return "reviews/review-list";
    }

    @GetMapping("reviews/{id}")
    public String review(Model model, @PathVariable Long id){
        model.addAttribute("review", reviewRepository.findById(id).orElseThrow());
        return "reviews/review-detail";
    }

    @GetMapping("reviews/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        reviewRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "¡Borrado exitoso!");
        return "redirect:/reviews";
    }

    // Get Mapping reviews / edit / {id}
    @GetMapping("reviews/edit/{id}")
    public String editReview(Model model, @PathVariable Long id) {
        model.addAttribute("review", reviewRepository.findById(id).orElseThrow());
        model.addAttribute("users", userRepository.findAll());
        return "reviews/review-form";
    }

    // @GetMapping reviews/new reviews-form.html
    // movieId
    @GetMapping("reviews/new")
    public String newReview(
            Model model,
            @RequestParam(required = false) Long movieId) {
        Review review = new Review();

        if (movieId != null)
            review.setMovie(movieRepository.findById(movieId).orElseThrow());

        model.addAttribute("review", review);
        model.addAttribute("users", userRepository.findAll());
        return "reviews/review-form";
    }

    @PostMapping("reviews")
    public String saveReview(
            @Valid @ModelAttribute Review review,
            BindingResult bindingResult,
            @AuthenticationPrincipal User user,
            RedirectAttributes redirectAttributes) {
        //  usuario que crea/modifica la review
        if (bindingResult.hasErrors()) {
            return "reviews/review-form";
        }

        review.setUser(user);

        if (review.getMovie() != null && review.getMovie().getId() != null) {
            review.setMovie(movieRepository.findById(review.getMovie().getId()).orElseThrow());
        }

        reviewRepository.save(review);
        redirectAttributes.addFlashAttribute("message", "¡Reseña guardada exitosamente!");
        return "redirect:/reviews";
    }




}

