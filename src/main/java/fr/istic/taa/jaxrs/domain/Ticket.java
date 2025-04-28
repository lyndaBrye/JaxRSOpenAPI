package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    @JsonBackReference(value = "concert-ticket")
    private Concert concert;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-ticket")
    private User user;

    public Ticket() {
    }

    public Ticket(Concert concert, User user) {
        this.concert = concert;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", concert=" + (concert != null ? concert.getId() : "null") +
                ", user=" + (user != null ? user.getId() : "null") +
                '}';
    }
}
