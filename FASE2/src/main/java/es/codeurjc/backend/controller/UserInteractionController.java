package es.codeurjc.backend.controller;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.service.TweetService;
import es.codeurjc.backend.utilities.OptTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;
import es.codeurjc.backend.service.UserService;

@Controller
public class UserInteractionController {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;
    
    @RequestMapping("/like/{id}")
    public String handleLike(
        HttpServletRequest req, @PathVariable String id
    ) {
        Optional<User> userOpt = userService.getUserFrom(req);
        Optional<Tweet> tweetOpt = tweetService.getTweetFromId(id);

        if (userOpt.isEmpty()) return "redirect:/login";
        if (tweetOpt.isEmpty()) return getCurrentPage(req);

        Tweet tweet = tweetOpt.get();
        tweet.switchLike(userOpt.get());
        tweetRepository.save(tweet);

        return getCurrentPage(req);
    }

    @RequestMapping("/follow/{username}")
    public String handleFollow(
        @PathVariable String username, HttpServletRequest req
    ) {
        OptTwo<User> users = userService.getUserFrom(username, req);

        if (!users.isLeft()) return getCurrentPage(req);
        if (!users.isRight()) return "redirect:/login";

        users.getRight().switchFollow(users.getLeft());

        users.forEach(user -> userService.saveUser(user));
        return getCurrentPage(req);
    }

    private String getCurrentPage(HttpServletRequest req) {
        return "redirect:" + req.getHeader("Referer");
    }

}
