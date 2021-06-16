package sml.smartdbe.me;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface HelloService extends RestService {
    @GET
    @Path("/webapi/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public void order(MethodCallback<String> callback);
}