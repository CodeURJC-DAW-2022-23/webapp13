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
    List<Tweet> findByOwnerUserOrderByDateAsc(User user);//if doesnt work, just put: findByOwnerOrderByDateAsc
    List<Tweet> findByTag(String tag);
    List<Tweet> findByOwner();
    List<Tweet> findAllOrderByDateAsc();
    List<Tweet> findFirst10ByOwner(User user);
    List<Tweet> findFirst10ByOwnerOrderByDateAsc(User user);
    //Page<Tweet> findByOwner(User user, Pageable page);
}
