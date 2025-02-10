package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Organisateur extends User implements Serializable {

    @OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL)
    private List<Concert> concerts;

    // 🔹 Constructeurs
    public Organisateur() {
    }

    public Organisateur(String nom, String prenom, String codePostal, String email, String tel, String password, int age, Sexe sexe) {
        super(nom, prenom, codePostal, email, tel, password, age, sexe);
    }

    // 🔹 Getters et Setters
    public List<Concert> getConcerts() { return concerts; }
    public void setConcerts(List<Concert> concerts) { this.concerts = concerts; }

    @Override
    public String toString() {
        return "Organisateur{" +
                "nom='" + getNom() + '\'' +   // 🔹 Correction ici
                ", prenom='" + getPrenom() + '\'' +
                ", concerts=" + (concerts != null ? concerts.size() : "null") +
                '}';
    }
}
