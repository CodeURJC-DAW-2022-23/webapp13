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
//@RequestMapping("/users")
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
            .setUsername("AnRandomLoremUser")
            .setEmail("a@a.com")
            .setEncodedPassword(passwordEncoder.encode("a"));
        

        User userA = builder.build();
        User userB = builder.setUsername("userB").setEmail("b@b.com").build();

        userRepository.save(userA);
        userRepository.save(userB);

        // Building tweets
        tweetBuilder
            .setAuthor(userA)
            .setText("This is my first tweet!")
            .addTag("amogus").addTag("asd").addTag("sus");
        
        Tweet tweet1 = tweetBuilder.build();
        Tweet tweet2 = tweetBuilder.setText("This is my second twit from da builder :D").build();
        Tweet tweet3 = tweetBuilder.setText("Random shitpost").build();

        tweet1.addLike(userB);
        tweetBuilder.setAuthor(userB).setText("I replied to userA's tweet!");
        
        Tweet tweet1reply = tweetBuilder.build();

        tweet1.addChild(tweet1reply);

        List<User> aux = userA.getFollowers();
        aux.add(userA);

        // FIXME changing save order could break tweet/reply linkings
        tweetRepository.save(tweet1reply);
        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
        tweetRepository.save(tweet3);

        // TODO this logic should get replicated for every added like in execution time
    }

}
