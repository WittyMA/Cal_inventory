package api.service;

import api.Applications;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("api/Applications")
public class ApplicationsRESTFacade extends AbstractFacade<Applications> {
  private static final Logger LOGGER = Logger.getLogger(api.service.ApplicationsRESTFacade.class.getName());
  
  @PersistenceContext(unitName = "my_persistence_unit")
  private EntityManager em;
  
  public ApplicationsRESTFacade() {
    super(Applications.class);
  }
  
  @POST
  @Consumes({"application/json"})
  public Response createApplication(Applications entity) {
    try {
      create(entity);
      return Response.status(Response.Status.CREATED).entity("Application created successfully").build();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error creating application", e);
      return Response.status(Response.Status.BAD_REQUEST).entity("Error creating application: " + e.getMessage()).build();
    } 
  }
  
  @PUT
  @Path("{id}")
  @Consumes({"application/json"})
  public Response editApplication(@PathParam("id") Integer id, Applications entity) {
    Applications existingApp = (Applications)find(id);
    if (existingApp == null)
      return Response.status(Response.Status.NOT_FOUND).entity("Application not found").build(); 
    try {
      entity.setId(id);
      edit(entity);
      return Response.ok("Application updated successfully").build();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error updating application", e);
      return Response.status(Response.Status.BAD_REQUEST).entity("Error updating application: " + e.getMessage()).build();
    } 
  }
  
  @DELETE
  @Path("{id}")
  public Response removeApplication(@PathParam("id") Integer id) {
    Applications entity = (Applications)find(id);
    if (entity == null)
      return Response.status(Response.Status.NOT_FOUND).entity("Application not found").build(); 
    remove(entity);
    return Response.ok("Application deleted successfully").build();
  }
  
  @GET
  @Path("{id}")
  @Produces({"application/json"})
  public Response findApplication(@PathParam("id") Integer id) {
    Applications entity = (Applications)find(id);
    if (entity == null)
      return Response.status(Response.Status.NOT_FOUND).entity("Application not found").build(); 
    return Response.ok(entity).build();
  }
  
  @GET
  @Produces({"application/json"})
  public Response findAllApplications() {
    List<Applications> applications = findAll();
    return Response.ok(applications).build();
  }
  
  @GET
  @Path("count")
  @Produces({"text/plain"})
  public String countREST() {
    return String.valueOf(count());
  }
  
  protected EntityManager getEntityManager() {
    return this.em;
  }
}
