package es.codeurjc.backend.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;
import es.codeurjc.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

//@SessionScope
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

		if (visitorAuthenticated) {
            loggedUser = userService.getUserByUsernameForced(principal.getName());
			model.addAttribute("logged", visitorAuthenticated);
            model.addAttribute("logged_user", loggedUser);
		} else {
			model.addAttribute("logged", visitorAuthenticated);
		}
	}

    //TODO
    
    
    @RequestMapping("/u/{username}")
    public String showProfile(Model model, @PathVariable String username){
        Optional <User> optionalProfileUser = userService.getUserByUsername(username);

        profileUser = optionalProfileUser.get();

        following = loggedUser.getFollowing().contains(profileUser);

        modelProfile(model, visitingOwnProfile(username));

        return "profileuser";
    }

    @RequestMapping("/switchfollowprofile")
    public String switchFollow(Model model){

        if (following){
            loggedUser.unfollow(profileUser);
            profileUser.getFollowers().remove(loggedUser);
        }else{
            loggedUser.follow(profileUser);
            profileUser.getFollowers().add(profileUser);
        }

        this.following = !this.following;

        userRepository.save(loggedUser);
        userRepository.save(profileUser);

        model.addAttribute("alreadyFollowing", this.following);

        return "profileuser";
    }

    private boolean visitingOwnProfile(String user) {
        return visitorAuthenticated && user.equals(loggedUser.getUsername());
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
