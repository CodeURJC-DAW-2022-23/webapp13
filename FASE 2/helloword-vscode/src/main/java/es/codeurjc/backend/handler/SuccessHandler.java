package es.codeurjc.backend.handler;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private User user;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        String loggedUsername = authentication.getName();
    
        this.user = userService.getUserByUsernameForced(loggedUsername);

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/feed");
            return;
        }else if(true){
            //another case
        }
        else{
            throw new RuntimeException("No redirection specified for user type");
        }     
        return;
    }
    
    
}
