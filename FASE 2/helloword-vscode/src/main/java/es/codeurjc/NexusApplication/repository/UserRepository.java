package es.codeurjc.NexusApplication.repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import es.codeurjc.NexusApplication.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
