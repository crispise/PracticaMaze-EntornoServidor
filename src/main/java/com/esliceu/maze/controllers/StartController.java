package com.esliceu.maze.controllers;

import com.esliceu.maze.model.Map;
import com.esliceu.maze.services.StartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StartController {
    @Autowired
    StartService startService;

    @GetMapping("/start")
    public String startPage(Model m, HttpSession session) {
        String username = (String) session.getAttribute("user");
        System.out.println("start controller");
        String hasGameMapId = String.valueOf(startService.checkIfUserHasGame(username));
        if (!hasGameMapId.equals("0")) {
            System.out.println("estas dentro de que si hay habitacion");
            String jsonToSend = startService.getFirstJson(hasGameMapId, username);
            System.out.println("el string json es");
            System.out.println(jsonToSend);
            m.addAttribute("jsonInfo", jsonToSend);
            return "game";
        }
        List<Map> maps = startService.getAllMaps();
        m.addAttribute("maps", maps);
        return "start";
    }

    @PostMapping("/start")
    public String selectMap(Model m, @RequestParam String mapId, HttpSession session) {
        String username = (String) session.getAttribute("user");
        String jsonToSend = startService.getFirstJson(mapId, username);
        m.addAttribute("jsonInfo", jsonToSend);
        return "game";
    }
}
