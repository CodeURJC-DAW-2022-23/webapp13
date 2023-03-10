package es.codeurjc.backend.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

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

    @Column(columnDefinition = "TEXT")
    private String text;

    @Nullable
    @Lob
    private Blob media;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> tags = new HashSet<String>();;    

    @Nullable
    @OneToMany
    private Set<ActionChronoWrapper> likes  = new HashSet<>();

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Tweet parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)*/
    
    @Nullable
    @OneToMany
    private List<Tweet> children = new ArrayList<>();

    /*
    @Nullable
    @ManyToOne
    private Tweet shared;
    */
    @Nullable
    @OneToMany
    private Set<Tweet> shares = new HashSet<>();

    public static class Builder {
        private User author;
        private String text;
        private Blob media;
        private HashSet<String> tags = new HashSet<String>();
        //private Tweet parent, shared;

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
        /*
        public Builder setParent(Tweet parent){
            this.parent = parent;
            this.shared = null;
            this.tags.clear();
            return this;
        }
        public Builder setShared(Tweet shared){
            this.shared = shared;
            
            return this;
        }*/
        public void reset(){
            author = null; text = null; media = null; tags.clear();// parent = null; shared = null;
        }
        public Tweet build(){
            assert(author != null && text != null);
            return new Tweet(this);
        }
    }

    private Tweet(Tweet.Builder builder) {
        this(builder.author, builder.text, builder.media, builder.tags/*, builder.parent, builder.shared*/);
    }
    private Tweet(User author, String text, Blob media, HashSet<String> tags/*, Tweet parent, Tweet shared*/) {
        this.author = author;
        this.date = LocalDateTime.now();
        this.text = text;
        this.media = media;
        this.tags = tags;

        /*
        this.parent = parent;
        if (parent != null){ 
            assert(parent != this && shared == null);
            parent.children.add(this);
        }
        if (shared != null){ 
            assert(shared != this && parent == null);
            shared.shares.add(this);
        }*/
    
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public User getAuthor() {return author;}
    public String getUserName() {return author.getUsername();}

    public LocalDateTime getDate() {return date;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    
    public Set<ActionChronoWrapper> getLikes() {return likes;}
    public void addLike(User user) {
        likes.add(new ActionChronoWrapper(user));
    }
    public void removeLike(User user) {
        likes.remove(new ActionChronoWrapper(user));
	}

    public Set<Tweet> getShares() {return shares;}
    
    public Set<String> getTags() {return tags;}
    public void addTag(String String) {
        //assert(this.parent == null);
        this.tags.add(String);
    }

    /*
    public Tweet getParent() {return parent;}
    public void setParent(Tweet parent) {this.parent = parent;}
    */

    public List<Tweet> getChildren() {return children;}
    public void addChild(Tweet tweet) {children.add(tweet);}

    /*
    public List<Tweet> getTweetThread(Long tweetId) {
        Tweet tweet = em.find(Tweet.class, tweetId);
        if (tweet == null) {
            throw new EntityNotFoundException("Comment with id " + tweetId + " not found");
        }
        String query = "SELECT t FROM Tweet t WHERE t.parent IS NULL START WITH t.id = :tweetId CONNECT BY PRIOR t.id = t.parent.id ORDER SIBLINGS BY t.createdDate DESC";
        TypedQuery<Tweet> q = em.createQuery(query, Tweet.class);
        q.setParameter("tweetId", tweetId);
        return q.getResultList();
    }
    public void addChildTweet(Long parentId, Tweet childTweet) {
        Tweet parentTweet = em.find(Tweet.class, parentId);
        if (parentTweet == null) {
            throw new EntityNotFoundException("Tweet with id " + parentId + " not found");
        }
        childTweet.setParent(parentTweet);
        parentTweet.getChildren().add(childTweet);
        em.persist(childTweet);
        em.merge(parentTweet);
    }*/


    public Blob getMedia() {return media;}
    public boolean hasMedia() {return media != null;}

    //DON'T USE, ONLY FOR DATABASE
    public Tweet() {}
	
}
