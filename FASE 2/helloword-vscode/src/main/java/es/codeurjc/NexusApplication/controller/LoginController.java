package es.codeurjc.NexusApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

   
    @RequestMapping("/login")
    public String login(){
        return "loginpage";
    }

    @RequestMapping("/loginerror")
    public String loginerror(){
        return "loginpageerror";
    }
}