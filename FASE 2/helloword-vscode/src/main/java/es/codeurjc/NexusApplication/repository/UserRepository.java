package es.codeurjc.NexusApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.NexusApplication.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByName(String name);
    Optional<User> findByUserName(String name);
    
}
