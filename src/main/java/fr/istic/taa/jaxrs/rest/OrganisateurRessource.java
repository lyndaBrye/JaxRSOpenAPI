package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("organisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganisateurRessource {

    private OrganisateurDao organisateurDao = new OrganisateurDao();
    private TicketDao ticketDao = new TicketDao(); // Ajouté pour libérer les tickets

    // Récupérer un organisateur par ID
    @GET
    @Path("/{organisateurId}")
    public Response getOrganisateurById(@PathParam("organisateurId") Long organisateurId) {
        Organisateur organisateur = organisateurDao.findOne(organisateurId);
        if (organisateur == null || organisateur.getCompagnie() == null || organisateur.getCompagnie().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Organisateur non trouvé").build();
        }
        return Response.ok(organisateur).build();
    }

    // Récupérer tous les organisateurs
    @GET
    public Response getAllOrganisateurs() {
        List<Organisateur> organisateurs = organisateurDao.findAll();
        return Response.ok(organisateurs).build();
    }

    // Ajouter un nouvel organisateur
    @POST
    public Response addOrganisateur(Organisateur organisateur) {
        if (organisateur == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Données invalides").build();
        }
        organisateurDao.save(organisateur);
        return Response.status(Response.Status.CREATED).entity("Organisateur ajouté avec succès").build();
    }

    // Mettre à jour un organisateur
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
    // Supprimer un organisateur (seulement si plus de concerts liés)
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

}
