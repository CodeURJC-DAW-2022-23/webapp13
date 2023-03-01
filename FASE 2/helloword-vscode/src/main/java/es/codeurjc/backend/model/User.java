package es.codeurjc.backend.model;

import java.sql.Blob;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.GenerationType;

@Component
@Entity(name = "UserTable")
@SessionScope
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name, username, email, encodedPassword;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    private boolean banned = false;

    @Nullable
    @Lob
    private Blob profilePicture;
    
    @OneToMany(mappedBy = "owner")
    private List<Tweet> tweets;

    @Nullable
    @ManyToMany
    private List<User> follows;

    @Nullable
    @ManyToMany
    private List<User> followers;

    @Nullable
    @OneToMany(mappedBy = "user")
    private List<Block> blockedUsers;

    public static class Builder {
        private String username;
        private String email;
        private String encodedPassword;
        private String name;
        private boolean admin = false;
        private boolean banned = false;

        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
        public Builder setEncodedPassword(String encodedPassword){
            this.encodedPassword = encodedPassword;
            return this;
        }
        public Builder setAdmin(){
            this.admin = true;
            return this;
        }
        public Builder setBan(){
            this.banned = true;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }

    //DON'T USE
    public User() {}

    private User(User.Builder builder){
        this(builder.username, builder.email, builder.encodedPassword, builder.name, builder.admin, builder.banned, null);
    }

    private User(String username, String email, String encodedPassword, String name, boolean admin, boolean banned, Blob profilePicture) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.roles = new ArrayList<>();
        this.roles.add("USER");
        if (admin){
            this.roles.add("ADMIN");
        }
        this.profilePicture = profilePicture;
        this.blockedUsers = new ArrayList<Block>();
        this.followers = new ArrayList<User>();
        this.follows = new ArrayList<User>();
        this.tweets = new ArrayList<Tweet>();
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name; }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getEncodedPassword() {return encodedPassword;}
    public void setEncodedPassword(String encodedPassword) {this.encodedPassword = encodedPassword;}

    public void ban() {this.banned = true;}
    public void unban() {this.banned = false;}
    public boolean isBanned() {return banned;}

    public Blob getProfilePicture() {return profilePicture;}
    public void setProfilePicture(Blob profilePicture) {this.profilePicture = profilePicture;}

    public List<Tweet> getTweets() {return tweets;}
    public void setTweets(List<Tweet> tweets) {this.tweets = tweets;}
  
    public List<String> getRoles() {return this.roles;}
    public void setBanned(boolean banned) {this.banned = banned;}

    public boolean isAdmin() {return roles.contains("ADMIN");}
    public void setAdmin() {assert(!isAdmin()); this.roles.add("ADMIN");}
    public void removeAdmin(){this.roles.remove("ADMIN");}

    public List<User> getFollows() {return follows;}
    public void setFollows(List<User> follows) {this.follows = follows;}

    public List<User> getFollowers() {return followers;}
    public void setFollowers(List<User> followers) {this.followers = followers;}

    public List<Block> getBlockedUsers() {return blockedUsers;}
    public void setBlockedUsers(List<Block> blockedUsers) {this.blockedUsers = blockedUsers;}
}
