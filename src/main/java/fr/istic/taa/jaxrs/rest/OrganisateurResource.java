package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("organisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganisateurResource {

    private OrganisateurDao organisateurDao = new OrganisateurDao();
    private TicketDao ticketDao = new TicketDao(); // Ajouté pour libérer les tickets

    // Récupérer un organisateur par ID
    @GET
    @Path("/{organisateurId}")
    @Operation(summary = "Trouver un organisateur par ID",
            description = "Retourne un organisateur selon son identifiant",
            responses = {
                    @ApiResponse(description = "L'organisateur trouvé"),
                    @ApiResponse(responseCode = "404", description = "Organisateur non trouvé")
            })
    public Response getOrganisateurById(@PathParam("organisateurId") Long organisateurId) {
        Organisateur organisateur = organisateurDao.findOne(organisateurId);
        if (organisateur == null || organisateur.getCompagnie() == null || organisateur.getCompagnie().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Organisateur non trouvé").build();
        }
        return Response.ok(organisateur).build();
    }

@Operation(summary = "Récupérer tous les organisateurs",
            description = "Retourne une liste de tous les organisateurs",
            responses = {@ApiResponse(responseCode = "200", description = "Liste des organisateurs")})
    @GET
    public Response getAllOrganisateurs() {
        List<Organisateur> organisateurs = organisateurDao.findAll();
        return Response.ok(organisateurs).build();
    }

    @Operation(summary = "Ajouter un organisateur",
            description = "Ajoute un nouvel organisateur ",
            responses = {@ApiResponse(responseCode = "201", description = "Organisateur ajouté avec succès")})
    @POST
    public Response addOrganisateur(Organisateur organisateur) {
        if (organisateur == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Données invalides").build();
        }
        organisateurDao.save(organisateur);
        return Response.status(Response.Status.CREATED).entity("Organisateur ajouté avec succès").build();
    }

    @Operation(summary = "Mettre à jour un organisateur",
            description = "Met à jour les informations d'un organisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organisateur mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Organisateur non trouvé")
            })
    @PUT
    @Path("/{organisateurId}")
    public Response updateOrganisateur(@PathParam("organisateurId") Long organisateurId, Organisateur organisateur) {
        Organisateur existing = organisateurDao.findOne(organisateurId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Organisateur non trouvé").build();
        }
        organisateur.setId(organisateurId); // Conserver l'ID original
        if (organisateur.getCompagnie() != null) {
            existing.setCompagnie(organisateur.getCompagnie());
        }
        organisateurDao.update(existing);
        return Response.ok("Organisateur mis à jour avec succès").build();
    }
@Operation(summary = "Supprimer un organisateur",
            description = "Supprime un organisateur s'il n'est pas lié à des concerts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organisateur supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Organisateur non trouvé"),
                    @ApiResponse(responseCode = "403", description = "Impossible de supprimer l'organisateur : concerts encore associés.")
            })
    @DELETE
    @Path("/{organisateurId}")
    public Response deleteOrganisateur(@PathParam("organisateurId") Long organisateurId) {
        Organisateur existing = organisateurDao.findOne(organisateurId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Organisateur non trouvé").build();
        }

        if (existing.getConcerts() != null && !existing.getConcerts().isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Impossible de supprimer l'organisateur : concerts encore associés.").build();
        }

        // Libération des tickets liés à cet organisateur (en tant qu'utilisateur)
        List<Ticket> tickets = existing.getTickets();
        for (Ticket ticket : tickets) {
            ticket.setUser(null);
            ticketDao.update(ticket);
        }

        organisateurDao.deleteById(organisateurId);
        return Response.ok("Organisateur supprimé avec succès").build();
    }
    @DELETE
    @Path("/{organisateurId}/concerts")
    public Response deleteAllConcertsForOrganisateur(@PathParam("organisateurId") Long organisateurId) {
        Organisateur organisateur = organisateurDao.findOne(organisateurId);
        if (organisateur == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Organisateur non trouvé").build();
        }

        if (organisateur.getConcerts() == null || organisateur.getConcerts().isEmpty()) {
            return Response.ok("Aucun concert à supprimer pour cet organisateur.").build();
        }

        // Supprimer chaque concert manuellement
        organisateur.getConcerts().forEach(concert -> concert.setOrganisateur(null));
        organisateur.getConcerts().clear(); // Vide la liste (orphanRemoval = true fait le reste)

        organisateurDao.update(organisateur); // Persiste la modification

        return Response.ok("Tous les concerts de l'organisateur ont été supprimés.").build();
    }

    @Operation(summary = "Authentification de l'organisateur",
            description = "Vérifie les informations d'identification de l'organisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentification réussie"),
                    @ApiResponse(responseCode = "401", description = "Email ou mot de passe incorrect")
            })
    @POST
    @Path("/login")
    public Response login(Organisateur organisateurInput) {
        List<Organisateur> organisateurs = organisateurDao.findAll();
        for (Organisateur o : organisateurs) {
            if (o.getEmail().equals(organisateurInput.getEmail()) && o.getPassword().equals(organisateurInput.getPassword())) {
                return Response.ok(o).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Email ou mot de passe incorrect").build();
    }

}
