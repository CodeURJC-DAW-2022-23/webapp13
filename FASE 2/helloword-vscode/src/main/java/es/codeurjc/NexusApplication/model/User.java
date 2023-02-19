package es.codeurjc.NexusApplication.model;

import java.sql.Blob;

import javax.management.RuntimeErrorException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.GenerationType;

@Entity(name = "UserTable")
@SessionScope
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String username;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;

    private boolean admin;

    private int followers;

    private boolean banned;

    private boolean passwordEncoded = false;
    
    @Lob
    private Blob profilePicture;

    public static class Builder {
        private String username;
        private String email;
        private String password;
        private boolean admin = false;

        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
        public Builder setPassword(String password){
            this.password = password;
            return this;
        }
        public Builder setAdmin(){
            this.admin = true;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }

    private User(User.Builder builder){
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.admin = builder.admin;       
    }

    public User(String username, String email, String password, boolean admin){
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
    public User(String username, String email, String password, boolean admin, Blob profilePicture){
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.profilePicture = profilePicture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password, PasswordEncoder passwordEncoder) {
        this.password = password;
        passwordEncoded = false;
        encodePassword(passwordEncoder);
    }


    public String getPassword() {
        return password;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        if (!this.passwordEncoded){
            this.password = passwordEncoder.encode(this.password);
            this.passwordEncoded = true;
        }
        else throw new RuntimeException("Password already encoded");
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followersNumber) {
        this.followers = followersNumber;
    }

    public boolean isBanned() {
        return banned;
    }

    public void ban() {
        this.banned = true;
    }
    public void unban(){
        this.banned = false;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    
}
