package es.codeurjc.NexusApplication.configuration;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.codeurjc.NexusApplication.service.UserService;

/*public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    //TODO hay algo mal porque la clase que quería extender el profesor en las diapositivas 
    //está deprecada así que hay que preguntarle qué hacer al respecto
    protected void configure(AuthenticationManagerBuilder auth){
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}*/
