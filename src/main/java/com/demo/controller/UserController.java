package com.demo.controller;

import com.demo.model.User;
import com.demo.model.enums.Role;
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
//        model.addAttribute("userStats", userService.findStatsById(id));
        return "users/user-detail";
    }

    @GetMapping("admin/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        model.addAttribute("edit", false);
        return "users/user-form";
    }

    @GetMapping("admin/users/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        User user = userService.findById(id);
        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("edit", true);
        return "users/user-form";
    }
}
