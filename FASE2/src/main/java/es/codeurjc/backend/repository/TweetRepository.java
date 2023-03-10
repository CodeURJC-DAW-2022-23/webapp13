package es.codeurjc.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;

@Component
public interface TweetRepository extends JpaRepository<Tweet, Long>{
    Optional<Tweet> findById(Long id);
    List<Tweet> findFirst10ByAuthor(User author);
    List<Tweet> findTop10ByOrderByDateDesc();
    List<Tweet> findTop10ByOrderByReportsDesc();
    Page<Tweet> findAllByOrderByDateDesc(Pageable pageable);
}
