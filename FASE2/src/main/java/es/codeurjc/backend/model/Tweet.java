package es.codeurjc.backend.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.lang.Nullable;

@Entity(name = "Tweet")
public class Tweet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User author;

    private LocalDateTime date;

    @Column(name="reports")
    private int reports = 0;

    @ManyToMany
    private Set<User> usersThatReportedThisTweet = new HashSet<>();

    @Column(columnDefinition = "TEXT")
    private String text;

    @Nullable
    @Lob
    private Blob media;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> tags = new HashSet<String>();;    

    @Nullable
    @ManyToMany
    private Set<User> likes  = new HashSet<>();

    @Nullable
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Tweet> children = new ArrayList<>();

    @Nullable
    @OneToMany
    private Set<Tweet> shares = new HashSet<>();

    public static class Builder {
        private User author;
        private String text;
        private Blob media;
        private HashSet<String> tags = new HashSet<String>();

        public Builder setAuthor(User author){
            this.author = author;
            return this;
        }
        public Builder setText(String text){
            this.text = text;
            return this;
        }
        public Builder setMedia(Blob media){
            this.media = media;
            return this;
        }
        public Builder setTags(HashSet<String> tags){
            this.tags = tags;
            return this;
        }
        public Builder clearTags(){
            this.tags.clear();
            return this;
        }
        public Builder addTag(String String){
            //assert(parent == null);
            this.tags.add(String);
            return this;
        }
        public void reset(){
            author = null; text = null; media = null; tags.clear();
        }
        public Tweet build(){
            assert(author != null && text != null);
            return new Tweet(this);
        }
    }

    private Tweet(Tweet.Builder builder) {
        this(builder.author, builder.text, builder.media, builder.tags);
    }
    private Tweet(User author, String text, Blob media, HashSet<String> tags) {
        this.author = author;
        this.date = LocalDateTime.now();
        this.text = text;
        this.media = media;
        this.tags = tags;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public User getAuthor() {return author;}
    public String getUserName() {return author.getUsername();}

    public LocalDateTime getDate() {return date;}

    public int getReports() {return reports;}
    public void addReport() {reports++;}
    public void subReport() {reports--;}

    public Set<User> getUsersThatReported() {
        return usersThatReportedThisTweet;
    }
    public void addUserThatReported(User u) {
        usersThatReportedThisTweet.add(u);
    }
    public void subUserThatReported(User u) {
        usersThatReportedThisTweet.remove(u);
    }

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    
    public Set<User> getLikes() {return likes;}
    public void switchLike(User user) {
        assert(user != null);
        if (!likes.contains(user))
            likes.add(user);
        else likes.remove(user);
    }

    public Set<Tweet> getShares() {return shares;}
    
    public Set<String> getTags() {return tags;}
    public void addTag(String String) {
        //assert(this.parent == null);
        this.tags.add(String);
    }

    public List<Tweet> getChildren() {return children;}
    public void addChild(Tweet tweet) {children.add(tweet);}

    public Blob getMedia() {return media;}
    public boolean hasMedia() {return media != null;}

    //DON'T USE, ONLY FOR DATABASE
    public Tweet() {}
	
}
