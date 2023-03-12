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
        HttpServletRequest req, HttpSession session, @PathVariable String id
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
        HttpServletRequest req, @PathVariable String username
    ) {
        Optional<User> followingUserOpt = userService.getUserFromRequest(req);
        Optional<User> followedUserOpt = userService.getUserByUsername(username);

        if (followingUserOpt.isEmpty()) return "redirect:/login";
        if (followedUserOpt.isEmpty()) return getCurrentPage(req);

        User followingUser = followingUserOpt.get();
        User followedUser = followedUserOpt.get();

        followingUser.switchFollow(followedUser);

        userService.saveUser(followedUser);
        userService.saveUser(followingUser);

        return getCurrentPage(req);
    }

    private String getCurrentPage(HttpServletRequest req) {
        return "redirect:" + req.getHeader("Referer");
    }

}
