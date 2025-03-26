package fr.istic.taa.jaxrs.DTO;

import fr.istic.taa.jaxrs.domain.Sexe;
import fr.istic.taa.jaxrs.domain.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private int age;
    private String sexe; // Ou Enum selon ton cas
    private List<Long> ticketIds; // On stocke uniquement les IDs des tickets liés

    // Constructeur vide (Obligatoire pour la conversion JSON)
    public UserDTO() {}

    // Constructeur pour transformer un User en UserDTO
    public UserDTO(User user) {
        this.id = user.getId();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.email = user.getEmail();
        this.tel = user.getTel();
        this.age = user.getAge();

        // Vérification de null avant d'appeler name()
        this.sexe = (user.getSexe() != null) ? user.getSexe().name() : "Non spécifié"; // "Non spécifié" ou autre valeur par défaut
        this.ticketIds = user.getTickets().stream()
                .map(ticket -> ticket.getId())
                .collect(Collectors.toList());
    }


    // Méthode pour convertir un DTO en User (utile pour POST / PUT)
    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setNom(this.nom);
        user.setPrenom(this.prenom);
        user.setEmail(this.email);
        user.setTel(this.tel);
        user.setAge(this.age);
        user.setSexe(Sexe.valueOf(this.sexe)); // Convertir string en Enum
        // Pour les tickets, il faudra récupérer les entités depuis les IDs dans le service
        return user;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }
}
