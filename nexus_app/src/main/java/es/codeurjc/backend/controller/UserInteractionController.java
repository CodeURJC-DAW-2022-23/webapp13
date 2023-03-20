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
            
                users.getRight().switchFollow(users.getLeft());
                users.forEach(user -> userService.save(user));

                return UserService.getCurrentPage(req);
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
        
            bannedUser.ban();

            userService.save(bannedUser);

            return UserService.getCurrentPage(req);
    
        }   
        return "error";
    }

    @RequestMapping("/u/{username}/delete")
    public String deleteUser(
        @PathVariable String username, HttpServletRequest req
    ) {
        OptTwo<User> users = userService.getUserBy(username, req);
        
        if (UserService.visitorAuthenticated(users) &&
            UserService.urlUserExistsAndNotSelfAction(users)) {

            userService.delete(users.getLeft());

            return UserService.getCurrentPage(req);
        }   
        return "error";
    }
}
