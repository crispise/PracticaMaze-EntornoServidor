package com.esliceu.maze.controllers;

import com.esliceu.maze.services.ResetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResetController {
    @Autowired
    ResetService resetService;

    @GetMapping("/reset")
    public String reset(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        resetService.resetGame(username);
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/resetGame")
    public String restartGame(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        resetService.resetGame(username);
        return "redirect:/start";
    }

}
