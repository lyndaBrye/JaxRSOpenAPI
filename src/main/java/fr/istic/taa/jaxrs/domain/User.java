package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String codePostal;
    private String email;
    private String tel;
    private String password;
    private int age;

    // La relation OneToMany pour les tickets de l'utilisateur
    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    public User() {
    }

    public User(String nom, String prenom, String codePostal, String email, String tel, String password, int age, Sexe sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.codePostal = codePostal;
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.age = age;
        this.sexe = sexe;
    }

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

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    // Ajout d'un ticket à la liste des tickets de l'utilisateur
    public void addTicket(Ticket ticket) {
        if (ticket != null && !this.tickets.contains(ticket)) {
            this.tickets.add(ticket);
        }
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    // Représentation sous forme de chaîne pour une meilleure lisibilité
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sexe=" + sexe +
                '}';
    }
}
