package es.codeurjc.backend.controller;

import java.security.Principal;
import java.sql.Blob;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.vavr.control.Try;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public void addAttributes(Model model, HttpSession session) {

        visitorAuthenticated = UserService.isAuthenticated(session);

        model.addAttribute("authenticated", visitorAuthenticated);

		loggedUser = (User)session.getAttribute("user");
	}
    
    @RequestMapping("/u/{username}")
    public String showProfile(Model model, @PathVariable String username){
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) return "error";
        else profileUser = user.get();

        following = Try
            .of(() -> loggedUser.getFollowing().contains(profileUser))
            .getOrElse(false);

        modelProfile(model, visitingOwnProfile(username));

        return "profile";
    }

    @RequestMapping("/switchfollowprofile")
    public String switchFollow(Model model){

        //don't remove brackets, body might be expanded
        if (following) {
            loggedUser.unfollow(profileUser);
        }
        else {
            loggedUser.follow(profileUser);
        }

        this.following = !this.following;

        userRepository.save(loggedUser);
        userRepository.save(profileUser);

        model.addAttribute("alreadyFollowing", this.following);

        return "profile";
    }

    private boolean visitingOwnProfile(String user) {
        return visitorAuthenticated && user.equals(loggedUser.getUsername());
    }

    @GetMapping("/u/{username}/profilepicture")
	public ResponseEntity<Resource> downloadImage(@PathVariable String username) {
        ResponseEntity<Resource> notFoundResource = ResponseEntity.notFound().build();

		Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) return notFoundResource;

        Optional<Blob> profilePicture = Optional.ofNullable(user.get().getProfilePicture());
        if (profilePicture.isEmpty()) return notFoundResource;

        return Try.of(() -> ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
            .contentLength(profilePicture.get().length())
            .body((Resource) new InputStreamResource(profilePicture.get().getBinaryStream()))
        ).getOrElse(notFoundResource);
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
        model.addAttribute("followerCount", "todo");

        model.addAttribute("ownProfile", ownProfile);
        model.addAttribute("alreadyFollowing", following);
    }
}
