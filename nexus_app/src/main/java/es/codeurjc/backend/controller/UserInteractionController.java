package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;

import es.codeurjc.backend.utilities.OptTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

@Controller
public class UserInteractionController {

    @Autowired
    UserService userService;

    @RequestMapping("/follow/{username}")
    public String handleFollow(
        @PathVariable String username, HttpServletRequest req
    ) {
        OptTwo<User> users = userService.getUserFrom(username, req);
        
        // If visitor is authenticated
        if (users.isRight()){
            
            // If URL-user exists and visitor isn't attempting to follow himself
            if (users.isLeft() || !users.getRight().equals(users.getLeft())){
            
                users.getRight().switchFollow(users.getLeft());
                users.forEach(user -> userService.save(user));

                return UserService.getCurrentPage(req);
            } else 
                return "redirect:/error";
        }   
        else return "redirect:/login";
    }
}
