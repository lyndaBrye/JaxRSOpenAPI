package fr.istic.taa.jaxrs.rest;
import fr.istic.taa.jaxrs.DTO.ConcertDTO;
import fr.istic.taa.jaxrs.DTO.TicketDTO;
import fr.istic.taa.jaxrs.DTO.UserDTO;
import fr.istic.taa.jaxrs.dao.generic.*;
import fr.istic.taa.jaxrs.domain.*;
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

@Path("concerts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConcertResource {
    private ConcertDao concertDao = new ConcertDao();
    private ArtisteDao artisteDao = new ArtisteDao();
    private OrganisateurDao organisateurDao = new OrganisateurDao();
    private TicketDao ticketDao = new TicketDao();

    @GET
    @Path("/all")
    public Response getAllConcerts() {
        System.out.println("Execution de la méthode getAllConcerts()");

        List<Concert> concerts = concertDao.findAll();

        if (concerts.isEmpty()) {
            System.out.println("Aucun concert trouvé");
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun concert trouvé").build();
        }

        List<ConcertDTO> concertDTOS = concerts.stream().map(ConcertDTO::new).collect(Collectors.toList());
        return Response.ok(concertDTOS).build();
    }


    @GET
    @Path("/{id}")
    @Operation(summary = "Trouver un concert par ID", description = "Retourne un concert en fonction de son identifiant",
            responses = {
                    @ApiResponse(description = "Le concert", content = @Content(
                            schema = @Schema(implementation = ConcertDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Concert non trouvé")
            })
    public Response getConcertById(@PathParam("id") Long id) {
        Concert concert = concertDao.findOne(id);
        if (concert != null) {
            return Response.ok(new ConcertDTO(concert)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
        }
    }

    @POST
    @Operation(summary = "Ajouter un concert", description = "Ajoute un nouveau concert à la base de données",
            responses = {@ApiResponse(responseCode = "201", description = "Concert ajouté avec succès")})
    public Response addConcert(@Parameter(description = "Concert à ajouter", required = true) ConcertDTO concertDTO) {
        Artiste artiste = artisteDao.findOne(concertDTO.getArtiste_id());
        Organisateur organisateur = organisateurDao.findOne(concertDTO.getOrganisateur_id());

        if (artiste == null || organisateur == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Artiste ou Organisateur non trouvé").build();
        }

        Concert concert = new Concert(concertDTO.getDate(), concertDTO.getLieu(), concertDTO.getCapacity(),concertDTO.getPrix());
        concert.setArtiste(artiste);
        concert.setOrganisateur(organisateur);
        for(int i = 0; i < concertDTO.getCapacity(); i++) {
            Ticket ticket = new Ticket(concert, null);
            concert.getTickets().add(ticket); // Important : relier via la liste du concert
        }

        concertDao.save(concert);


        return Response.status(Response.Status.CREATED).entity(new ConcertDTO(concert)).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Mettre à jour un concert", description = "Met à jour les informations d'un concert existant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Concert mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Concert non trouvé")
            })
    public Response updateConcert(@PathParam("id") Long id, ConcertDTO concertDTO) {
        Concert existingConcert = concertDao.findOne(id);
        if (existingConcert == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Concert non trouvé").build();
        }

        Artiste artiste = (concertDTO.getArtiste_id() != null) ? artisteDao.findOne(concertDTO.getArtiste_id()) : existingConcert.getArtiste();
        Organisateur organisateur = (concertDTO.getOrganisateur_id() != null) ? organisateurDao.findOne(concertDTO.getOrganisateur_id()) : existingConcert.getOrganisateur();

        Concert updatedConcert = concertDTO.toEntity(existingConcert, artiste, organisateur);
        concertDao.update(updatedConcert);

        return Response.ok(new ConcertDTO(updatedConcert)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un concert", description = "Supprime un concert de la base de données",
            responses = {@ApiResponse(responseCode = "200", description = "Concert supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Concert non trouvé")})
    public Response deleteConcert(@PathParam("id") Long id) {

        Concert concert = concertDao.findOne(id); // Récupère le concert par son ID
        if (concert != null) {
            concertDao.deleteById(id);
            return Response.ok("Concert supprimé avec succès").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Concert non trouvé")
                    .build();
        }
    }
}
