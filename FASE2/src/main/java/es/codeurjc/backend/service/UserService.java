package es.codeurjc.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

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
        return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Set<User> getFollowers(User user) {
        return userRepository.findByFollowing(user);
    }
    public boolean isEmailTaken(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean isUsernameTaken(String username){
        return userRepository.existsByUsername(username);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    public static boolean isEmail(String input){
        return input.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
    /* TODO
    public Set<UserActionChronologicalWrapper> getFollowers(User user) {
        return userRepository.findByFollowing(user);
    }*/
    public static boolean isAuthenticated(HttpSession session){
        return session.getAttribute("user") != null;
    }

    public static Optional<User> getUserFrom(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute("user"));
    }
}