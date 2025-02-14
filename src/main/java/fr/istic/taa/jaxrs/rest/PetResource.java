package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Pet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("pet")
@Produces({"application/json"})
public class PetResource {

  @GET
  @Path("/{petId}")
  @Operation(summary = "Find pet by ID",
          tags = {"pets"},
          description = "Returns a pet when 0 < ID <= 10.  ID > 10 or nonintegers will simulate API error conditions",
          responses = {
                  @ApiResponse(description = "The pet", content = @Content(
                          schema = @Schema(implementation = Pet.class)
                  )),
                  @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
                  @ApiResponse(responseCode = "404", description = "Pet not found")
          })
  public Pet getPetById(@PathParam("petId") Long petId)  {
      // return pet
      return new Pet();
  }
  @Path("/")
  public Pet getPet(Long petId)  {
      return new Pet();
  }


  @POST
  @Consumes("application/json")
  public Response addPet(
      @Parameter(description = "Pet object that needs to be added to the store", required = true) Pet pet) {
    // add pet
    return Response.ok().entity("SUCCESS").build();
  }
}