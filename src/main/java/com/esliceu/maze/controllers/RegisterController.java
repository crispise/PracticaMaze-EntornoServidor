package com.esliceu.maze.controllers;

import com.esliceu.maze.exceptions.NameTooShortException;
import com.esliceu.maze.exceptions.PasswordTooShortException;
import com.esliceu.maze.exceptions.UserExistsException;
import com.esliceu.maze.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @RequestParam String name, @RequestParam String username, @RequestParam String password) {
        try {
            registerService.registerUser(name, username, password);
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "El registro se ha realizado correctamente");
        } catch (UserExistsException userException) {
            model.addAttribute("messageType", "errorUserExists");
            model.addAttribute("message", "El username ya existe.");
        } catch (PasswordTooShortException passwordTooShortException) {
            model.addAttribute("messageType", "errorPassword");
            model.addAttribute("message", "La contraseña tiene que tener un mínimo de 5 carácteres.");
        } catch (NameTooShortException nameTooShortException) {
            model.addAttribute("messageType", "errorName");
            model.addAttribute("message", "El nombre es demasiado corto, tiene que tener 6 carácteres.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "register";
    }

}
