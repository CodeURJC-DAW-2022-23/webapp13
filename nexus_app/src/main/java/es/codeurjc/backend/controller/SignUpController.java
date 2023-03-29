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
    public String showSignUpForm(Model model) {
        return "signuppage";
    }
   
    @PostMapping("/signup")
    public String processSignUpForm(@RequestParam String email, @RequestParam String username, @RequestParam String password, Model model) {
        
        if (!UserService.isEmail(email)) 

            model.addAttribute("fail", "E-mail format not adequate.");
        
        else if(userService.isEmailTaken(email)) 

            model.addAttribute("fail", "E-mail address already in use.");
        
        else if (userService.isUsernameTaken(username)) 

            model.addAttribute("fail", "Username is taken.");
        
        else if (password.length() <= -1) 

            model.addAttribute("fail", "Password is too short (min 8 characters).");
        
        else {
            User.Builder builder = new User.Builder();

            builder.setUsername(username).setEmail(email)
            .setEncodedPassword(passwordEncoder.encode(password));

            User newUser = builder.build();

            userService.save(newUser);

            return "redirect:/login";
        }

        return showSignUpForm(model);
    }
}