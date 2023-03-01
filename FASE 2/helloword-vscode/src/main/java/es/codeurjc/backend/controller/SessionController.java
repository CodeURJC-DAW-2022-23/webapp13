package es.codeurjc.backend.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class SessionController {
    
    @Autowired
    private User user;

    //TODO
    @RequestMapping("/tomyprofile")
    public String redirectToProfile(final Model model) {
        
        return "redirect:/" + user.getUsername();
    }
}
