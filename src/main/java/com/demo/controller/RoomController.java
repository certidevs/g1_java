package com.demo.controller;

import com.demo.model.Room;
import com.demo.model.enums.ScreenType;
import com.demo.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class RoomController {
    private final RoomRepository roomRepository;

    // Lista todas las salas activas con filtros
    @GetMapping("/rooms")
    public String roomsList(
            Model model,
            @RequestParam(required = false) ScreenType screenType,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String title
    ) {
        List<Room> rooms = roomRepository.findActiveFiltering(screenType, capacity, price, title);
        model.addAttribute("rooms", rooms);
        model.addAttribute("numRooms", rooms.size());
        model.addAttribute("title", "Lista de salas");
        return "rooms/room-list";
    }

    // Detalle de una sala activa por ID
    @GetMapping("/rooms/{id}")
    public String roomDetail(Model model, @PathVariable Long id) {

        // Room room = roomRepository.findByIdAndActiveTrue(id).orElse(null);

        model.addAttribute("room", roomRepository.findByIdAndActiveTrue(id).orElse(null));
        // model.addAttribute("sessions", room.getSessions());
        return "rooms/room-detail";
    }

    // Filtrar por capacidad mayor o igual que (solo activas)
    @GetMapping("/rooms/capacity/{capacity}")
    public String roomsListCapacity(Model model, @PathVariable Integer capacity) {
        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqualAndActiveTrue(capacity);
        model.addAttribute("rooms", rooms);
        model.addAttribute("numRooms", rooms.size());
        model.addAttribute("title", "Salas con capacidad mayor o igual a " + capacity);
        return "rooms/room-list";
    }

    // Filtrar por tipo de pantalla (solo activas)
    @GetMapping("/rooms/screentype/{screenType}")
    public String roomsListScreenType(Model model, @PathVariable ScreenType screenType) {
        List<Room> rooms = roomRepository.findByScreentypeAndActiveTrue(screenType);
        model.addAttribute("rooms", rooms);
        model.addAttribute("numRooms", rooms.size());
        model.addAttribute("title", "Salas con tipo de pantalla " + screenType);
        return "rooms/room-list";
    }

    // Crear una nueva sala desde cero
    @GetMapping("/rooms/new")
    public String newRoom(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("screenTypes", ScreenType.values());
        return "rooms/room-form";
    }

    // Editar una sala existente
    @GetMapping("/rooms/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomRepository.findById(id).orElseThrow());
        model.addAttribute("screenTypes", ScreenType.values());
        return "rooms/room-form";
    }

    // Desactivar una sala
    @GetMapping("/rooms/deactivate/{id}")
    public String roomDeactivate(@PathVariable Long id) {  // Removí 'Model model' ya que no se usa
        Optional<Room> roomOptional = roomRepository.findByIdAndActiveTrue(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setActive(false);
            roomRepository.save(room);
        }
        return "redirect:/rooms";
    }

    @PostMapping("/rooms")
    public String createRoom(@ModelAttribute Room room) {
        System.out.println("Sala recibida: " + room);
        roomRepository.save(room);
        return "redirect:/rooms/" + room.getId();
    }
}