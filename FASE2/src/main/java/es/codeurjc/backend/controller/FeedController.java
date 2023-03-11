package es.codeurjc.backend.controller;

import javax.servlet.http.HttpSession;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.repository.TweetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    
    @Autowired
    private TweetRepository tweetRepository;

    private User loggedUser;

    @RequestMapping("/feed")
    public String showFeed(Model model, HttpSession session){

        loggedUser = (User)session.getAttribute("user");

        boolean visitorAuthenticated = UserService.isAuthenticated(session);

        if (visitorAuthenticated)
            updateFeedModelForUsers(model);
        else
            updateFeedModelForAnons(model);

        model.addAttribute("authenticated", visitorAuthenticated);
        model.addAttribute("inLogin", false);
        return "feed";
    }

    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        return "redirect:/u/" + loggedUser.getUsername();
    }

    
    private void updateFeedModelForUsers(Model model) {
        
        ArrayList<User> followings = new ArrayList<>();  
        followings.addAll(loggedUser.getFollowing());
        
        model.addAttribute("username", loggedUser.getUsername());
        model.addAttribute(
            "tweets",
            FeedQuerier.queryTweetsForUsers(followings, tweetRepository)
        );
    }

    private void updateFeedModelForAnons(Model model) {
        List<Tweet> tweets = tweetRepository.findTop10ByOrderByDateDesc();
        model.addAttribute("tweets", tweets);
    }
}
