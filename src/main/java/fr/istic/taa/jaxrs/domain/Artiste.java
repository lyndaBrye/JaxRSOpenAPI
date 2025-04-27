package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Artiste implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    private String imageUrl;
    private String biographie;

   // @OneToMany(mappedBy = "artiste", cascade = CascadeType.ALL, orphanRemoval = true)
//    private java.util.List<Concert> concerts = new java.util.ArrayList<>();
   /*? public java.util.List<fr.istic.taa.jaxrs.domain.Concert> getConcerts() {
     return concerts;
    }
    public void setConcerts(java.util.List<fr.istic.taa.jaxrs.domain.Concert> concerts) {
    this.concerts = concerts;
}
*/
    public Artiste() {
    }


    public Artiste(String nom, String prenom, String biographie, String imageUrl) {
        this.nom = nom;
        this.prenom = prenom;
        this.biographie = biographie;
        this.imageUrl = imageUrl;
    }
   public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // 🔹 Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    // 🔹 Méthode toString() pour affichage/logging
    @Override
    public String toString() {
        return "Artiste{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", biographie='" + biographie + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
