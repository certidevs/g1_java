package com.demo.controller;

import com.demo.model.Room;
import com.demo.model.enums.ScreenType;
import com.demo.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class RoomController {
        private final RoomRepository roomRepository;

    // Lista todas las salas
    @GetMapping("/rooms")
    public String roomsList(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("numRooms", rooms.size());
        model.addAttribute("title", "Lista de salas");
        return "rooms/room-list";
    }

    // Detalle de una sala por ID
    @GetMapping("/rooms/{id}")
    public String roomDetail(Model model, @PathVariable Long id) {
        model.addAttribute("room", roomRepository.findById(id).orElse(null));
        return "rooms/room-detail";
    }

    // Filtrar por capacidad mayor o igual que
    @GetMapping("/rooms/capacity/{capacity}")
    public String roomsListCapacity(Model model, @PathVariable Integer capacity) {
        model.addAttribute("rooms", roomRepository.findByCapacityGreaterThanEqual(capacity));
        model.addAttribute("numRooms", roomRepository.findByCapacityGreaterThanEqual(capacity).size());
        model.addAttribute("title", "Salas con capacidad mayor o igual a " + capacity);
        return "rooms/room-list";
    }

    // Filtrar por tipo de pantalla
    @GetMapping("/rooms/screentype/{screenType}")
    public String roomsListScreenType(Model model, @PathVariable ScreenType screenType) {
        List<Room> rooms = roomRepository.findByScreentype(screenType);
        model.addAttribute("rooms", roomRepository.findByScreentype(screenType));
        model.addAttribute("numRooms", rooms.size());
        model.addAttribute("title", "Salas con tipo de pantalla " + screenType);
        return "rooms/room-list";
    }

    // Crear una nueva sala desde cero
    @GetMapping("/rooms/new")
    public String newRoom(Model model) {
        // añadir objeto Room vacío para rellenarlo desde el formulario
        model.addAttribute("room", new Room());
        model.addAttribute("screenTypes", ScreenType.values());
        // datos para el formulario
        return "rooms/room-form";
    }

    // Editar una sala existente
    @GetMapping("rooms/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomRepository.findById(id).orElseThrow());
        model.addAttribute("screenTypes", ScreenType.values());
        return "rooms/room-form";
    }

    @PostMapping("rooms")
    public String createRoom(@ModelAttribute Room room) {
        System.out.println("Sala recibida: " + room);
        roomRepository.save(room);
        return "redirect:/rooms/" + room.getId();
    }

}
