package com.demo.controller;

import com.demo.model.User;
import com.demo.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

/*
Advice para exponer información global accesible desde cualquier
HTML para evitar repetirla en todos los controladores
 */
@ControllerAdvice(annotations = Controller.class)
@AllArgsConstructor
public class GlobalModelAttributes {

    private final FavoriteService favoriteService;

    @ModelAttribute("favoriteMovieIds")
    public Set<Long> getFavoriteMovieIds(@AuthenticationPrincipal User user) {
        if(user != null) {
            return favoriteService.findMovieIdsByUserId(user.getId());
        }
        return Set.of();
    }
}