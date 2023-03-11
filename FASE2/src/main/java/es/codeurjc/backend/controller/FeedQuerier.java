package es.codeurjc.backend.controller;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public class FeedQuerier {
    // NOTE this strategy is inefficient. If len(users) == 10
    // and each users has at least 10 posts, then len(@return) == 100.
    public static List<Tweet> queryTweetsForUsers(List<User> users, TweetRepository rep) {
        if (users == null) return List.of();
        return users
            .stream()
            .map(rep::findFirst10ByAuthor)
            .flatMap(Collection::stream)
            .sorted(Comparator.comparing(Tweet::getDate))
            .collect(Collectors.toList());
    }

}
