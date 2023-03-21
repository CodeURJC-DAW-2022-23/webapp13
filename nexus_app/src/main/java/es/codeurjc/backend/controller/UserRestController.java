package es.codeurjc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.service.TweetService;
import es.codeurjc.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    
}
