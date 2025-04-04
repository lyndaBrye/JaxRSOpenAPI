package fr.istic.taa.jaxrs.DTO;

import fr.istic.taa.jaxrs.domain.Sexe;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.dao.generic.TicketDao;

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
    public User toEntity(Long userId, User existingUser,TicketDao ticketDao) {
        if (existingUser == null) {
            existingUser = new User(); // Si l'utilisateur n'existe pas, on crée un nouvel utilisateur
        }

        // Assurer que l'ID reste le même
        existingUser.setId(userId);

        // Mettre à jour les champs non null du DTO
        if (this.nom != null) {
            existingUser.setNom(this.nom);
        }
        if (this.prenom != null) {
            existingUser.setPrenom(this.prenom);
        }
        if (this.email != null) {
            existingUser.setEmail(this.email);
        }
        if (this.tel != null) {
            existingUser.setTel(this.tel);
        }
        if (this.age != 0) { // Vérifier l'âge seulement si non zéro
            existingUser.setAge(this.age);
        }
        if (this.sexe != null) {
            existingUser.setSexe(Sexe.valueOf(this.sexe)); // Assurez-vous que l'énumération Sexe est bien définie
        }

       // Retourner l'entité mise à jour
        return existingUser;
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
