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
public class SignupController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/signup")
    public String showSignupForm() {

        return "signup/loginpage";
    }
   
    
    @PostMapping("/signup")
    public String processSignupForm(@RequestParam String email, @RequestParam String username, @RequestParam String password, Model model) {
        
        
        if (!email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
            model.addAttribute("fail", "E-mail format not adequate.");
            return "signup/loginpage";
        }else if(userService.isEmailTaken(email)){
            model.addAttribute("fail", "E-mail address already in use.");
            return "signup/loginpage";
        }
        else if (userService.isUsernameTaken(username)){
            model.addAttribute("fail", "Username is taken.");
            return "signup/loginpage";
        }
        else if (password.length() <= 6){
            model.addAttribute("fail", "Password is too short (min 6 characters).");
            return "signup/loginpage";
        }

        return null;
    }
}