package com.demo.controller;


import com.demo.model.User;
import com.demo.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // toggle para marcar o desmarcar como favorito
    @PostMapping("favorites/toggle")
    public String toggle(
            @RequestParam String type,
            @RequestParam Long targetId,
            @RequestParam(defaultValue = "/movies") String redirectUrl,
            @AuthenticationPrincipal User user,
            RedirectAttributes redirectAttributes
    ) {

        boolean favorited;
        if(type.equalsIgnoreCase("movie")) {
            favorited = favoriteService.toggleMovie(user, targetId);
        }
//       else if(type.equalsIgnoreCase("session")) {
//        }
        else {
            return  "redirect:" + redirectUrl;
        }

        if(favorited) {
            redirectAttributes.addFlashAttribute("message", "añadido como favorito");
        } else {
            redirectAttributes.addFlashAttribute("message", "quitado como favorito");
        }
        return  "redirect:" + redirectUrl;
    }
}
