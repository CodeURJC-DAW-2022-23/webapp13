package es.codeurjc.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;


@Controller
public class LoginController {

    @Autowired 
    private UserService userService;
   
    @RequestMapping("/login")
    public String login(Model model){

        model.addAttribute("inLogin", true);

        return "loginpage";
    }

    @RequestMapping("/loginerror")
    public String loginerror(Model model){
        model.addAttribute("fail", "fallaste");
        return "loginpageerror";
    }

    @RequestMapping("/loginsuccess")
    public String login(HttpServletRequest request, HttpSession session){

        String username = request.getUserPrincipal().getName();
        User loggedUser = userService.getUserByUsernameForced(username);

        session.setAttribute("user", loggedUser);

        return "redirect:/feed";
    }
}