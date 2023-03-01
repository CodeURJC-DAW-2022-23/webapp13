package es.codeurjc.backend.controller;

import java.util.List;
//import java.util.Date;
import java.sql.Date;

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
    /*
     * 
     */
    @PostConstruct
    public void init() {
        Date todayDate = new Date(System.currentTimeMillis());
        
        User.Builder builder = new User.Builder();

        builder.setUsername("a").setEmail("a@a.com").setEncodedPassword(passwordEncoder.encode("a"));

        User testUser1 = builder.build();


        Tweet tweet = new Tweet(testUser1, todayDate, "Esto es una prueba", 0, null, null, null);
        Tweet tweet1 = new Tweet(testUser1, todayDate, "Esto es otra prueba", 0, null, null, null);
        Tweet tweet2 = new Tweet(testUser1, todayDate, "Esto es una ultima prueba", 0, null, tweet1, null);
        Tweet tweet3 = new Tweet(testUser1, todayDate, "Esto es una prueba de paco", 0, null, null, null);
        Tweet tweet4 = new Tweet(testUser1, todayDate, "Esto es una prueba de pepe", 0, null, null, null);
        List<User> aux = testUser1.getFollowers();
        aux.add(testUser1);
        testUser1.setFollowers(aux);
        userRepository.save(testUser1);

        tweetRepository.save(tweet);
        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
        tweetRepository.save(tweet3);
        tweetRepository.save(tweet4);
        //User auxUser = userRepository.findByUsername("alb014").orElse(null);
        //List<Tweet> aList = auxUser.getTweets();
        //System.out.println(aList.get(0).getText());
    }
    
}
