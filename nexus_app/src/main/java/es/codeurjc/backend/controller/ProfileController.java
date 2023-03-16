package es.codeurjc.backend.controller;


import java.sql.Blob;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.utilities.OptTwo;
import io.vavr.control.Try;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.TweetRepository;
import es.codeurjc.backend.repository.UserRepository;
import es.codeurjc.backend.service.UserService;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class ProfileController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    private Optional<User> loggedUser;
    private User profileUser;

    private boolean following;

    @RequestMapping("/u/{username}")
    public String showProfile(
        Model model, @PathVariable String username, HttpServletRequest req
    ){

        OptTwo<User> users = userService.getUserFrom(username, req);

        if (users.isLeft()) profileUser = users.getLeft();
        loggedUser = users.getOptRight();

        model.addAttribute("authenticated", loggedUser.isPresent());

        if (profileUser == null) return "error";

        following = Try
            .of(() -> loggedUser.get().getFollowing().contains(profileUser))
            .getOrElse(false);

        modelProfile(model, visitingOwnProfile(username));

        return "profile";
    }

    @GetMapping("/u/{username}/profilepicture")
	public ResponseEntity<Resource> downloadImage(@PathVariable String username) {

		Optional<User> user = userService.getUserFrom(username);
        if (user.isEmpty()) return getAnonImage();

        Optional<Blob> profilePicture = Optional.ofNullable(user.get().getProfilePicture());
        if (profilePicture.isEmpty()) return getAnonImage();

        return ResourcesBuilder
            .tryBuildImgResponse(profilePicture)
            .getOrElse(getAnonImage());
	}

    private ResponseEntity<Resource> getAnonImage() {
        Resource img = new ClassPathResource("static/images/anonpfp.jpg");
        return ResourcesBuilder.buildImgResponseOrNotFound(img);
    }

    @GetMapping("/u/{username}/write")
    public String startWritingTweet(@PathVariable String username, Model model) {
        if (!visitingOwnProfile(username)) return "error";

         model.addAttribute("posting", true);
         modelProfile(model, visitingOwnProfile(username));

        return "profile";
    }

    @PostMapping("/u/{username}/posttweet")
    public String startWritingTweet(Model model, @PathVariable String username, @RequestParam String text) {
        if (!visitingOwnProfile(username)) return "error";

        Tweet.Builder builder = new Tweet.Builder();

        builder.setAuthor(loggedUser.get());
        builder.setText(text);
        tweetRepository.save(builder.build());
        userService.saveUser(profileUser);

        

        return "redirect:/u/" + username;
    }

    @PostMapping("/{username}/update/profilepicture")
    public String uploadProfilePicture(
        @RequestParam MultipartFile image, @PathVariable String username
    ) {
        if (!usernameIsLoggedUser(username)) return "redirect:/u/" + username;

        Try.run(() -> profileUser.setProfilePicture(
            BlobProxy.generateProxy(image.getInputStream(), image.getSize())
        ));

        userService.saveUser(profileUser);
        return "redirect:/u/" + loggedUser.get().getUsername();
    }

    @RequestMapping("{username}/remove/profilepicture")
    public String removeProfilePicture(@PathVariable String username) {
        if (!usernameIsLoggedUser(username)) return "redirect:/u/" + username;

        profileUser.setProfilePicture(null);
        return "profile";
    }

    private void modelProfile (Model model, boolean ownProfile){
        Collections.sort(profileUser.getTweets(), Collections.reverseOrder());

        model.addAttribute("profileUser", profileUser);
        model.addAttribute("followerCount", profileUser.getFollowers(userService).size());
        model.addAttribute("ownProfile", ownProfile);
        model.addAttribute("following", following);
        if (loggedUser.isEmpty()) return;
        model.addAttribute("loggedUser", loggedUser.get());
    }

    private boolean visitingOwnProfile(String username) {
        return usernameIsLoggedUser(username);
    }

    private boolean usernameIsLoggedUser(String username) {
        return loggedUser.isPresent() && username.equals(loggedUser.get().getUsername());
    }
}
