package com.esliceu.maze.controllers;

import com.esliceu.maze.services.ResetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResetController {
    @Autowired
    ResetService resetService;
    @GetMapping("/reset")
    public String reset (Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        String jsonToSend = resetService.resetGame(username);
        m.addAttribute("jsonInfo", jsonToSend);
        return "game";
    }
}
