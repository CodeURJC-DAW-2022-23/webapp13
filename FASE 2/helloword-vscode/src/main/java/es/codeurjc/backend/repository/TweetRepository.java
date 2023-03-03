package es.codeurjc.backend.repository;

import java.util.List;
import java.util.Optional;

import org.h2.mvstore.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;

@Component
public interface TweetRepository extends JpaRepository<Tweet, Long>{

    Optional<Tweet> findById(Long id);
    List<Tweet> findByAuthorOrderByDate(User author);//if doesnt work, just put: findByOwnerOrderByDateAsc
    List<Tweet> findByTag(String tag);
    List<Tweet> findByAuthor(User author);
    //List<Tweet> findAllOrderByDate();
    List<Tweet> findFirst10ByAuthor(User author);
    List<Tweet> findFirst10ByAuthorOrderByDate(User author);
    //Page<Tweet> findByAuthor(User user, Pageable page);*/
}
