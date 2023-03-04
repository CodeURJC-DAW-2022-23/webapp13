package es.codeurjc.backend.controller;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

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

    @ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {

            this.loggedUser = userService.getUserByUsernameForced(principal.getName());

			model.addAttribute("logged", true);
            model.addAttribute("logged_user", loggedUser);
		} else {
			model.addAttribute("logged", false);
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

        if (profileUser.isPresent()){

            boolean ownProfile = username.equals(loggedUser.getUsername());

            return loadProfile(model, profileUser.get(), ownProfile).toString();
        } 
        else return null;
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
