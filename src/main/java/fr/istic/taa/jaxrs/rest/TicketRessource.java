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
        List<TicketDTO> ticketDTOs = tickets.stream()
                .map(TicketDTO::new)
                .collect(Collectors.toList());

        return Response.ok(ticketDTOs).build();
    }

    @POST
    public Response addTicket(TicketDTO ticketDTO) {
        if (ticketDTO == null || ticketDTO.getConcertId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Données invalides").build();
        }
        Concert concert = concertDao.findOne(ticketDTO.getConcertId());
        if (concert == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
        }
        User user = null;
        if (ticketDTO.getUserId() != null) {
            user = userDao.findOne(ticketDTO.getUserId());
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
            }
        }

        Ticket ticket = new Ticket (concert, user);
        ticketDao.save(ticket);
        return Response.status(Response.Status.CREATED).entity(new TicketDTO(ticket)).build();
    }
    @PUT
    @Path("/{ticketId}")
    public Response updateTicket(@PathParam("ticketId") Long ticketId, TicketDTO ticketDTO) {
        // Vérifier si le ticket existe
        Ticket existingTicket = ticketDao.findOne(ticketId);
        if (existingTicket == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }

        Concert concert = existingTicket.getConcert();
        if (ticketDTO.getConcertId() != null) {
            Concert newConcert = concertDao.findOne(ticketDTO.getConcertId());
            if (newConcert == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
            }
            concert = newConcert;      }

        // Récupérer l'ancien utilisateur et ne le changer que si un nouvel ID est fourni
        User user = existingTicket.getUser();
        if (ticketDTO.getUserId() != null) {
            User newUser = userDao.findOne(ticketDTO.getUserId());
            if (newUser == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
            }
            user = newUser;    }

        Ticket updatedTicket = ticketDTO.toEntity(ticketId, concert, user, existingTicket);

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
