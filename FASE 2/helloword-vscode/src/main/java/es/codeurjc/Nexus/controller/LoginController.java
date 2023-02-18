package es.codeurjc.Nexus.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.codeurjc.Nexus.service.UserService;
import es.codeurjc.NexusApplication.model.User;

@Controller
public class LoginController {

    private final String HTML_DIRECTORY = "login/loginpage";

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    @ResponseBody
    public String showLoginForm() {

        return HTML_DIRECTORY;
    }
   
    @PostMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> user = userService.getUserByUsername(username);

        if (user.isEmpty()) {
            model.addAttribute("error", "Username does not exist");
            return HTML_DIRECTORY;
        } 
        else if (!user.get().getEncodedPassword().equals(password)) {
            model.addAttribute("error", "Password is incorrect");
            return HTML_DIRECTORY;
        } 
        else {
            model.addAttribute("username", username);
            return "redirect:/home";
        }
    }
}