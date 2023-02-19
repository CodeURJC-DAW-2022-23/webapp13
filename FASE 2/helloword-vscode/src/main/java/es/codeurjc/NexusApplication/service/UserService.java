package es.codeurjc.NexusApplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.NexusApplication.model.User;
import es.codeurjc.NexusApplication.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean isEmailTaken(String email){
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean isUsernameTaken(String username){
        return userRepository.findByUsername(username).isPresent();
    }
    public void createUser(User user){
        userRepository.save(user);
    }
    public boolean isEmail(String input){
        return input.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
}