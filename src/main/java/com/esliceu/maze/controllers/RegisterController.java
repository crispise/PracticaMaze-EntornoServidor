package com.esliceu.maze.controllers;

import com.esliceu.maze.model.User;
import com.esliceu.maze.services.RegisterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;
    @GetMapping("/register")
    public String getLogin() {
        return "register";
    }

    @PostMapping("/register")
    public String register(HttpSession session, @RequestParam String name, @RequestParam String username, @RequestParam String password) {
        registerService.registerUser(name, username, password);
        return "redirect:/login";
    }

}
