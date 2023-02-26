package es.codeurjc.NexusApplication.model;

import java.sql.Blob;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.lang.Nullable;
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

    private String encodedPassword;

    //@ElementCollection
    private ArrayList<String> roles = new ArrayList<>();

    private int followersNumber;

    private boolean banned;

    private boolean passwordEncoded = false;
    
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
    private List<Block> blockedUserList;



    public static class Builder {
        private String username;
        private String email;
        private String password;
        private ArrayList<String> roles = new ArrayList<String>();

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
            this.roles.add("ADMIN");
            return this;
        }
        public User build(){
            this.roles.add("USER");
            return new User(this);
        }
    }

    private User(User.Builder builder){
        this.username = builder.username;
        this.email = builder.email;
        this.encodedPassword = builder.password;
        this.roles = builder.roles;       
    }

    public User(String username, String email, String password, boolean admin) {
        this.username = username;
        this.email = email;
        this.encodedPassword = password;
        if (admin){
            this.roles.add("ADMIN");
        }
    }

    /*
     * Para declarar usuarios al iniciar la aplicación
     */
    public User(String name, String username, String email, String password, boolean admin, Blob profilePicture) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.encodedPassword = password;
        if (admin){
            this.roles.add("ADMIN");
        }
        this.profilePicture = profilePicture;
        this.banned = false;
        this.blockedUserList = new ArrayList<Block>();
        this.followersNumber = 0;
        this.followers = new ArrayList<User>();
        this.follows = new ArrayList<User>();
        this.passwordEncoded = false;
        this.tweets = new ArrayList<Tweet>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password, PasswordEncoder passwordEncoder) {
        this.encodedPassword = password;
        passwordEncoded = false;
        encodePassword(passwordEncoder);
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        if (!this.passwordEncoded){
            this.encodedPassword = passwordEncoder.encode(this.encodedPassword);
            this.passwordEncoded = true;
        }
        else throw new RuntimeException("Password already encoded");
    }

    public boolean isAdmin() {
        if(roles.contains("ADMIN")){
            return true;
        }
        return false;
    }

    public void setAdmin() {
        this.roles.add("ADMIN");
        return;
    }

    public void removeAdmin(){
        this.roles.remove("ADMIN");
        return;
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

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
  
    public ArrayList<String> getRoles(){
        return this.roles;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public List<User> getFollows() {
        return follows;
    }

    public void setFollows(List<User> follows) {
        this.follows = follows;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Block> getBlockedUserList() {
        return blockedUserList;
    }

    public void setBlockedUserList(List<Block> blockedUserList) {
        this.blockedUserList = blockedUserList;
    }

}
