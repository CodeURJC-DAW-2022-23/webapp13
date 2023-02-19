package es.codeurjc.NexusApplication.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.NexusApplication.service.UserService;
import es.codeurjc.NexusApplication.model.User;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginForm() {

        return "login/loginpage";
    }
   
    @PostMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> user = userService.getUserByUsername(username);

        if (user.isEmpty()) {
            model.addAttribute("fail", "Username does not exist");
            return "login/loginpage";
        } 
        else if (!user.get().getEncodedPassword().equals(password)) {
            model.addAttribute("fail", "Password is incorrect");
            return "login/loginpage";
        } 
        else {
            model.addAttribute("username", username);
            return "login/loginpage";
        }
    }
}