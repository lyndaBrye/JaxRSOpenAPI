package fr.istic.taa.jaxrs.DTO;

import fr.istic.taa.jaxrs.domain.Artiste;
import java.io.Serializable;

public class ArtisteDTO{
    private Long id;
    private String nom;
    private String prenom;
    private String biographie;

    // ✅ Constructeur par défaut
    public ArtisteDTO() {
    }

    // ✅ Constructeur avec paramètres
    public ArtisteDTO(Long id, String nom, String prenom, String biographie) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.biographie = biographie;
    }

    // ✅ Constructeur à partir de l'entité `Artiste`
    public ArtisteDTO(Artiste artiste) {
        if (artiste != null) {
            this.id = artiste.getId();
            this.nom = artiste.getNom();
            this.prenom = artiste.getPrenom();
            this.biographie = artiste.getBiographie();
        }
    }

    // ✅ Conversion DTO → Entité
    public Artiste toEntity() {

        return new Artiste(this.nom, this.prenom, this.biographie);
    }

    // ✅ Getters et Setters
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

    // ✅ Méthode toString() utile pour le logging
    @Override
    public String toString() {
        return "ArtisteDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", biographie='" + biographie + '\'' +
                '}';
    }
}
