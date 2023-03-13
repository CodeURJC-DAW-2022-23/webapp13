package es.codeurjc.backend.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;


import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;

import es.codeurjc.backend.repository.TweetRepository;
import es.codeurjc.backend.repository.UserRepository;

@RestController
public class DataBaseController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
	private PasswordEncoder passwordEncoder;
   
    @PostConstruct
    public void init() {

        User.Builder builder = new User.Builder();
        Tweet.Builder tweetBuilder = new Tweet.Builder();

        // Building users
        builder
            .setUsername("a")
            .setEmail("a@a.com")
            .setEncodedPassword(passwordEncoder.encode("a"))
            .setAdmin();
        

        User userA = builder.build();
        User userB = builder.setUsername("b").setEmail("b@b.com").build();
        User userC = builder.setUsername("c").setEmail("c@c.com").build();
        
        
        userRepository.save(userB);
        userRepository.save(userA);
        userRepository.save(userC);

        userA.switchFollow(userB);
        userRepository.save(userA);
        userRepository.save(userB);
        
        // Building tweets
        tweetBuilder
            .setAuthor(userA)
            .setText("This is my first tweet!")
            .addTag("amogus").addTag("asd").addTag("sus");
        
        Tweet tweet1 = tweetBuilder.build();

        tweet1.switchLike(userB);
        //-------------------------------------
        tweetBuilder.setAuthor(userB).setText("I replied to userA's tweet!");
        
        Tweet tweet2 = tweetBuilder.build();

        tweet1.addChild(tweet2);

        //DON'T CHANGE ORDER
        tweetRepository.save(tweet2);
        tweetRepository.save(tweet1);
        tweetRepository.save(
            tweetBuilder.setAuthor(userA).setText("Random post").build()
        );
        tweetRepository.save(
            tweetBuilder.setText("post severo, boilerplate").build()
        );

        //TODO hacer esto cada vez que se agregue un like en el code
    }

}
