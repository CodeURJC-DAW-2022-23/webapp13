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
    public String processLoginForm(@RequestParam String topInput, @RequestParam String password, Model model) {
                
        Optional<User> user;

        boolean emailInput;

        if (userService.isEmail(topInput)){
            user = userService.getUserByEmail(topInput);
            emailInput = true;
        }
        else{
            user = userService.getUserByUsername(topInput);
            emailInput = false;
        }

        //TODO quitar toda esta lógica y hacerla donde se debe (como dijo el profesor)
        if (user.isEmpty()) {
            if (emailInput){
                model.addAttribute("fail", "E-mail not registered.");
            }
            else {
                model.addAttribute("fail", "Username does not exist.");
            }
            return "login/loginpage";
        } 
        else if (!user.get().getEncodedPassword().equals(password)) {//TODO hash input password in order to compare it.
            model.addAttribute("fail", "Password is incorrect.");
            return "login/loginpage";
        } 
        else {
            model.addAttribute("username", topInput);
            return "login/loginpage";
        }
    }
}