package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Concert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    private String lieu;
    private int capacity;
    private double prix;  // ✅ Ajout du prix

    @OneToOne
    @JoinColumn(name = "artiste_id")
    private Artiste artiste;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Concert> concerts = new ArrayList<>();

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void addConcert(Concert concert) {
        concerts.add(concert);
        concert.setOrganisateur(this.organisateur);
    }

    @ManyToOne
    @JoinColumn(name = "organisateur_id")
    private Organisateur organisateur;

    public Concert() {
    }

    // ✅ Modification du constructeur pour inclure le prix
    public Concert(LocalDateTime date, String lieu, int capacity, double prix) {
        this.date = date;
        this.lieu = lieu;
        this.capacity = capacity;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrix() {  // ✅ Getter pour prix
        return prix;
    }

    public void setPrix(double prix) {  // ✅ Setter pour prix
        this.prix = prix;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "id=" + id +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", capacity=" + capacity +
                ", prix=" + prix +  // ✅ Ajout du prix dans le toString()
                ", artiste=" + artiste +
                ", tickets=" + tickets +
                ", organisateur=" + organisateur +
                '}';
    }
}
