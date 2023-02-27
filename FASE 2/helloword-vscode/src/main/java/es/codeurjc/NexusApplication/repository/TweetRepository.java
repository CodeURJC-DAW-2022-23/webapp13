package es.codeurjc.NexusApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import es.codeurjc.NexusApplication.model.Tweet;
import es.codeurjc.NexusApplication.model.User;

@Component
public interface TweetRepository extends JpaRepository<Tweet, Long>{

    Optional<Tweet> findById(Long id);
    List<Tweet> findByOwner(User user);
    List<Tweet> findByTag(String tag);
    
}
