package es.codeurjc.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import io.vavr.control.Try;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;
import es.codeurjc.backend.service.UserService;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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


    @ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

        visitorAuthenticated = (principal != null);
        model.addAttribute("authenticated", visitorAuthenticated);

		if (visitorAuthenticated) {
            loggedUser = Try
                .of(() -> userService.getUserByUsernameForced(principal.getName()))
                .getOrNull();
		}
	}

    //TODO
    
    
    @RequestMapping("/u/{username}")
    public String showProfile(Model model, @PathVariable String username){
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) return "error";

        following = Try
            .of(() -> loggedUser.getFollowing().contains(user.get()))
            .getOrElse(false);

        modelProfile(model, visitingOwnProfile(username));

        return "profileuser";
    }

    @RequestMapping("/switchfollowprofile")
    public String switchFollow(Model model){

        if (following) loggedUser.unfollow(profileUser);
        else loggedUser.follow(profileUser);

        this.following = !this.following;

        userRepository.save(loggedUser);
        userRepository.save(profileUser);

        model.addAttribute("alreadyFollowing", this.following);

        return "profileuser";
    }

    private boolean visitingOwnProfile(String user) {
        return visitorAuthenticated && user.equals(loggedUser.getUsername());
    }

    @GetMapping("/u/{username}/profilepicture")
	public ResponseEntity<Resource> downloadImage(@PathVariable String username) {
        ResponseEntity<Resource> notFoundRes = ResponseEntity.notFound().build();

		Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) return notFoundRes;

        Optional<Blob> pfp = Optional.ofNullable(user.get().getProfilePicture());
        if (pfp.isEmpty()) return notFoundRes;

        return Try.of(() -> ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
            .contentLength(pfp.get().length())
            .body((Resource) new InputStreamResource(pfp.get().getBinaryStream()))
        ).getOrElse(notFoundRes);
	}
	
	@RequestMapping("/update/pfp")
    public String uploadProfilePicture(@RequestParam MultipartFile img) {
        Try.run(() -> profileUser.setProfilePicture(
            BlobProxy.generateProxy(img.getInputStream(), img.getSize())
        ));
        return "profileuser";
    }

    @RequestMapping("/remove/pfp")
    public String removeProfilePicture() {
        profileUser.setProfilePicture(null);
        return "profileuser";
    }

    private void modelProfile (Model model, boolean ownProfile){
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("profileUsername", profileUser.getUsername());
        model.addAttribute("followerCount", profileUser.getFollowing().size());
        model.addAttribute("followingCount", profileUser.getFollowing().size());
        model.addAttribute("tweets", profileUser.getTweets());
        model.addAttribute("description", profileUser.getDescription());    
        model.addAttribute("ownProfile", ownProfile);
        model.addAttribute("alreadyFollowing", following);
    }
}
