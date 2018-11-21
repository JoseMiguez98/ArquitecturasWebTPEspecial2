package restControllers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestController {

	public class RecursoDuplicado extends WebApplicationException {
	     public RecursoDuplicado(int id) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con ID "+id+" ya existe").type(MediaType.TEXT_PLAIN).build());
	     }
	}
}
