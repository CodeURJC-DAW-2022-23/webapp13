package es.codeurjc.backend.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
	private RepositoryUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	// Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/signup").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/loginfail").permitAll();
        http.authorizeRequests().antMatchers("/feed").permitAll();
        http.authorizeRequests().antMatchers("/loginsuccess").hasAnyRole("USER");

        http.authorizeRequests().antMatchers("/like").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/tweet/*/report").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/u/*/profilepicture/*").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/u/*/write").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/u/*/posttweet").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        http.authorizeRequests().antMatchers("/moderate").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/tweet/*/delete").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/u/*/delete").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/u/*/ban").hasAnyRole("ADMIN");
        

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");

        http.formLogin().failureUrl("/loginfail");
        http.formLogin().defaultSuccessUrl("/loginsuccess");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/login");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
