package restControllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ProjectDAO;
import dao.RevisionDAO;
import dao.UserDAO;
import entities.Project;
import entities.Revision;
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

	@GET
	@Path("/{id}/rev")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getUserRevisions(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		User user = UserDAO.getInstance().findById(id);
		if(user!=null)
			return UserDAO.getInstance().getRevisions(id);
		else
			throw new RecursoNoExiste(id);
	}

	@GET
	@Path("/{id}/findRevBetween")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> findPerrosByEdad(@QueryParam("from") String from,
			@QueryParam("to") String to, @PathParam("id") int id) {
		List <Project> revs = new ArrayList<Project>();
		try {
			revs = UserDAO.getInstance().getRevisionsBetween(id, from, to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revs;
	}

	@POST
	@Path("/rev")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRevision(Revision rev) {
		User user = UserDAO.getInstance().findById(rev.getIdUser());
		if(user==null) {
			throw new RecursoNoExiste(rev.getIdUser());
		}else {

			Project project = ProjectDAO.getInstance().findById(rev.getIdProject());
			if(project == null) {
				throw new RecursoNoExiste(rev.getIdProject());
			}

			else {

				try {
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String date = formatter.format(rev.getRevisionDate());
					Revision revision = user.addRevision(project, date);
					RevisionDAO.getInstance().persist(revision);
					UserDAO.getInstance().update(user.getId_user(), user);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Response.status(201).entity(rev).build();	

			}
		}
	}

	@GET
	@Path("/{id}/works")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getAllResearchWorks(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		User user = UserDAO.getInstance().findById(id);
		if(user!=null)
			return UserDAO.getInstance().getAllResearchWorks(id);
		else
			throw new RecursoNoExiste(id);
	}
}