package com.esliceu.maze.controllers;

import com.esliceu.maze.services.GetCoinService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetCoinController {
    @Autowired
    GetCoinService getCoinService;

    @GetMapping("/getcoin")
    public String getCoin(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        String jsonToSend = getCoinService.addCoinToUser(username);
        m.addAttribute("jsonInfo", jsonToSend);
        return "game";
    }
}
