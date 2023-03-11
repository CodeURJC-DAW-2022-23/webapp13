package es.codeurjc.backend.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date date;

    @ManyToOne
    private User recipient;

    @ManyToOne
    private User sender;

    @Column(columnDefinition = "TEXT")
    private String textContent;

    public Message(){}

    public Message(long id, Date date, User recipient, User sender, String textContent) {
        this.id = id;
        this.date = date;
        this.recipient = recipient;
        this.sender = sender;
        this.textContent = textContent;
    }

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public User getSender() {return sender;}
    public User getRecipient() {return recipient;}

    public String getTextContent() {return textContent;}
    public void setTextContent(String textContent) {this.textContent = textContent;}

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
}
