package com.esliceu.maze.controllers;

import com.esliceu.maze.model.Score;
import com.esliceu.maze.model.User;
import com.esliceu.maze.services.ScoresService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ScoresContoller {
    @Autowired
    ScoresService scoresService;
    @GetMapping("/scores")
    public String getScores(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        List <Score> usersScores  = scoresService.obtainScores(username);
        System.out.println("si que entra en el get");
        System.out.println(usersScores);
        m.addAttribute("usersScore", usersScores);
        return "scores";
    }

    @PostMapping("/scores")
    public String getFormScores(Model m, HttpSession session, @RequestParam String gameComent) {
        System.out.println("entra en el post");
        String username = (String) session.getAttribute("user");
        scoresService.updateScores(username, gameComent);
        List <Score> usersScores  = scoresService.obtainScores(username);
        m.addAttribute("usersScore", usersScores);
        return "scores";
    }
}
