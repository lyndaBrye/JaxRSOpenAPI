package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.DTO.TicketDTO;
import fr.istic.taa.jaxrs.dao.generic.ConcertDao;
import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.dao.generic.UserDao;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    private TicketDao ticketDao = new TicketDao();
    private ConcertDao concertDao = new ConcertDao();
    private UserDao userDao = new UserDao();
    @Operation(summary = "Récupérer un ticket par ID",
            description = "Retourne un ticket selon son identifiant",
            responses = {
                    @ApiResponse(description = "Le ticket trouvé",
                            content = @Content(schema = @Schema(implementation = TicketDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Ticket non trouvé")
            })
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
    @Operation(summary = "Récupérer tous les tickets",
            description = "Retourne une liste de tous les tickets",
            responses = {@ApiResponse(responseCode = "200", description = "Liste des tickets")})
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
    @Operation(summary = "Ajouter un ticket",
            description = "Achat d'un ticket pour un concert",
            responses = {@ApiResponse(responseCode = "201", description = "Ticket ajouté avec succès")})
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
    @Operation(summary = "Mettre à jour un ticket",
            description = "Met à jour les informations d'un ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Ticket non trouvé")
            })
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
