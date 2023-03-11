package es.codeurjc.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModerateController {
    
    @RequestMapping("/moderate")
    public String moderate(){
        return "moderate";
    }
}
