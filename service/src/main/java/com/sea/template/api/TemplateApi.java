package com.sea.template.api;

import com.sea.template.guest.JDBCGuestRepo;
import org.slf4j.Logger;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import static org.slf4j.LoggerFactory.getLogger;

@Path("/receptions")
public class TemplateApi {

    private static final Logger log = getLogger(TemplateApi.class);
    private JDBCGuestRepo jdbcGuestRepo;

    public TemplateApi(JDBCGuestRepo jdbcGuestRepo) {
        this.jdbcGuestRepo = jdbcGuestRepo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/manager/{manager}")
    public Response getManager(@PathParam("manager") String name) {
        String managerMsg = String.format("Manager %s's Greeting!", name);
        return Response.ok(managerMsg).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{guest}")
    public Response getGuest(@PathParam("guest") String name) {
        try {
            return Response.ok(jdbcGuestRepo.getGuest(name)).build();
        } catch (RuntimeException e) {
            log.error("No record for guest: [{}]", name);
            return  Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register-name")
    public Response postContact(String name) {
        try {
            log.info("received guest name: [{}]", name);
            return Response.ok(jdbcGuestRepo.persistGuest(name)).build();
        } catch (RuntimeException e) {
            log.error("Unable to insert guest: [{}]", name);
            return  Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

}
