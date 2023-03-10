package es.codeurjc.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.model.ActionChronoWrapper;

@Component
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Set<ActionChronoWrapper> findByFollowing(User user);
}
