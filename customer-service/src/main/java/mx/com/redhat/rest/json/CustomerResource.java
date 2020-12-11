package mx.com.redhat.rest.json;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.jaxrs.PathParam;


@Path("/api/customer")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class CustomerResource {

    @GET
    public List<Customer> getAll(){
        return Customer.listAll();
        
    }

    @GET
    @Path("{accountId}")
    public List<Customer> getCustomerByAccountId(@PathParam String accountId){
        return Customer.<Customer>streamAll()
        .filter(p -> p.accountId.equals(accountId))
        .collect(Collectors.toList());
    }
    
    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                    .build();
        }

    }
}
