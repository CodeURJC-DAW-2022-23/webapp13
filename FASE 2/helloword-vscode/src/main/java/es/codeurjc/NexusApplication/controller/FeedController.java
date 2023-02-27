package es.codeurjc.NexusApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.NexusApplication.service.TweetService;

@Controller
public class FeedController {
    
    @Autowired
    private TweetService tweetService;

    @GetMapping("/feed")
    public String showFeed(Model model){
        
        return "userfeed/feeduser";
    }

}
