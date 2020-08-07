package Module.Exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CustomException extends WebApplicationException{
    public CustomException(int status, String message){
        super(Response.status(status).entity(message).build());
    }
}
