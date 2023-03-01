package es.codeurjc.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;

@Service
public final class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsernameForced(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username +" not found"));
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean isEmailTaken(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean isUsernameTaken(String username){
        return userRepository.existsByUsername(username);
    }
    public void registerUser(User user){
        userRepository.save(user);
    }
    public boolean isEmail(String input){
        return input.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
}