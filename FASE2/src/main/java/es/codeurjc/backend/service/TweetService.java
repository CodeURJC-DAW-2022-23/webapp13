package es.codeurjc.backend.service;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.LockInfo;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void addReportToTweet(User reporting, Tweet tweet) {
        addReportToTweet(reporting, Optional.of(tweet));
    }

    public void addReportToTweet(User reporting, Optional<Tweet> reported) {
        if (reported.isEmpty()) return;
        Tweet tweet = reported.get();
        if (tweet.getUsersThatReported().contains(reporting)) return;
        tweet.addReport();
        tweet.addUserThatReported(reporting);
        tweetRepository.save(tweet);
    }

    // NOTE this strategy is inefficient. If len(users) == 10
    // and each users has at least 10 posts, then len(@return) == 100.
    public List<Tweet> queryTweetsForUsers(List<User> users) {
        if (users == null) return List.of();
        return users
            .stream()
            .map(tweetRepository::findFirst10ByAuthor)
            .flatMap(Collection::stream)
            .sorted(Comparator.comparing(Tweet::getDate))
            .collect(Collectors.toList());
    }

    public List<Tweet> queryTweetsToModerate() {
        return tweetRepository.findTop10ByOrderByReportsDesc();
    }

}
