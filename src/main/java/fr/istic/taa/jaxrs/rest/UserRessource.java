package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.DTO.UserDTO;
import fr.istic.taa.jaxrs.dao.generic.UserDao;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

//penser a ajouter la notion de secuirté dans les méthodes + doublons d'emmail et username
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRessource {

    private UserDao userDao = new UserDao();


    // Récupérer un utilisateur par ID
    @GET
    @Path("/{userId}")
    public Response getUserById(@PathParam("userId") Long userId) {
        User user = userDao.findOne(userId);
        System.out.println(user.toString()+"sdfghjhklfghjk");

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
        }
        UserDTO userDTO = new UserDTO(user); // Conversion de User en UserDTO
        return Response.ok(userDTO).build(); // Retourner le DTO dans la réponse
    }


    @GET
    @Path("/all")
    public Response getAllUsers() {
        List<User> users = userDao.findAll();
        if (users == null || users.isEmpty()) { // Vérification que la liste d'utilisateurs n'est pas vide
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun utilisateur trouvé").build();
        }

        // Transforme chaque utilisateur en un UserDTO
        List<UserDTO> userDTOs = users.stream()
                .map(user -> {
                    // Récupérer les tickets pour chaque utilisateur
                    List<Ticket> tickets = userDao.getTicketsByUserId(user.getId());

                    // Créer un UserDTO à partir de l'utilisateur
                    UserDTO userDTO = new UserDTO(user);

                    // Transformer les tickets en IDs et les ajouter dans le UserDTO
                    List<Long> ticketIds = tickets.stream()
                            .map(Ticket::getId) // Transformer chaque ticket en son ID
                            .collect(Collectors.toList());

                    // Ajouter les ticketIds dans le DTO
                    userDTO.setTicketIds(ticketIds); // Le nom de la méthode est setTicketIds, pas setTicket

                    return userDTO;  // Retourner l'utilisateur DTO avec les tickets associés
                })
                .collect(Collectors.toList());

        // Retourner les UserDTOs avec leurs tickets
        return Response.ok(userDTOs).build();  // On renvoie la liste des DTOs, pas les entités User
    }



    // Ajouter un nouvel utilisateur
    @POST
    public Response addUser(
            @Parameter(description = "Utilisateur à ajouter", required = true) User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Données invalides").build();
        }
        userDao.save(user);
        return Response.status(Response.Status.CREATED).entity("Utilisateur ajouté avec succès").build();
    }

    // Mettre à jour un utilisateur
    // ca met bien a jour tout les utilisateurs sauf que si on rempli pas tout les champs il met vide les champs manquants
    @PUT
    @Path("/{userId}")
    public Response updateUser(@PathParam("userId") Long userId, User user) {
        User existing = userDao.findOne(userId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
        }
        user.setId(userId); // Assure que l'ID ne change pas
        userDao.update(user);
        return Response.ok("Utilisateur mis à jour avec succès").build();
    }

    // Supprimer un utilisateur
    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") Long userId) {
        User existing = userDao.findOne(userId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
        }
        userDao.deleteById(userId);
        return Response.ok("Utilisateur supprimé avec succès").build();
    }
}
