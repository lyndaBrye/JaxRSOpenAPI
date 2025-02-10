package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate date;
    private String lieu;
    private int capacity;
    @OneToOne
    @JoinColumn(name = "artiste_id")
    private Artiste artiste;

    @OneToMany(mappedBy = "concert",cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "organisateur_id")  // Clé étrangère en DB
    private Organisateur organisateur;
    public Concert() {
    }


    public Concert(LocalDate date, String lieu, int capacity) {
        this.date = date;
        this.lieu = lieu;
        this.capacity = capacity;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    @Override
    public String toString() {
        return "Concert{" +
                "id=" + id +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

}
