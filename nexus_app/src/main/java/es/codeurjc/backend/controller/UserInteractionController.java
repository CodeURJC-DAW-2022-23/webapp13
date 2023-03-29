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

    @RequestMapping("/u/{username}/follow")
    public String handleFollow(
        @PathVariable String username, HttpServletRequest req
    ) {
        OptTwo<User> users = userService.getUserBy(username, req);
        
        if (UserService.visitorAuthenticated(users)){
            
            if (UserService.urlUserExistsAndNotSelfAction(users)){
            
                users.getRight().switchFollow(users.getLeft(), userService);

                return UserService.redirectToReferer(req);
            } else 
                return "error";
        }   
        else return "redirect:/login";
    }

    @RequestMapping("/u/{username}/ban")
    public String banUser(
        @PathVariable String username, HttpServletRequest req
    ) {
        OptTwo<User> users = userService.getUserBy(username, req);
        
        if (UserService.visitorAuthenticated(users) &&
            UserService.urlUserExistsAndNotSelfAction(users)) {
            
            User bannedUser = users.getLeft();
            
            if (!bannedUser.isBanned())
                bannedUser.ban();
            else
                bannedUser.unban();

            userService.save(bannedUser);

            return UserService.redirectToReferer(req);
    
        }   
        return "error";
    }

    @RequestMapping("/u/{username}/delete")
    public String deleteUser(
        @PathVariable String username, HttpServletRequest req
    ) {
        OptTwo<User> users = userService.getUserBy(username, req);
        
        if (UserService.visitorAuthenticated(users) &&
            UserService.urlUserExists(users) &&
            (users.getRight().isAdmin() || UserService.isSelfAction(users))) {

            //TODO add prompt asking whether you want to delete your account
            userService.delete(users.getLeft());

            return UserService.redirectToReferer(req);
        }   
        return "error";
    }
}
