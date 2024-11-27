package com.esliceu.maze.controllers;
import com.esliceu.maze.services.OpenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OpenController {
    @Autowired
    OpenService openService;
    @GetMapping("/open")
    public String openDoor(Model m, HttpSession session, @RequestParam("dir") String direction) {
        System.out.println("esta en el controlleer");
        String username = (String) session.getAttribute("user");
        String jsonToSend = openService.tryOpenDoor(direction, username);
        System.out.println("json que se envia");
        System.out.println(jsonToSend);
        m.addAttribute("jsonInfo", jsonToSend);
        return "game";
    }
}
