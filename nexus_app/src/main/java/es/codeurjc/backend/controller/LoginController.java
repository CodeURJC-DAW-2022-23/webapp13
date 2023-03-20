package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

import java.util.Optional;


@Controller
public class LoginController {

    @Autowired 
    private UserService userService;
   
    @RequestMapping("/login")
    public String login(Model model){

        model.addAttribute("inLogin", true);

        return "loginpage";
    }

    @RequestMapping("/loginfail")
    public String loginerror(Model model){
        model.addAttribute("fail", "Failed login");
        return "loginpage";
    }

    @RequestMapping("/loginsuccess")
    public String login(HttpServletRequest req, HttpSession session){
        Optional<User> loggedUser = userService.getUserBy(req);
        if (loggedUser.isEmpty()) return "redirect:/loginfail";
        return "redirect:/feed";
    }
}