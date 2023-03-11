package es.codeurjc.backend.model;

import java.sql.Blob;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import org.springframework.lang.Nullable;

import es.codeurjc.backend.repository.UserRepository;
import es.codeurjc.backend.service.UserService;


import javax.persistence.GenerationType;

@Entity(name = "UserTable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name, username, email, encodedPassword, description = "";

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    private boolean banned = false;

    @Nullable
    @Lob
    private Blob profilePicture;
    
    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets = new ArrayList<Tweet>();

    @Nullable
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> following = new HashSet<User>();

    @Nullable
    @OneToMany
    private Set<User> blockedUsers = new HashSet<>();  

    public static class Builder {
        private String username, email, encodedPassword, name, description = "";
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
        public Builder setDescription(String description){
            assert(description != null);
            this.description = description;
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
        public void reset(){
            username = null; email = null; encodedPassword = null; 
            name = null; description = ""; admin = false; banned = false;
        }
        public User build(){
            return new User(this);
        }
    }
    
    private User(User.Builder builder){
        this(builder.username, builder.email, builder.encodedPassword, builder.name, 
        builder.description, builder.admin, builder.banned, null);
    }
    private User(String username, String email, String encodedPassword, String name, 
    String description, boolean admin, boolean banned, Blob profilePicture) {

        this.username = username;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.name = name;
        this.description = description;
        this.roles.add("USER");
        if (admin){
            this.roles.add("ADMIN");
        }
        this.profilePicture = profilePicture;
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

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public void ban() {this.banned = true;}
    public void unban() {this.banned = false;}
    public boolean isBanned() {return banned;}

    public Blob getProfilePicture() {return profilePicture;}
    public boolean hasProfilePicture() {return profilePicture != null;}
    public void setProfilePicture(Blob profilePicture) {this.profilePicture = profilePicture;}

    public List<Tweet> getTweets() {return tweets;}
    public void setTweets(List<Tweet> tweets) {this.tweets = tweets;}
  
    public Set<String> getRoles() {return this.roles;}

    public boolean isAdmin() {return roles.contains("ADMIN");}
    public void setAdmin() {assert(!isAdmin()); this.roles.add("ADMIN");}
    public void removeAdmin(){this.roles.remove("ADMIN");}

    public Set<User> getFollowing() {return following;}
    public void follow(User user) {
        this.following.add(user);
    }
    public void unfollow(User user) {
        this.following.remove(user);
    }

    /* TODO
    public Set<UserActionChronologicalWrapper> getFollowers(UserService userService) {        
        return .getFollowers(this);
    }
    */

    public Set<User> getBlockedUsers() {return blockedUsers;}
    public void block(User user){blockedUsers.add(user);};
    public void unblock(User user){blockedUsers.remove(user);};

    @Override
    public boolean equals(Object o) {
 
        if (o == this) {
            return true;
        }
        else if ((o == null)|| (o.getClass() != this.getClass())) {
            return false;
        }
         
        User other = (User) o;
         
        return this.id == other.id;
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int)id;
        hash = 31 * hash + (null == username ? 0 : username.hashCode());
        return hash;
    }


    //DON'T USE, ONLY FOR DATABASE 
    public User() {}
}
