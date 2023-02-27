package es.codeurjc.NexusApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {
    

    @GetMapping("/feed")
    public String showFeed(Model model){
        model.addAttribute("username", "");
        return "userfeed/feeduser";
    }

}
