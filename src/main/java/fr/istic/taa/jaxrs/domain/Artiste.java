package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;

@Entity
public class Artiste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String biographie;
    @OneToOne(mappedBy = "artiste", cascade = CascadeType.ALL)
    private Concert concert;
    public Artiste() {
    }


    public Artiste(String nom, String prenom, String biographie) {
        this.nom = nom;
        this.prenom = prenom;
        this.biographie = biographie;

    }

    // 🔹 Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 🔹 Méthode toString() pour affichage/logging
    @Override
    public String toString() {
        return "Artiste{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", biographie='" + biographie + '\'' +
                '}';
    }
}
