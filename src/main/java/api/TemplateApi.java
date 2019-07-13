package api;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/threats/{guest}")
@Produces(MediaType.APPLICATION_JSON)
public class TemplateApi {

    @GET
    public String getThreats(@PathParam("guest") String name) {
        return String.format("Hands up, %s !", name);
    }
}
