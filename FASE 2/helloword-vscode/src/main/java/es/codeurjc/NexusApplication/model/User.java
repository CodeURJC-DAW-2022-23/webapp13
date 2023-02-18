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

    private String userName;

    private String encodedPassword;

    private boolean isAdmin;

    private int followersNumber;

    private boolean isBanned;
    
    @Lob
    private Blob profilePicture;

    public User(){

    }

    public User(String name, String userName, String encodedPassword, boolean isAdmin, int followersNumber, boolean isBanned, Blob profilePicture){
        this.name = name;
        this.userName = userName;
        this.encodedPassword = encodedPassword;
        this.isAdmin = isAdmin;
        this.followersNumber = followersNumber;
        this.isBanned = isBanned;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    public void setFollowersNumber(int followersNumber) {
        this.followersNumber = followersNumber;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    
}
