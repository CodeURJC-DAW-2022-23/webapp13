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
    private User owner;

    private Date date;

    @Column(columnDefinition = "TEXT")
    private String text;

    private int likesNumber;

    @Nullable
    private String tag;

    @Nullable
    @OneToOne
    private Tweet replyTo;

    @Nullable
    @Lob
    private Blob media;

    @OneToMany(mappedBy = "tweet")
    private List<Like> likes;

    public Tweet(){

    }

    public Tweet(User owner, Date date, String text, int likesNumber, String tag, Tweet replyTo, Blob media) {
        this.owner = owner;
        this.date = date;
        this.text = text;
        this.likesNumber = likesNumber;
        this.tag = tag;
        this.replyTo = replyTo;
        this.media = media;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Tweet getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Tweet replyTo) {
        this.replyTo = replyTo;
    }

    public Blob getMedia() {
        return media;
    }

    public void setMedia(Blob media) {
        this.media = media;
    }
    
    
}
