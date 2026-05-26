package com.demo.controller;

import com.demo.model.Director;
import com.demo.repository.DirectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class DirectorController {
    private final DirectorRepository directorRepository;

//    @GetMapping("directors")
//    public String directorList(Model model) {
//        model.addAttribute("directors", directorRepository.findAll());
//        return "directors/director-list";
//    }
//
//    @GetMapping("directors/{id}")
//    public String directorDetail(@PathVariable Long id, Model model) {
//        model.addAttribute("director", directorRepository.findById(id).orElseThrow());
//        return "directors/director-detail";
//    }

    @GetMapping("directors/new")
    public String newDirector(@RequestParam(required = false) String returnTo, Model model) {
        model.addAttribute("director", new Director());
        model.addAttribute("returnTo", returnTo);
        return "directors/director-form";
    }

    @PostMapping("directors")
    public String saveDirector(@ModelAttribute Director director,
                               @RequestParam(required = false) String returnTo,
                               RedirectAttributes redirect) {
        directorRepository.save(director);
        redirect.addFlashAttribute("message", "Director creado");

        // Validación contra open-redirect: solo rutas internas
        if (returnTo != null && returnTo.startsWith("/") && !returnTo.startsWith("//")) {
            return "redirect:" + returnTo;
        }
        return "redirect:/directors";
    }
}