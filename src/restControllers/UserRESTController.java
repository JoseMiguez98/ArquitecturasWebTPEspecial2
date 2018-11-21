package restControllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.UserDAO;
import entities.User;

@Path("/users")
public class UserRESTController extends RestController {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return UserDAO.getInstance().findAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {
		User result= UserDAO.getInstance().persist(user);
		if(result==null) {
			throw new RecursoDuplicado(user.getId_user());
		}else {
			return Response.status(201).entity(user).build();
		}
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") int id,User user) {
		User user_entity = UserDAO.getInstance().findById(id);
		if(user_entity!=null) {
			UserDAO.getInstance().update(id, user);
			return Response.status(200).entity(user).build();
		}
		throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		User user = UserDAO.getInstance().findById(id);
		if(user!=null)
			return user;
		else
			throw new RecursoNoExiste(id);
	}
}
