package es.codeurjc.backend.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "acw")
public class ActionChronoWrapper implements Comparable<ActionChronoWrapper>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    private LocalDateTime dateTime;

    public ActionChronoWrapper(User user){
        this.dateTime = LocalDateTime.now();
    }

    public User getUser() {return user;}
    public LocalDateTime getDateTime() {return dateTime;}

    @Override
    public int compareTo(ActionChronoWrapper o) {
        
        if (this == o || this.user.equals(o.user))
            return 0;
        else if (o.dateTime.isAfter(this.dateTime))
            return -1;
        else 
           return 1;
    }

    /* TODO https://www.technofundo.com/tech/java/equalhash.html
    @Override
    public boolean equals(Object o) {
        
        if (o == this){
            return true;
        }if (o instanceof UserActionChronologicalWrapper){
            UserActionChronologicalWrapper other = (UserActionChronologicalWrapper)o;
            return this.user.equals(other.user);
        }else 
            return false;
    }


    public int hashCode()
		{
			int hash = 7;
			hash = 31 * hash + id;
			hash = 31 * hash + (null == user ? 0 : user.hashCode());
			return hash;
		}
    */

    //DON'T USE
    ActionChronoWrapper() {}
}
