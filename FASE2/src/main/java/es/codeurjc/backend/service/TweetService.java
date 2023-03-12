package es.codeurjc.backend.service;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.LockInfo;
import java.util.Optional;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public Optional<Tweet> getTweetFromId(String id) {
        return getTweetFromId(Long.parseLong(id));
    }

    public Optional<Tweet> getTweetFromId(Long id) {
        return tweetRepository.findById(id);
    }

    public void addReportToTweet(User reporting, Long tweetId) {
        addReportToTweet(reporting, getTweetFromId(tweetId));
    }

    public void addReportToTweet(User reporting, Optional<Tweet> reported) {
        if (reported.isEmpty()) return;
        Tweet tweet = reported.get();
        if (tweet.getUsersThatReported().contains(reporting)) return;
        tweet.addReport();
        tweet.addUserThatReported(reporting);
        tweetRepository.save(tweet);
    }
}
