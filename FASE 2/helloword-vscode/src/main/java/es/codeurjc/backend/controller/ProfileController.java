package es.codeurjc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.backend.service.UserService;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/u/{username}")
    public String showFeed(Model model, @PathVariable String username){
        
        return "profileuser";
    }

}
