package fr.istic.taa.jaxrs.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Organisateur;

import java.time.LocalDateTime;

public class ConcertDTO {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    private String lieu;
    private int capacity;



    private String libelle;



    private String imageUrl;
    private Long artiste_id;
    private Long organisateur_id;
    private double prix; // Ajout du prix

    // 🔹 Constructeur par défaut (nécessaire pour la désérialisation JSON)
    public ConcertDTO() {}

    // 🔹 Constructeur pour convertir une entité `Concert` en DTO
    public ConcertDTO(Concert concert) {
        if (concert != null) {
            this.id = concert.getId();
            this.date = concert.getDate();
            this.lieu = concert.getLieu();
            this.capacity = concert.getCapacity();
            this.artiste_id = (concert.getArtiste() != null) ? concert.getArtiste().getId() : null;
            this.organisateur_id = (concert.getOrganisateur() != null) ? concert.getOrganisateur().getId() : null;
            this.prix = concert.getPrix();
            this.libelle = concert.getLibelle();
            this.imageUrl = concert.getImageUrl();
        }
    }

    // 🔹 Convertir le DTO en `Concert` (Mettre à jour un concert existant en conservant les anciennes valeurs)
    public Concert toEntity(Concert existingConcert, Artiste artiste, Organisateur organisateur) {
        if (existingConcert == null) {
            existingConcert = new Concert(); // Si l'entité n'existe pas, en créer une nouvelle
        }

        // Mise à jour des champs uniquement s'ils ne sont pas nuls
        if (this.date != null) {
            existingConcert.setDate(this.date);
        }
        if (this.libelle != null) {
            existingConcert.setLibelle(this.libelle);
        }
        if (this.imageUrl != null) {
            existingConcert.setImageUrl(this.imageUrl);
        }
        if (this.lieu != null) {
            existingConcert.setLieu(this.lieu);
        }
        if (this.capacity > 0) { // Vérifier si capacity est valide avant de modifier
            existingConcert.setCapacity(this.capacity);
        }
        if (artiste != null) {
            existingConcert.setArtiste(artiste);
        }
        if (organisateur != null) {
            existingConcert.setOrganisateur(organisateur);
        }
        if(this.prix!=0){
            existingConcert.setPrix(prix);
        }

        return existingConcert;
    }

    // 🔹 Getters et Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
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

    public Long getArtiste_id() {
        return artiste_id;
    }

    public void setArtiste_id(Long artiste_id) {
        this.artiste_id = artiste_id;
    }

    public Long getOrganisateur_id() {
        return organisateur_id;
    }

    public void setOrganisateur_id(Long organisateur_id) {
        this.organisateur_id = organisateur_id;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
