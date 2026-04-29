package com.demo.controller;

import com.demo.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RoomController {
    private final RoomRepository roomRepository;
    @GetMapping("/rooms")
    public String roomsList(Model model){
        return "room/room-list";}
}
