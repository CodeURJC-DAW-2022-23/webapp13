package es.codeurjc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signuppage";
    }
   
    @PostMapping("/signup")
    public String processSignUpForm(@RequestParam String email, @RequestParam String username, @RequestParam String password, Model model) {
        
        if (!userService.isEmail(email)){

            model.addAttribute("fail", "E-mail format not adequate.");
            return "signuppage";
        }
        else if(userService.isEmailTaken(email)){

            model.addAttribute("fail", "E-mail address already in use.");
            return "signuppage";
        }
        else if (userService.isUsernameTaken(username)){

            model.addAttribute("fail", "Username is taken.");
            return "signuppage";
        }
        else if (password.length() <= -1){

            model.addAttribute("fail", "Password is too short (min 8 characters).");
            return "signuppage";
        }

        User.Builder builder = new User.Builder();

        builder.setUsername(username);
        builder.setEmail(email);
        builder.setEncodedPassword(passwordEncoder.encode(password));

        User newUser = builder.build();

        userService.registerUser(newUser);

        return "redirect:/login";
    }
}