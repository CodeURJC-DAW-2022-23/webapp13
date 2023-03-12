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
        HttpServletRequest request, HttpSession session, @PathVariable String id
    ) {
        Optional<User> userOpt = UserService.getUserFrom(session);
        Optional<Tweet> tweetOpt = tweetService.getTweetFromId(id);

        if (userOpt.isEmpty()) return "redirect:/login";
        if (tweetOpt.isEmpty()) return getCurrentPage(request);

        Tweet tweet = tweetOpt.get();
        tweet.switchLike(userOpt.get());
        tweetRepository.save(tweet);

        return getCurrentPage(request);
    }

    @RequestMapping("/follow/{username}")
    public String handleFollow(
        HttpServletRequest request, HttpSession session, @PathVariable String username
    ) {
        Optional<User> followingUserOpt = UserService.getUserFrom(session);
        Optional<User> followedUserOpt = userService.getUserByUsername(username);

        if (followingUserOpt.isEmpty()) return "redirect:/login";
        if (followedUserOpt.isEmpty()) return getCurrentPage(request);

        User followingUser = followingUserOpt.get();
        User followedUser = followedUserOpt.get();

        followingUser.switchFollow(followedUser);

        userService.saveUser(followedUser);
        userService.saveUser(followingUser);

        return getCurrentPage(request);
    }

    private String getCurrentPage(HttpServletRequest req) {
        return "redirect:" + req.getHeader("Referer");
    }

}
