package es.codeurjc.NexusApplication.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Message {

    private Date date;

    private long idReceptor;

    private long idEmisor;

    @Column(columnDefinition = "TEXT")
    private String message;

    public Message(){

    }

    public Message(Date date, long idReceptor, long idEmisor, String message) {
        this.date = date;
        this.idReceptor = idReceptor;
        this.idEmisor = idEmisor;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(long idReceptor) {
        this.idReceptor = idReceptor;
    }

    public long getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(long idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

}
