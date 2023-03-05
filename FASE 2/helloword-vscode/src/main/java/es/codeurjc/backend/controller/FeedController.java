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

import java.util.List;
import java.util.Optional;

@Controller
public class FeedController {
    
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    private final FeedQuerier querier = new FeedQuerier();

    @RequestMapping("/feed")
    public String showFeed(Model model){
        if (userIsLogged(model)) handleModelForLoggedUser(model);
        handleAnonFeed(model);
        return "feed";
    }

    private void handleModelForLoggedUser(Model model) {
        Optional<User> user = getCurrentUser(model);
        if(user.isEmpty()) return;

        List<User> followings = userRepository.findByFollowers(user.get());
        model.addAttribute("tweets", querier.queryTweetsForUsers(followings));
    }

    private void handleAnonFeed(Model model) {
        List<Tweet> tweets = tweetRepository.findTop10ByOrderByDateDesc();
        model.addAttribute("tweets", tweets);
    }

    private Boolean userIsLogged(Model model) {
        return Try
            .of(() -> (Boolean) model.getAttribute("logged"))
            .getOrElse(false);
    }

    private Optional<User> getCurrentUser(Model model) {
        return Try
            .of(() -> (User) model.getAttribute("logged_user"))
            .toOption()
            .orElse(Option.none())
            .toJavaOptional();
    }

}
