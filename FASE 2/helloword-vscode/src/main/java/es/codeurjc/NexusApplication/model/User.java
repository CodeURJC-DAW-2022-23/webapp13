package es.codeurjc.NexusApplication.model;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

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

    private String encodedPassword;

    private boolean admin;

    private int followersNumber;

    private boolean banned;
    
    @Lob
    private Blob profilePicture;

    public User(){
        
    }

    //TODO (Stefano): create a builder
    public User(String name, String username, String encodedPassword, boolean isAdmin, int followersNumber, boolean isBanned, Blob profilePicture){
        this.name = name;
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.admin = isAdmin;
        this.followersNumber = followersNumber;
        this.banned = isBanned;
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

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    public void setFollowersNumber(int followersNumber) {
        this.followersNumber = followersNumber;
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
