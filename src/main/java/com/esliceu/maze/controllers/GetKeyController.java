package com.esliceu.maze.controllers;

import com.esliceu.maze.services.GetKeyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetKeyController {
    @Autowired
    GetKeyService getKeyService;

    @GetMapping("/getkey")
    public String getKey(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        String jsonToSend = getKeyService.ckeckClickInKey(username);
        m.addAttribute("jsonInfo", jsonToSend);
        return "game";
    }
}
