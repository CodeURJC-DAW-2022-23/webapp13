package es.codeurjc.backend.controller;


import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.codeurjc.backend.service.TweetService;
import io.vavr.control.Option;
import io.vavr.control.Try;
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
public class InteractionController {

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

        if (userOpt.isEmpty()) return "redirect:/login";
        User likingUser = userOpt.get();

        Optional<Tweet> tweetOpt = tweetService.getTweetFromId(id);

        if (tweetOpt.isPresent()) {
            Tweet tweet = tweetOpt.get();

            Set<User> likes = Optional
                .ofNullable(tweet.getLikes())
                .orElseGet(Collections::emptySet);

            if (!likes.contains(likingUser))
                tweet.addLike(likingUser);
            else tweet.removeLike(likingUser);

            tweetRepository.save(tweet);
        }

        return getCurrentPage(request);
    }

    @RequestMapping("/follow/{username}")
    public String handleFollow(
        HttpServletRequest request, HttpSession session, @PathVariable String username
    ) {
        Optional<User> userOpt = UserService.getUserFrom(session);
        Optional<User> followedUserOpt = userService.getUserByUsername(username);

        if (userOpt.isEmpty()) return "redirect:/login";
        if (followedUserOpt.isEmpty()) return getCurrentPage(request);

        User followedUser = followedUserOpt.get();

        Set<User> follows = Optional
            .ofNullable(userOpt.get().getFollowing())
            .orElseGet(Collections::emptySet);

        if (!follows.contains(followedUser))
            follows.add(followedUser);
        else follows.remove(followedUser);

        userService.saveUser(followedUser);

        return getCurrentPage(request);
    }

    private String getCurrentPage(HttpServletRequest req) {
        return "redirect:" + req.getHeader("Referer");
    }

}
