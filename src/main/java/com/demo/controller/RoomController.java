package com.demo.controller;

import com.demo.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RoomController {

    // inyectar el room repository
    private final RoomRepository roomRepository;

    //getmapping rooms
    @GetMapping("/rooms")
    public String roomsList(Model model){
        model.addAttribute("rooms", roomRepository.findAll());
        return "rooms/room-list";}
}
