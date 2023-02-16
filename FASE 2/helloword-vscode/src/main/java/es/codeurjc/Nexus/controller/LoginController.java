package es.codeurjc.Nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.Nexus.service.UserService;
import es.codeurjc.NexusApplication.model.User;

public class LoginController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
   
    @PostMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.getUserByUsername(username);
        if (user == null || !user.getEncodedPassword().equals(password)) {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "login";
        } else {
            model.addAttribute("username", username);
            return "redirect:/home";
        }
    }

}

}