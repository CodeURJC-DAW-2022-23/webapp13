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
import es.codeurjc.backend.service.UserService;

@Controller
public class TweetInteractionController {
    
    @Autowired
    TweetService tweetService;

    @Autowired
    UserService userService;
    
    @RequestMapping("/tweet/{id}/like")
    public String handleLike(
        HttpServletRequest req, @PathVariable String id
    ) {
        Optional<User> userOpt = userService.getUserBy(req);
        Optional<Tweet> tweetOpt = tweetService.getTweetById(id);

        if (!userOpt.isEmpty()) {
            if (!tweetOpt.isEmpty()) {

                Tweet tweet = tweetOpt.get();
                tweet.switchLike(userOpt.get(), tweetService);
            }
            return UserService.redirectToReferer(req);
        }
        else return "redirect:/login";
    }

    @RequestMapping("/tweet/{id}/report")
    public String reportTweet(
        Model model, HttpServletRequest req, @PathVariable Long id
    ) {
        Optional<User> reportingUserOpt = userService.getUserBy(req);
        Optional<Tweet> tweetOpt = tweetService.getTweetById(id);

        if (!reportingUserOpt.isEmpty()) {
            if (!tweetOpt.isEmpty()) {

                Tweet tweet = tweetOpt.get();

                tweet.getReporters().add(reportingUserOpt.get());

                tweetService.save(tweet);                       
            }
            return UserService.redirectToReferer(req);
        }
        else 
            return "redirect:/login";
    }

    @RequestMapping(value = "/tweet/{id}/delete")
    public String deleteTweet(@PathVariable Long id, HttpServletRequest req) {

        Optional<User> deletingUserOpt = userService.getUserBy(req);
        Optional<Tweet> tweetOpt = tweetService.getTweetById(id);

        if (tweetOpt.isPresent() && 
        (TweetService.isOwnTweet(tweetOpt.get(), req) ||
        UserService.isAdmin(deletingUserOpt) || 
        TweetService.readIfPostShouldGetdeleted(id))) 
        {
            tweetService.delete(tweetOpt.get());
          
            return "redirect:" + req.getHeader("referer");
        } else
            return "error";
    }
}
