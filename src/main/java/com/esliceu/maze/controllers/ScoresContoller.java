package com.esliceu.maze.controllers;

import com.esliceu.maze.services.ScoresService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScoresContoller {
    @Autowired
    ScoresService scoresService;
    @GetMapping
    public String getScores(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        String jsonToSend = scoresService.obtainScores(username);
        m.addAttribute("jsonInfo", jsonToSend);
        return "scores";
    }
}
