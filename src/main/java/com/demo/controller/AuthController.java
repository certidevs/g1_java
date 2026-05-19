package com.demo.controller;

import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor

public class AuthController {

    private final UserService userService;

    // @GetMapping /register

    // @PostMapping /register

    // @GetMapping  /login

}
