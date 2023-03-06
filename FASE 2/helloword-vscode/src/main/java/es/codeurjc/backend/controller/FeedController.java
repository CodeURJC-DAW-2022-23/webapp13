package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.repository.TweetRepository;
import es.codeurjc.backend.repository.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.TweetService;
import es.codeurjc.backend.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class FeedController {
    
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserService userService;

    private User loggedUser;

    private boolean visitorAuthenticated;

    private final FeedQuerier querier = new FeedQuerier();

    @RequestMapping("/feed")
    public String showFeed(Model model, HttpServletRequest request){

        visitorAuthenticated = (request.getUserPrincipal() != null);
        
        if (visitorAuthenticated){
            
            String username = request.getUserPrincipal().getName();
            loggedUser = userService.getUserByUsernameForced(username);
            modelFeedUser(model);
        }
        else
            modelFeedAnon(model);
        model.addAttribute("authenticated", visitorAuthenticated);
        return "feed";
    }

    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        return "redirect:/u/" + loggedUser.getUsername();
    }

    private void modelFeedUser(Model model) {
        List<User> followings = loggedUser.getFollowing();
        model.addAttribute("username", loggedUser.getUsername());
        //model.addAttribute("tweets", querier.queryTweetsForUsers(followings));
    }

    private void modelFeedAnon(Model model) {
        List<Tweet> tweets = tweetRepository.findTop10ByOrderByDateDesc();
        model.addAttribute("tweets", tweets);
    }


}
