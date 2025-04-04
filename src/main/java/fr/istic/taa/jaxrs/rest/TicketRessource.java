package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.DTO.TicketDTO;
import fr.istic.taa.jaxrs.dao.generic.ConcertDao;
import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.dao.generic.UserDao;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketRessource {

    private TicketDao ticketDao = new TicketDao();
    private ConcertDao concertDao = new ConcertDao();
    private UserDao userDao = new UserDao();

    // Récupérer un ticket par ID
    @GET
    @Path("/{ticketId}")
    public Response getTicketById(@PathParam("ticketId") Long ticketId) {
        Ticket ticket = ticketDao.findOne(ticketId);
        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }
        TicketDTO ticketDTO = new TicketDTO(ticket);
        return Response.ok(ticketDTO).build();
    }

    // Récupérer tous les tickets
    @GET
    @Path("/all")
    public Response getAllTickets() {
        List<Ticket> tickets = ticketDao.findAllWithConcertAndUser(); // Récupère les tickets avec leurs relations

        if (tickets == null || tickets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun ticket trouvé").build();
        }

        // Transformation en DTO
        List<TicketDTO> ticketDTOs = tickets.stream()
                .map(TicketDTO::new) // Utilise directement le constructeur TicketDTO(Ticket)
                .collect(Collectors.toList());

        return Response.ok(ticketDTOs).build();
    }



    // Ajouter un nouveau ticket
    @POST
    public Response addTicket(TicketDTO ticketDTO) {
        if (ticketDTO == null || ticketDTO.getConcertId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Données invalides").build();
        }

        // Récupérer l'entité Concert
        Concert concert = concertDao.findOne(ticketDTO.getConcertId());
        if (concert == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
        }

        // Si userId est null, ne pas récupérer d'utilisateur
        User user = null;
        if (ticketDTO.getUserId() != null) {
            user = userDao.findOne(ticketDTO.getUserId());
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
            }
        }

        // Créer le ticket avec un utilisateur null si non spécifié
        Ticket ticket = new Ticket (concert, user);

        // Sauvegarder le ticket dans la base de données
        ticketDao.save(ticket);

        // Retourner le DTO du ticket ajouté
        return Response.status(Response.Status.CREATED).entity(new TicketDTO(ticket)).build();
    }




    // Mettre à jour un ticket
    @PUT
    @Path("/{ticketId}")
    public Response updateTicket(@PathParam("ticketId") Long ticketId, TicketDTO ticketDTO) {
        // Vérifier si le ticket existe
        Ticket existingTicket = ticketDao.findOne(ticketId);
        if (existingTicket == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }

        // Récupérer l'ancien concert et ne le changer que si un nouvel ID est fourni
        Concert concert = existingTicket.getConcert();
        if (ticketDTO.getConcertId() != null) {
            Concert newConcert = concertDao.findOne(ticketDTO.getConcertId());
            if (newConcert == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
            }
            concert = newConcert; // Met à jour uniquement si un nouvel ID valide est donné
        }

        // Récupérer l'ancien utilisateur et ne le changer que si un nouvel ID est fourni
        User user = existingTicket.getUser();
        if (ticketDTO.getUserId() != null) {
            User newUser = userDao.findOne(ticketDTO.getUserId());
            if (newUser == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
            }
            user = newUser; // Met à jour uniquement si un nouvel ID valide est donné
        }

        // Convertir le DTO en entité tout en conservant les anciennes valeurs si null
        Ticket updatedTicket = ticketDTO.toEntity(ticketId, concert, user, existingTicket);

        // Mettre à jour la base de données
        ticketDao.update(updatedTicket);

        return Response.ok("Ticket mis à jour avec succès").build();
    }


    // Supprimer un ticket OK
    @DELETE
    @Path("/{ticketId}")
    public Response deleteTicket(@PathParam("ticketId") Long ticketId) {
        Ticket existing = ticketDao.findOne(ticketId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }
        existing.setConcert(null);
        ticketDao.deleteById(ticketId);
        return Response.ok("Ticket supprimé avec succès").build();
    }
}
