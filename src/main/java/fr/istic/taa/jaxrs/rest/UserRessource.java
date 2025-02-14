package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.generic.UserDao;
import fr.istic.taa.jaxrs.domain.User;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

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
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Utilisateur non trouvé").build();
        }
        return Response.ok(user).build();
    }

    // Récupérer tous les utilisateurs
    @GET
    @Path("/all")
    public Response getAllUsers() {
        List<User> users = userDao.findAll();
        return Response.ok(users).build();
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
