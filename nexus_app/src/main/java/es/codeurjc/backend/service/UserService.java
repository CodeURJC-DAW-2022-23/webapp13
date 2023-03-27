package es.codeurjc.backend.service;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.utilities.OptTwo;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.conditional.ElseAction;
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

    public OptTwo<User> getUserBy(
        String username, HttpServletRequest request
    ) {
        return OptTwo.of(getUserBy(username), getUserBy(request));
    }

    public Optional<User> getUserBy(HttpServletRequest request) {
        return getUserBy(
            Try.of(() -> request.getUserPrincipal().getName()).getOrElse("")
        );
    }

    public Optional<User> getUserBy(String username) {
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
    public UserService save(User user){
        userRepository.save(user);
        return this;
    }
    public UserService delete(User user){
        userRepository.delete(user);
        return this;
    }
    public static boolean visitorAuthenticated(OptTwo<User> users){
        return users.isRight();
    }
    public static boolean urlUserExists(OptTwo<User> users){
        return users.isLeft();
    }
    public static boolean isSelfAction(OptTwo<User> users){
        return users.getRight().equals(users.getLeft());
    }
    public static boolean urlUserExistsAndNotSelfAction(OptTwo<User> users){
        return urlUserExists(users) || !isSelfAction(users);
    }
    public static boolean isAdmin(Optional<User> user){
        return user.isPresent() && user.get().isAdmin();
    }

    public static boolean isEmail(String input){
        return input.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
    public static boolean isOwnResource(String resourceUsername, Optional<User> loggedUser){

        return loggedUser.isPresent() && loggedUser.get().getUsername().equals(resourceUsername);
    }
    public static String redirectToReferer(HttpServletRequest req) {
        return "redirect:" + req.getHeader("Referer");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    /* TODO
    public Set<UserActionChronologicalWrapper> getFollowers(User user) {
        return userRepository.findByFollowing(user);
    }*/
}