package es.codeurjc.backend.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

//@SessionScope
@Controller
public class SessionController {
    
    @Autowired
    private UserService userService;

    private User loggedUser;
    private Boolean isSomeoneLogged = false;

    @ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {

            loggedUser = userService.getUserByUsernameForced(principal.getName());
            isSomeoneLogged = true;
			model.addAttribute("logged", true);
            model.addAttribute("logged_user", loggedUser);
            model.addAttribute("login_logout_text", "Log out");
		} else {
			model.addAttribute("logged", isSomeoneLogged);
            model.addAttribute("login_logout_text", "Log in");
		}
	}

    //TODO
    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        return "redirect:/u/" + loggedUser.getUsername();
    }
    
    @RequestMapping("/u/{username}")
    public String showProfile(Model model, @PathVariable String username){
        Optional <User> profileUser = userService.getUserByUsername(username);
        return loadProfile(model, profileUser.get(), isUserGoingToHisOwnProfile(username));
    }

    private Boolean isUserGoingToHisOwnProfile(String user) {
        return isSomeoneLogged && user.equals(loggedUser.getUsername());
    }

    private String loadProfile (Model model, User profileUser, boolean ownProfile){
        model.addAttribute("profileUsername", profileUser.getUsername());
        model.addAttribute("followerCount", profileUser.getFollowers().size());
        model.addAttribute("followingCount", profileUser.getFollowing().size());
        model.addAttribute("tweets", profileUser.getTweets());
        model.addAttribute("description", profileUser.getDescription());

        return "profileuser";
    }
}
