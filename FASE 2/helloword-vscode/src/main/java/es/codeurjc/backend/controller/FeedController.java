package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

import java.util.List;

@Controller
public class FeedController {
    
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserService userService;

    private User loggedUser;

    @RequestMapping("/feed")
    public String showFeed(Model model, HttpServletRequest req){

        if (visitorAuthenticated(req)) {

            String username = req.getUserPrincipal().getName();
            loggedUser = userService.getUserByUsernameForced(username);
            updateFeedModelForUsers(model);

        } else updateFeedModelForAnons(model);

        model.addAttribute("authenticated", visitorAuthenticated((req)));
        return "feed";
    }

    public static boolean visitorAuthenticated(HttpServletRequest req) {
        return req.getUserPrincipal() != null;
    }

    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        return "redirect:/u/" + loggedUser.getUsername();
    }

    private void updateFeedModelForUsers(Model model) {
        List<User> followings = loggedUser.getFollowing();
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
