package com.esliceu.maze.controllers;

import com.esliceu.maze.services.NavService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NavController {
    @Autowired
    NavService navService;
    @GetMapping("/nav")
    public String move(Model m, @RequestParam("dir") String direction, HttpSession session){
        String username = (String) session.getAttribute("user");
        String jsonToSend = navService.trySelectedDirection(direction, username);
        m.addAttribute("jsonInfo", jsonToSend);
        return "game";
    }
}
