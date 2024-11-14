package com.esliceu.maze.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class StartController {
    @GetMapping("/start")
    public String startPage(Model m, @SessionAttribute String user, @RequestParam(required = false) Integer mapId) {
        if (mapId != null) {
            return "redirect:/start?mapId=" + mapId;
        } else {
            return "start"; // Retornar la vista para elegir un mapa
        }
    }

    @PostMapping("/start")
    public String selectMap(Model m, @RequestParam int mapId){

        return "redirect:/start?mapId=" + mapId;
    }


}
