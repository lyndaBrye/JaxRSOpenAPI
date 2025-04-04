package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.DTO.ArtisteDTO;
import fr.istic.taa.jaxrs.dao.generic.ArtisteDao;
import fr.istic.taa.jaxrs.domain.Artiste;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("artistes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtisteResource {
    private final ArtisteDao artisteDao = new ArtisteDao();
    // 🔹 Récupérer un artiste par ID
    @GET
    @Path("/{id}")
    @Operation(summary = "Trouver un artiste par ID",
            description = "Retourne un artiste selon son identifiant",
            responses = {
                    @ApiResponse(description = "L'artiste trouvé",
                            content = @Content(schema = @Schema(implementation = ArtisteDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
            })
    public Response getArtisteById( @PathParam("id") Long id) {
        Artiste artiste = artisteDao.findOne(id);
        if (artiste == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Artiste non trouvé").build();
        }
        ArtisteDTO artisteDTO=new ArtisteDTO(artiste);
        return Response.ok(artisteDTO).build();
    }

    // 🔹 Ajouter un nouvel artiste
    @POST
    @Operation(summary = "Ajouter un artiste",
            description = "Ajoute un nouvel artiste à la base de données",
            responses = {@ApiResponse(responseCode = "201", description = "Artiste ajouté avec succès")})
    public Response addArtiste(@Parameter(description = "Artiste à ajouter", required = true) ArtisteDTO artisteDTO) {
        Artiste artiste = artisteDTO.toEntity();
        artisteDao.save(artiste);
        return Response.status(Response.Status.CREATED).entity(new ArtisteDTO(artiste)).build();
    }

    // 🔹 Mettre à jour un artiste
    @PUT
    @Path("/{id}")
    @Operation(summary = "Mettre à jour un artiste",
            description = "Met à jour les informations d'un artiste",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Artiste mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
            })
    public Response updateArtiste(@Parameter(description = "ID de l'artiste", required = true) @PathParam("id") Long id,
                                  @Parameter(description = "Données de l'artiste", required = true) ArtisteDTO artisteDTO) {
        Artiste artiste = artisteDao.findOne(id);
        if (artiste == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Artiste non trouvé").build();
        }
        // Mise à jour des champs
        artiste.setNom(artisteDTO.getNom());
        artiste.setPrenom(artisteDTO.getPrenom());
        artiste.setBiographie(artisteDTO.getBiographie());

        artisteDao.update(artiste);
        return Response.ok(new ArtisteDTO(artiste)).build();
    }

    // 🔹 Supprimer un artiste
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un artiste",
            description = "Supprime un artiste de la base de données",
            responses = {@ApiResponse(responseCode = "204", description = "Artiste supprimé avec succès")})
    public Response deleteArtiste(@Parameter(description = "ID de l'artiste à supprimer", required = true) @PathParam("id") Long id) {
        Artiste artiste = artisteDao.findOne(id);
        if (artiste == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Artiste non trouvé").build();
        }
        artisteDao.deleteById(id);
        return Response.noContent().build(); // 204 No Content
    }

    // 🔹 Récupérer tous les artistes
    @GET
    @Operation(summary = "Lister tous les artistes",
            description = "Retourne la liste complète des artistes",
            responses = {@ApiResponse(responseCode = "200", description = "Liste des artistes récupérée avec succès")})
    public Response getAllArtistes() {
        List<Artiste> artistes = artisteDao.findAll();
        List<ArtisteDTO> artisteDTOs = artistes.stream()
                .map(ArtisteDTO::new)
                .collect(Collectors.toList());

        return Response.ok(artisteDTOs).build();
    }
}
