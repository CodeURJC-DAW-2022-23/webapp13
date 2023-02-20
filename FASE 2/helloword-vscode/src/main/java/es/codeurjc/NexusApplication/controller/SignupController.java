package es.codeurjc.NexusApplication.controller;

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

        return "signup/signuppage";
    }
   
    @PostMapping("/signup")
    public String processSignupForm(@RequestParam String email, @RequestParam String username, @RequestParam String password, Model model) {
        
        
        if (!userService.isEmail(email)){

            model.addAttribute("fail", "E-mail format not adequate.");
            return "signup/signuppage";
        }
        else if(userService.isEmailTaken(email)){

            model.addAttribute("fail", "E-mail address already in use.");
            return "signup/signuppage";
        }
        else if (userService.isUsernameTaken(username)){

            model.addAttribute("fail", "Username is taken.");
            return "signup/signuppage";
        }
        else if (password.length() <= 8){

            model.addAttribute("fail", "Password is too short (min 8 characters).");
            return "signup/signuppage";
        }

        User.Builder builder = new User.Builder();

        builder.setUsername(username);
        builder.setEmail(email);
        
        //builder.setPassword(password);

        User newUser = builder.build();

        userService.registerUser(newUser);

        return "userfeed/feedanon";
    }
}