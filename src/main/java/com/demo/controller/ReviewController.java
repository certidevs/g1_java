package com.demo.controller;

import com.demo.model.Review;
import com.demo.repository.MovieRepository;
import com.demo.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class ReviewController {

    // Inyectar el repositorio de reviews
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    //getmapping reviews
    @GetMapping("reviews")
    public String reviews(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
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
        return "reviews/review-form";
    }

    @PostMapping("reviews")
    public String saveReview(@ModelAttribute Review review, RedirectAttributes redirectAttributes){
        reviewRepository.save(review);

//        if (review.getMovie() != null)
//            return "redirect:/movies/" + review.getMovie().getId();
            redirectAttributes.addFlashAttribute("message", "¡Reseña guardada exitosamente!");
        return "redirect:/reviews";
    }



}

