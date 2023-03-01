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
        User alberto = new User("Alberto", "a", "a@a.com", passwordEncoder.encode("a"), true, null);
        User pepe = new User("Pepe", "pepito", "pepito@gmail.com", "pepito123", false, null);
        User paco = new User("Paco", "paquito", "paquitunin@gamil.com", "paco123", false, null);
        User blopp = new User("Blop", "BlopGG", "blopp@gmail.com", "blopp", false, null);
        Tweet tweet = new Tweet(alberto, todayDate, "Esto es una prueba", 0, null, null, null);
        Tweet tweet1 = new Tweet(alberto, todayDate, "Esto es otra prueba", 0, null, null, null);
        Tweet tweet2 = new Tweet(alberto, todayDate, "Esto es una ultima prueba", 0, null, tweet1, null);
        Tweet tweet3 = new Tweet(paco, todayDate, "Esto es una prueba de paco", 0, null, null, null);
        Tweet tweet4 = new Tweet(pepe, todayDate, "Esto es una prueba de pepe", 0, null, null, null);
        List<User> aux = alberto.getFollowers();
        aux.add(pepe);
        alberto.setFollowers(aux);
        userRepository.save(pepe);
        userRepository.save(alberto);
        userRepository.save(paco);
        userRepository.save(blopp);
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
