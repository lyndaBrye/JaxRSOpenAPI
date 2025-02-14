package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.domain.Organisateur;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("organisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganisateurRessource {

    private OrganisateurDao organisateurDao = new OrganisateurDao();

    // Récupérer un organisateur par ID
    @GET
    @Path("/{organisateurId}")
    public Response getOrganisateurById(@PathParam("organisateurId") Long organisateurId) {
        Organisateur organisateur = organisateurDao.findOne(organisateurId);
        if (organisateur == null) {
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
    public Response addOrganisateur(
            @Parameter(description = "Organisateur à ajouter", required = true) Organisateur organisateur) {
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
        organisateur.setId(organisateurId); // Assure que l'ID est bien conservé
        organisateurDao.update(organisateur);
        return Response.ok("Organisateur mis à jour avec succès").build();
    }

    // Supprimer un organisateur
    @DELETE
    @Path("/{organisateurId}")
    public Response deleteOrganisateur(@PathParam("organisateurId") Long organisateurId) {
        Organisateur existing = organisateurDao.findOne(organisateurId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Organisateur non trouvé").build();
        }
        organisateurDao.deleteById(organisateurId);
        return Response.ok("Organisateur supprimé avec succès").build();
    }
}
