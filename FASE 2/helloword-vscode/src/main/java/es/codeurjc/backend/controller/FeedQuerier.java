package es.codeurjc.backend.controller;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FeedQuerier {
    @Autowired
    private TweetRepository tweetRepository;

    // NOTE this strategy is inefficient. If len(users) == 10
    // and each users has at least 10 posts, then len(@return) == 100.
    public List<Tweet> queryTweetsForUsers(List<User> users) {
        return users
            .stream()
            .map(user -> tweetRepository.findFirst10ByAuthor(user))
            .flatMap(Collection::stream)
            .sorted(Comparator.comparing(Tweet::getDate))
            .collect(Collectors.toList());
    }

}
