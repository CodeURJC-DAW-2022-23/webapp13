package es.codeurjc.NexusApplication.model;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
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

    private String passwordDigest;

    private boolean admin;

    private int followers;

    private boolean banned;
    
    @Lob
    private Blob profilePicture;

    public static class Builder {
        private String username;
        private String email;
        private String passwordDigest;
        private boolean admin = false;

        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
        public Builder setPasswordDigest(String digest){
            this.passwordDigest = digest;
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
        this.passwordDigest = builder.passwordDigest;
        this.admin = builder.admin;       
    }

    public User(String username, String email, String passwordDigest, boolean admin){
        this.username = username;
        this.email = email;
        this.passwordDigest = passwordDigest;
        this.admin = admin;
    }
    public User(String username, String email, String passwordDigest, boolean admin, Blob profilePicture){
        this.username = username;
        this.email = email;
        this.passwordDigest = passwordDigest;
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

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String encodedPassword) {
        this.passwordDigest = encodedPassword;
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
