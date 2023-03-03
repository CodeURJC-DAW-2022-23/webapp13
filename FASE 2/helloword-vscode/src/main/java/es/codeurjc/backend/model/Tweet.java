package es.codeurjc.backend.model;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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

    private Date date;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Nullable
    private String tag;

    @Nullable
    @OneToOne
    private Tweet repliesTo;

    @Nullable
    @Lob
    private Blob media;

    @Nullable
    @OneToMany
    private List<User> likes;

    public Tweet(){

    }

    //TODO
    /*public static class Builder {
        private String owner;
        private String tag;
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
        public Tweet build(){
            return new Tweet(this);
        }
    }*/

    public Tweet(User author, Date date, String text, String tag, Tweet repliesTo, Blob media) {
        this.author = author;
        this.date = date;
        this.text = text;
        this.tag = tag;
        this.repliesTo = repliesTo;
        this.media = media;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {return author;}

    public String getAuthorName() {return author.getUsername();};

    public Date getDate() {return date;}


    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    /*public List<User> getLikes() {
        return likes;
    }*/

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Tweet getRepliedTo() {
        return repliesTo;
    }

    public Blob getMedia() {
        return media;
    }

    public void setMedia(Blob media) {
        this.media = media;
    }
    
    public boolean hasMedia() {
        return media != null;
    }
}
