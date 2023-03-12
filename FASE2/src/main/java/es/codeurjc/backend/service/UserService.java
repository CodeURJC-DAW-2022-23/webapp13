package es.codeurjc.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
    public Optional<User> getUserFromRequest(HttpServletRequest request) {
        return getUserByUsername(request.getUserPrincipal().getName());
    }
    
    public User getUserByUsernameOrNull(String username) {
        Optional<User> userOpt = getUserByUsername(username);
        if (userOpt.isPresent()) return userOpt.get();
        else return null;
    }
    public User getUserFromRequestOrNull(HttpServletRequest request) {

        if (request.getUserPrincipal() == null) return null;
        else
            return getUserByUsernameOrNull(request.getUserPrincipal().getName());
    }
    public User[] getUsersFromUsernameAndRequest(String username, HttpServletRequest request) {
        User[] pair = new User[2];
        pair[0] = getUserByUsernameOrNull(username);
        pair[1] = getUserFromRequestOrNull(request);
        return pair;
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
}