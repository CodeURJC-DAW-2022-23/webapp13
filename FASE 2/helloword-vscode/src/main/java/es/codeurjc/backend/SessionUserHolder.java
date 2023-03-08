package es.codeurjc.backend;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import es.codeurjc.backend.model.User;

@SessionScope
@Component
public class SessionUserHolder {
    
    private User user;

    public SessionUserHolder(){}
    
    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
