package es.codeurjc.backend.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@SessionScope
@Controller
public class SessionController {
    
    @Autowired
    private User user;

    @Autowired
    private UserService userService;

    //TODO
    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        
        return "redirect:/" + user.getUsername();
    }
}
