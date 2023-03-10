package es.codeurjc.backend.controller;


import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.model.ActionChronoWrapper;
import es.codeurjc.backend.repository.TweetRepository;
import es.codeurjc.backend.service.UserService;

@Controller
public class TweetController {

    @Autowired
    TweetRepository tweetRepository;
    
    @RequestMapping("/like/{id}")
    public String likeHandler(HttpServletRequest request, HttpSession session, @PathVariable String id){

        
        //TODO handle exceptions
        Long tweetId = Long.parseLong(id);

        Optional<Tweet> tweetOpt = tweetRepository.findById(tweetId);

        User likingUser = (User)session.getAttribute("user");

        if (tweetOpt.isPresent()) {
            Tweet tweet = tweetOpt.get();

            Set<ActionChronoWrapper> likes = tweet.getLikes();

            //don't 
            if (!likes.contains(tweetOpt)) {
                tweet.addLike(likingUser);
            }
            else {
                tweet.removeLike(likingUser);
            }
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
        
    }
}
