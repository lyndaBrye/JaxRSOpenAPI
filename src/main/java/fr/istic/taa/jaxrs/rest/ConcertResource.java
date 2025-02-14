package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.generic.ConcertDao;
import fr.istic.taa.jaxrs.domain.Concert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;
@Path("/concerts")
@Produces("application/json")
@Consumes("application/json")
public class ConcertResource {
    private ConcertDao concertDao = new ConcertDao();

    @GET
    @Operation(summary = "Lister tous les concerts", description = "Retourne la liste complète des concerts",
            responses = {@ApiResponse(description = "Liste des concerts", content = @Content(
                    schema = @Schema(implementation = Concert.class)))} )
    public List<Concert> getAllConcerts() {
        return concertDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Trouver un concert par ID", description = "Retourne un concert en fonction de son identifiant",
            responses = {
                    @ApiResponse(description = "Le concert", content = @Content(
                            schema = @Schema(implementation = Concert.class))),
                    @ApiResponse(responseCode = "404", description = "Concert non trouvé")
            })
    public Response getConcertById(@Parameter(description = "ID du concert", required = true) @PathParam("id") Long id) {
        Concert concert = concertDao.findOne(id);
        if (concert != null) {
            return Response.ok(concert).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
        }
    }

    @POST
    @Operation(summary = "Ajouter un concert", description = "Ajoute un nouveau concert à la base de données",
            responses = {@ApiResponse(responseCode = "201", description = "Concert ajouté avec succès")})
    public Response addConcert(@Parameter(description = "Concert à ajouter", required = true) Concert concert) {
        concertDao.save(concert);
        return Response.status(Response.Status.CREATED).entity("Concert ajouté").build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Mettre à jour un concert", description = "Met à jour les informations d'un concert existant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Concert mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Concert non trouvé")
            })
    public Response updateConcert(@PathParam("id") Long id, @Parameter(description = "Concert mis à jour", required = true) Concert concert) {
        Concert existingConcert = concertDao.findOne(id);
        if (existingConcert != null) {
            concert.setId(id);
            concertDao.update(concert);
            return Response.ok("Concert mis à jour").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un concert", description = "Supprime un concert de la base de données",
            responses = {@ApiResponse(responseCode = "200", description = "Concert supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Concert non trouvé")})
    public Response deleteConcert(@PathParam("id") Long id) {
        Concert concert = concertDao.findOne(id);
        if (concert != null) {
            concertDao.delete(concert);
            return Response.ok("Concert supprimé").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
        }
    }
}