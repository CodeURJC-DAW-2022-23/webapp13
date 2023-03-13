package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.repository.TweetRepository;

import es.codeurjc.backend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FeedController {
    
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    private Optional<User> loggedUser;

    @RequestMapping("/feed")
    public String showFeed(Model model, HttpServletRequest request){

        loggedUser = userService.getUserFrom(request);

        if (loggedUser.isPresent())
            updateFeedModelForUsers(model);
        else updateFeedModelForAnons(model);

        model.addAttribute("authenticated", loggedUser.isPresent());
        model.addAttribute("inLogin", false);
        return "feed";
    }

    @RequestMapping("/feed/moderator")
    public String showModFeed(Model model, HttpServletRequest req) {
        Optional<User> user = userService.getUserFrom(req);
        if (user.isEmpty() || !user.get().isAdmin()) return "error";
        updateFeedModelForMods(model, user.get());
        return "feed";
    }

    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        return "redirect:/u/" + loggedUser.get().getUsername();
    }

    
    private void updateFeedModelForUsers(Model model) {
        ArrayList<User> followings = new ArrayList<>();
        followings.addAll(loggedUser.get().getFollowing());
        
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("tweets", tweetService.queryTweetsForUsers(followings));
    }

    private void updateFeedModelForAnons(Model model) {
        List<Tweet> tweets = tweetRepository.findTop10ByOrderByDateDesc();
        model.addAttribute("tweets", tweets);
    }

    private void updateFeedModelForMods(Model model, User admin) {
        model.addAttribute("loggedUser", admin);
        model.addAttribute("tweets", tweetService.queryTweetsToModerate());
    }
}
