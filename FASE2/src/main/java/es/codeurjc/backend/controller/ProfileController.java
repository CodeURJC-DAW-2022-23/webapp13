package es.codeurjc.backend.controller;


import java.sql.Blob;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import io.vavr.control.Try;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.model.User;
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

    private User loggedUser, profileUser;

    private boolean visitorAuthenticated, following;

    @RequestMapping("/u/{username}")
    public String showProfile(Model model, @PathVariable String username, HttpServletRequest request){

        User[] users = userService.getUsersFromUsernameAndRequest(username, request);

        profileUser = users[0];
        loggedUser = users[1];

        visitorAuthenticated = loggedUser != null;

        model.addAttribute("authenticated", visitorAuthenticated);

        if (profileUser == null) return "error";

        following = Try
            .of(() -> loggedUser.getFollowing().contains(profileUser))
            .getOrElse(false);

        modelProfile(model, visitingOwnProfile(username));

        return "profile";
    }

    private boolean visitingOwnProfile(String user) {
        return visitorAuthenticated && user.equals(loggedUser.getUsername());
    }

    @GetMapping("/u/{username}/profilepicture")
	public ResponseEntity<Resource> downloadImage(@PathVariable String username) {

		Optional<User> user = userService.getUserByUsername(username);
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

	@PostMapping("/update/profilepicture")
    public String uploadProfilePicture(@RequestParam MultipartFile image) {
       
        Try.run(() -> profileUser.setProfilePicture(
            BlobProxy.generateProxy(image.getInputStream(), image.getSize())
        ));

        userService.saveUser(profileUser);
        return "redirect:/u/" + loggedUser.getUsername();
    }

    @RequestMapping("/remove/profilepicture")
    public String removeProfilePicture() {
        profileUser.setProfilePicture(null);
        return "profile";
    }

    private void modelProfile (Model model, boolean ownProfile){
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("followerCount", profileUser.getFollowers(userService).size());
        model.addAttribute("ownProfile", ownProfile);
        model.addAttribute("following", following);
    }
}
