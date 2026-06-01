package com.demo.controller;

import com.demo.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // toggle para marcar o desmarcar como favorito
}
