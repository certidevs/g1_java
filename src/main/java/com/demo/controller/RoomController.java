package com.demo.controller;

import com.demo.model.enums.ScreenType;
import com.demo.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class RoomController {
        private final RoomRepository roomRepository;

    // Lista todas las salas
    @GetMapping("/rooms")
    public String roomsList(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "rooms/room-list";
    }

    // Detalle de una sala por ID
    @GetMapping("/rooms/{id}")
    public String roomDetail(Model model, @PathVariable Long id) {
        model.addAttribute("room", roomRepository.findById(id).orElse(null));
        return "rooms/room-detail";
    }

    // Filtrar por capacidad mayor o igual que
    @GetMapping("/rooms/{capacity}")
    public String roomsListCapacity(Model model, @PathVariable Integer capacity) {
        model.addAttribute("rooms", roomRepository.findByCapacityGreaterThanEqual(capacity));
        return "rooms/room-list";
    }

    // Filtrar por tipo de pantalla
    @GetMapping("/rooms/{screenType}")
    public String roomsListScreenType(Model model, @PathVariable ScreenType screenType) {
        model.addAttribute("rooms", roomRepository.findByScreentype(screenType));
        return "rooms/room-list";
    }
}
