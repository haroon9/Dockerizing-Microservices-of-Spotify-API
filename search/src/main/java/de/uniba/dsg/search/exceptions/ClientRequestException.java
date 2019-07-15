package de.uniba.dsg.search.exceptions;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import de.uniba.dsg.search.models.ErrorMessage;

public class ClientRequestException extends WebApplicationException {
    public ClientRequestException(ErrorMessage message) {
        super(Response.status(400).entity(message).build());
    }
}
