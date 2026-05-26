package com.demo.controller;

import com.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@Controller
public class UserController {
    private UserService userService;

    // user list
    @GetMapping("admin/users")
    public String list(Model model){
        model.addAttribute("users", userService.findAll());
        return "users/user-list";
    }

    // user detail
    @GetMapping("admin/users/{id}")
    public String detail(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "users/user-detail";
    }
}
