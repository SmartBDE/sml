package sml.smartdbe.me;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface HelloService extends RestService {
    @POST
    @Path("/webapi/hello")
    public void order(MethodCallback<String> callback);
}