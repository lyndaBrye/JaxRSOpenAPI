package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.domain.Ticket;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketRessource {

    private TicketDao ticketDao = new TicketDao();

    // Récupérer un ticket par ID
    @GET
    @Path("/{ticketId}")
    public Response getTicketById(@PathParam("ticketId") Long ticketId) {
        Ticket ticket = ticketDao.findOne(ticketId);
        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }
        return Response.ok(ticket).build();
    }

    // Récupérer tous les tickets
    @GET
    public Response getAllTickets() {
        List<Ticket> tickets = ticketDao.findAll();
        return Response.ok(tickets).build();
    }

    // Ajouter un nouveau ticket
    @POST
    public Response addTicket(
            @Parameter(description = "Ticket à ajouter", required = true) Ticket ticket) {
        if (ticket == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Données invalides").build();
        }
        ticketDao.save(ticket);
        return Response.status(Response.Status.CREATED).entity("Ticket ajouté avec succès").build();
    }

    // Mettre à jour un ticket
    @PUT
    @Path("/{ticketId}")
    public Response updateTicket(@PathParam("ticketId") Long ticketId, Ticket ticket) {
        Ticket existing = ticketDao.findOne(ticketId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }
        ticket.setId(ticketId); // Assure que l'ID est bien conservé
        ticketDao.update(ticket);
        return Response.ok("Ticket mis à jour avec succès").build();
    }

    // Supprimer un ticket
    @DELETE
    @Path("/{ticketId}")
    public Response deleteTicket(@PathParam("ticketId") Long ticketId) {
        Ticket existing = ticketDao.findOne(ticketId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }
        ticketDao.deleteById(ticketId);
        return Response.ok("Ticket supprimé avec succès").build();
    }
}
