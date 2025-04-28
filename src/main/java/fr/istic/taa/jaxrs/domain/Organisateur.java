package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("organisateur")
@PrimaryKeyJoinColumn(name = "id")
public class Organisateur extends User {

    @OneToMany(mappedBy = "organisateur")
    @JsonManagedReference(value = "organisateur-concert")
    private List<Concert> concerts = new ArrayList<>();

    private String compagnie;

    public Organisateur() {
        super();
    }

    public Organisateur(String nom, String prenom, String codePostal, String email, String tel, String password, int age, Sexe sexe,String compagnie) {
        super(nom, prenom, codePostal, email, tel, password, age, sexe);
        this.compagnie = compagnie;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }

    public void addConcert(Concert concert) {
        if (concert != null && !concerts.contains(concert)) {
            concerts.add(concert);
            concert.setOrganisateur(this);
        }
    }

    public void removeConcert(Concert concert) {
        if (concert != null && concerts.remove(concert)) {
            concert.setOrganisateur(null);
        }
    }
    public String getCompagnie() {
        return compagnie;
    }
    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    @Override
    public String toString() {
        return "Organisateur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", nbConcerts=" + concerts.size() +
                ", compagnie='" + compagnie + '\'' +
                '}';
    }
}
