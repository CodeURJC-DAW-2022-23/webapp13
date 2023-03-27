package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.repository.TweetRepository;

import es.codeurjc.backend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

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

        loggedUser = userService.getUserBy(request);

        if (loggedUser.isPresent())
            updateFeedModelForUsers(model);
        else updateFeedModelForAnons(model);

        model.addAttribute("authenticated", loggedUser.isPresent());
        model.addAttribute("inLogin", false);
        return "feed";
    }

    @RequestMapping("/feed/{tag}")
    public String showFeedTag(Model model, HttpServletRequest request, @PathVariable String Tag){

        loggedUser = userService.getUserBy(request);

        if (loggedUser.isPresent())
                updateFeedModelForUsersByTags(model, Tag);
        else updateFeedModelForAnons(model);

        model.addAttribute("authenticated", loggedUser.isPresent());
        model.addAttribute("inLogin", false);
        return "feed";
    }

    @RequestMapping("/feed/moderator")
    public String showModFeed(Model model, HttpServletRequest req) {
        Optional<User> user = userService.getUserBy(req);
        if (user.isEmpty() || !user.get().isAdmin()) return "error";
        updateFeedModelForMods(model, user.get());
        return "feed";
    }

    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        if (loggedUser.isPresent())
            return "redirect:/u/" + loggedUser.get().getUsername();
        else
            return "redirect:/error";
    }

    private void updateFeedModelForUsers(Model model) {
        ArrayList<User> followings = new ArrayList<>();
        followings.addAll(loggedUser.get().getFollowing());
        
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("tweets", tweetService.queryTweetsForUsers(followings));
    }

    private void updateFeedModelForUsersByTags(Model model, String Tag) {
        ArrayList<User> followings = new ArrayList<>();
        followings.addAll(loggedUser.get().getFollowing());
        
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("tweets", tweetRepository.findTweetsByTags(Tag));
    }

    private void updateFeedModelForAnons(Model model) {
        List<Tweet> tweets = tweetRepository.findTop10ByOrderByDateDesc();
        model.addAttribute("tweets", tweets);
    }

    private void updateFeedModelForMods(Model model, User admin) {
        model.addAttribute("authenticated", true);
        model.addAttribute("loggedUser", admin);
        model.addAttribute("tweets", tweetService.queryTweetsToModerate());
    }

    /*
    // FIXME if a tweet has N likes it will query all the N likes, in this sense
    // the model for Tweet should store the likes count. Same applies to replies.
    @GetMapping("/api/tweets")
    public ResponseEntity<Page<Tweet>> getTweets(
        @RequestParam("page") int page, @RequestParam("size") int size
    ) {
        if (size > 10) return ResponseEntity.badRequest().build();

        Page<Tweet> tweetsPage = tweetRepository
            .findAllByOrderByDateDesc(PageRequest.of(page, size));

        return ResponseEntity
            .ok()
            .header("X-Total-Count", String.valueOf(tweetsPage.getTotalElements()))
            .body(tweetsPage);
    }*/
}
