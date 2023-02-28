package es.codeurjc.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

   
    @RequestMapping("/login")
    public String login(){
        return "loginpage";
    }

    @RequestMapping("/loginerror")
    public String loginerror(Model model){
        model.addAttribute("fail", "fallaste");
        return "loginpageerror";
    }
}