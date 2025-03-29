package fr.istic.taa.jaxrs.DTO;

import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;

public class TicketDTO {
    private Long id;
    private double prix;
    private Long concertId;
    private Long userId;

    // Constructeur par défaut (nécessaire pour la désérialisation JSON)
    public TicketDTO() {
    }

    // Constructeur pour convertir une entité Ticket en DTO (Entité -> DTO)
    public TicketDTO(Ticket ticket) {
        if (ticket != null) {
            this.id = ticket.getId();
            this.prix = ticket.getPrix();
            this.concertId = (ticket.getConcert() != null) ? ticket.getConcert().getId() : null;
            this.userId = (ticket.getUser() != null) ? ticket.getUser().getId() : null;
        }
    }
    public Ticket toEntity(Long ticketId, Concert concert, User user, Ticket existingTicket) {
        if (existingTicket == null) {
            existingTicket = new Ticket(); // Si le ticket n'existe pas, on crée un nouveau ticket
        }
        // Assurer que l'ID reste le même
        existingTicket.setId(ticketId);

        // Mettre à jour le prix seulement si le prix n'est pas nul
        if (this.prix != 0) { // Vérifier que le prix n'est pas 0 ou nul
            existingTicket.setPrix(this.prix);
        }

        // Mettre à jour le concertId si défini
        if (concertId != null) {
            existingTicket.setConcert(concert);
        }

        // Mettre à jour le userId si défini
        if (userId != null) {
            existingTicket.setUser(user);
        }

        // Retourner l'entité mise à jour
        return existingTicket;
    }


    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
