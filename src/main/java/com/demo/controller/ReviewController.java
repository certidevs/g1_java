package com.demo.controller;

import com.demo.model.Review;
import com.demo.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class ReviewController {

    // Inyectar el repositorio de reviews
    private final ReviewRepository reviewRepository;

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

    @PostMapping("reviews")
    public String saveReview(@ModelAttribute Review review, RedirectAttributes redirectAttributes){
        reviewRepository.save(review);
        redirectAttributes.addFlashAttribute("message", "¡Reseña guardada exitosamente!");
        return "redirect:/reviews";
    }



}

