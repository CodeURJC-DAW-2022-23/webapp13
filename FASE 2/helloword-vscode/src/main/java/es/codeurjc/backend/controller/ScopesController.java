package es.codeurjc.backend.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ScopesController {
    
    /*@Resource(name = "sessionScopedBean")
    HelloMessageGenerator sessionScopedBean;*/

    //TODO
    @RequestMapping("/")
    public String getSessionScopeMessage(final Model model) {
        
        return "scopesExample";
    }
}
