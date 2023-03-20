package es.codeurjc.backend.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;
import es.codeurjc.backend.service.UserService;

@Controller
public class TweetInteractionController {
    
    @Autowired
    TweetService tweetService;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserService userService;
    
    @RequestMapping("/tweet/like/{id}")
    public String handleLike(
        HttpServletRequest req, @PathVariable String id
    ) {
        Optional<User> userOpt = userService.getUserFrom(req);
        Optional<Tweet> tweetOpt = tweetService.getTweetFromId(id);

        if (!userOpt.isEmpty()) {
            if (!tweetOpt.isEmpty()) {

                Tweet tweet = tweetOpt.get();
                tweet.switchLike(userOpt.get(), tweetService);
            }
            return UserService.getCurrentPage(req);
        }
        else return "redirect:/login";
    }

    @RequestMapping("/tweet/report/{id}")
    public String reportTweet(
        Model model, HttpServletRequest req, @PathVariable Long id
    ) {
        Optional<User> reportingUserOpt = userService.getUserFrom(req);
        Optional<Tweet> tweetOpt = tweetService.getTweetFromId(id);

        if (!reportingUserOpt.isEmpty()) {
            if (!tweetOpt.isEmpty()) {

                Tweet tweet = tweetOpt.get();

                tweet.getReporters().add(reportingUserOpt.get());

                tweetService.save(tweet);                       
            }
            return UserService.getCurrentPage(req);
        }
        else 
            return "redirect:/login";
    }
}
