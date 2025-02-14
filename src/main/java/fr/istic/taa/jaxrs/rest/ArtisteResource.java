package fr.istic.taa.jaxrs.rest;
import fr.istic.taa.jaxrs.dao.generic.ArtisteDao;
import fr.istic.taa.jaxrs.domain.Artiste;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;
@Path("/artistes")
@Produces("application/json")
@Consumes("application/json")
public class ArtisteResource {
    private final ArtisteDao artisteDao = new ArtisteDao();

    @GET
    @Path("/{id}")
    @Operation(summary = "Trouver un artiste par ID",
            description = "Retourne un artiste selon son identifiant",
            responses = {
                    @ApiResponse(description = "L'artiste trouvé",
                            content = @Content(schema = @Schema(implementation = Artiste.class))),
                    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
            })
    public Response getArtisteById(@Parameter(description = "ID de l'artiste", required = true) @PathParam("id") Long id) {
        Artiste artiste = artisteDao.findOne(id);
        if (artiste != null) {
            return Response.ok(artiste).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Artiste non trouvé").build();
    }

    @POST
    @Operation(summary = "Ajouter un artiste",
            description = "Ajoute un nouvel artiste à la base de données",
            responses = {@ApiResponse(responseCode = "200", description = "Artiste ajouté avec succès")})
    public Response addArtiste(@Parameter(description = "Artiste à ajouter", required = true) Artiste artiste) {
        artisteDao.save(artiste);
        return Response.ok("SUCCESS").build();
    }

    @PUT
    @Operation(summary = "Mettre à jour un artiste",
            description = "Met à jour les informations d'un artiste",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Artiste mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
            })
    public Response updateArtiste(@Parameter(description = "Artiste à mettre à jour", required = true) Artiste artiste) {
        if (artisteDao.findOne(artiste.getId()) != null) {
            artisteDao.update(artiste);
            return Response.ok("SUCCESS").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Artiste non trouvé").build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un artiste",
            description = "Supprime un artiste de la base de données",
            responses = {@ApiResponse(responseCode = "200", description = "Artiste supprimé avec succès")})
    public Response deleteArtiste(@Parameter(description = "ID de l'artiste à supprimer", required = true) @PathParam("id") Long id) {
        if (artisteDao.findOne(id) != null) {
            artisteDao.deleteById(id);
            return Response.ok("SUCCESS").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Artiste non trouvé").build();
    }

    @GET
    @Operation(summary = "Lister tous les artistes",
            description = "Retourne la liste complète des artistes",
            responses = {@ApiResponse(responseCode = "200", description = "Liste des artistes récupérée avec succès")})
    public Response getAllArtistes() {
        List<Artiste> artistes = artisteDao.findAll();
        return Response.ok(artistes).build();
    }
}
