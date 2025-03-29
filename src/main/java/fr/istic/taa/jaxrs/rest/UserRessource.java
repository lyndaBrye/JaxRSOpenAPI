package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.DTO.TicketDTO;
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

        // Transformation en DTO
        List<UserDTO> userDTOS = users.stream()
                .map(UserDTO::new) // Utilise directement le constructeur TicketDTO(Ticket)
                .collect(Collectors.toList());

        return Response.ok(userDTOS).build();
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
    public Response updateUser(@PathParam("userId") Long userId, UserDTO userDTO) {
        // Vérifier si l'utilisateur existe déjà dans la base de données
        User existing = userDao.findOne(userId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
        }

        // Convertir le DTO en entité User et mettre à jour les champs existants
        User updatedUser = userDTO.toEntity(userId, existing);

        // Sauvegarder l'utilisateur mis à jour
        userDao.update(updatedUser);

        // Retourner une réponse indiquant que la mise à jour a réussi
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
