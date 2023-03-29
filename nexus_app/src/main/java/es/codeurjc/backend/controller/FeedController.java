package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.repository.TweetRepository;

import es.codeurjc.backend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class FeedController {
    
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @RequestMapping("/feed")
    public String showFeed(Model model, HttpServletRequest request){

        Optional<User> loggedUser = userService.getUserBy(request);

        List<Tweet> tweetsToDisplay;

        if (loggedUser.isPresent() && loggedUser.get().getFollowing().size() > 0){
            tweetsToDisplay = tweetRepository.findFollowedUsersTweets(loggedUser.get(), PageRequest.of(0, 10));
        } else
            tweetsToDisplay = tweetRepository.findTop10ByOrderByDateDesc();
        
        updateFeedModel(model, loggedUser, tweetsToDisplay);

        return "feed";
    }

    @GetMapping("/feed/search")
    public String searchBytags(Model model, HttpServletRequest req, @RequestParam String tags) {

        Optional<User> loggedUser = userService.getUserBy(req);

        Set<String> inputTags = Set.of(tags.split("\\s+"));

        List<Tweet> foundTweets = tweetRepository.findTweetsByTags(inputTags, PageRequest.of(0, 10));
            
        updateFeedModel(model, loggedUser, foundTweets);
    
        return "feed";
    }

    @RequestMapping("/feed/moderator")
    public String showModFeed(Model model, HttpServletRequest req) {
        Optional<User> loggedUser = userService.getUserBy(req);
        if (loggedUser.isPresent() && loggedUser.get().isAdmin()){
            updateFeedModel(model, loggedUser, tweetService.queryTweetsToModerate());
            return "feed";
        } else
            return "error";
    }

    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model, HttpServletRequest req) {

        Optional<User> loggedUser = userService.getUserBy(req);

        if (loggedUser.isPresent())
            return "redirect:/u/" + loggedUser.get().getUsername();
        else
            return "error";
    }

    private void updateFeedModel(Model model, Optional<User> loggedUser, List<Tweet> displayedTweets) {
        model.addAttribute("loggedUser", loggedUser.get());
        model.addAttribute("authenticated", loggedUser.isPresent());
        model.addAttribute("inLogin", false);
        model.addAttribute("tweets", displayedTweets); 
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
