package es.codeurjc.Nexus.controller;

import java.util.Optional;

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
        Optional<User> user = userService.getUserByUsername(username);

        if (user.isEmpty()) {
            model.addAttribute("error", "Username does not exist");
            return "login";
        } 
        else if (!user.get().getEncodedPassword().equals(password)) {
            model.addAttribute("error", "Password is incorrect");
            return "login";
        } 
        else {
            model.addAttribute("username", username);
            return "redirect:/home";
        }
    }


}