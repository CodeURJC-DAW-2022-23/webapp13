package es.codeurjc.backend.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.lang.Nullable;

@Entity(name = "Tweet")
public class Tweet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User author;

    private LocalDateTime date;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Nullable
    @Lob
    private Blob media;

    @Nullable
    @ManyToMany
    private List<String> tags;

    @Nullable
    @OneToMany
    private List<User> likes;

    @Nullable
    @ManyToOne
    private Tweet parent;
    @Nullable
    @OneToMany
    private List<Tweet> children;

    @Nullable
    @ManyToOne
    private Tweet shared;
    @Nullable
    @OneToMany
    private List<Tweet> shares;

    public static class Builder {
        private User author;
        private String text;
        private Blob media;
        private ArrayList<String> tags = new ArrayList<String>();
        private Tweet parent, shared;

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
        public Builder setTags(ArrayList<String> tags){
            this.tags = tags;
            return this;
        }
        public Builder clearTags(){
            this.tags.clear();
            return this;
        }
        public Builder addTag(String tag){
            assert(parent == null);
            this.tags.add(tag);
            return this;
        }
        public Builder setParent(Tweet parent){
            this.parent = parent;
            this.shared = null;
            this.tags.clear();
            return this;
        }
        public Builder setShared(Tweet shared){
            this.shared = shared;
            this.parent = null;
            return this;
        }
        public void reset(){
            author = null; text = null; media = null; tags.clear(); parent = null; shared = null;
        }
        public Tweet build(){
            return new Tweet(this);
        }
    }

    private Tweet(Tweet.Builder builder) {
        this(builder.author, builder.text, builder.media, builder.tags, builder.parent, builder.shared);
    }
    private Tweet(User author, String text, Blob media, ArrayList<String> tags, Tweet parent, Tweet shared) {
        this.author = author;
        this.date = LocalDateTime.now();
        this.text = text;
        this.media = media;
        this.tags = tags;
        this.parent = parent;
        if (parent != null){ 
            assert(parent != this && shared == null);
            parent.children.add(this);
        }
        if (shared != null){ 
            assert(shared != this && parent == null);
            shared.shares.add(this);
        }
        this.children = new ArrayList<Tweet>();
        this.likes = new ArrayList<User>();
        this.shares = new ArrayList<Tweet>();
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public User getAuthor() {return author;}
    public String getAuthorName() {return author.getUsername();}

    public LocalDateTime getDate() {return date;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    
    public List<User> getLikes() {return likes;}
    public int getLikeCount() {return likes.size();}
    public void addLike(User like) {likes.add(like);}

    public int getShareCount() {return shares.size();}

    public List <String> getTags() {return tags;}
    public void addTag(String tag) {
        assert(this.parent == null);
        this.tags.add(tag);
    }

    public Tweet getParent() {return parent;}
    public List<Tweet> getChildren() {return children;}
    public int getChildrenCount() {return children.size();}

    public Blob getMedia() {return media;}
    public boolean hasMedia() {return media != null;}

    //DON'T USE, ONLY FOR DATABASE
    public Tweet(){}
}
