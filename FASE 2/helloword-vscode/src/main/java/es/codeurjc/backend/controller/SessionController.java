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

    @Autowired
    private User loggedUser;

    private Boolean isSomeoneLogged = false;

    @ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {
            //loggedUser = userService.getUserByUsernameForced(principal.getName());
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
    
    
    @RequestMapping("/u/{username}")
    public String showProfile(Model model, @PathVariable String username){
        Optional <User> profileUser = userService.getUserByUsername(username);
        modelProfile(model, profileUser.get(), visitingOwnProfile(username));

        return "profileuser";
    }

    private Boolean visitingOwnProfile(String user) {
        return isSomeoneLogged && user.equals(loggedUser.getUsername());
    }

    private void modelProfile (Model model, User profileUser, boolean ownProfile){
        model.addAttribute("profileUsername", profileUser.getUsername());
        model.addAttribute("followerCount", profileUser.getFollowing().size());
        model.addAttribute("followingCount", profileUser.getFollowing().size());
        model.addAttribute("tweets", profileUser.getTweets());
        model.addAttribute("description", profileUser.getDescription());    
    }
}
