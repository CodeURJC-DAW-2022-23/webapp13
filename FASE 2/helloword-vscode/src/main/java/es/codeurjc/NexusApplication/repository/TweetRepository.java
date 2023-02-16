package es.codeurjc.NexusApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.NexusApplication.model.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long>{

    Optional<Tweet> findById(Long id);
    List<Tweet> findByUserId(Long userId);
    List<Tweet> findByTag(String tag);
    
}
