package es.codeurjc.backend.controller;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.codeurjc.backend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;
import es.codeurjc.backend.repository.UserRepository;
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
        Optional<User> userOpt = userService.getUserFromRequest(req);
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
        User[] users = userService.getUsersFromUsernameAndRequest(username, req);

        User followedUser = users[0];
        User followingUser = users[1];

        if (followedUser == null) return getCurrentPage(req);
        if (followingUser == null) return "redirect:/login";

        followingUser.switchFollow(followedUser);

        userService.saveUser(followedUser);
        userService.saveUser(followingUser);

        return getCurrentPage(req);
    }

    private String getCurrentPage(HttpServletRequest req) {
        return "redirect:" + req.getHeader("Referer");
    }

}
