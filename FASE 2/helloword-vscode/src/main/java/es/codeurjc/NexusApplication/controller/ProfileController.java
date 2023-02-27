package es.codeurjc.NexusApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    
    @GetMapping("/u/{{username}}")
    public String showFeed(Model model){
        
        return "profile/profile";
    }

}
