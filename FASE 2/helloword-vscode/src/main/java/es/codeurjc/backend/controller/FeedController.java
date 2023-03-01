package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.TweetService;

@Controller
public class FeedController {
    
    @Autowired
    private TweetService tweetService;


    @RequestMapping("/feed")
    public String showFeed(Model model){
        
        return "feeduser";
    }

}
