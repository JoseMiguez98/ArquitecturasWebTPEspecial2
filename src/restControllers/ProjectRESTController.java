package restControllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ProjectDAO;
import dao.UserDAO;
import entities.Project;
import entities.User;
import restControllers.RestController.RecursoNoExiste;

@Path("/projects")
public class ProjectRESTController extends RestController {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getAllProjects() {
		return ProjectDAO.getInstance().findAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProject(Project project) {
		Project result= ProjectDAO.getInstance().persist(project);
		if(result==null) {
			throw new RecursoDuplicado(project.getId_project());
		}else {
			return Response.status(201).entity(project).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Project getProjectById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Project project= ProjectDAO.getInstance().findById(id);
		if(project!=null)
			return project;
		else
			throw new RecursoNoExiste(id);
	}
	
//	@PUT
//	@Path("/{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateProject(@PathParam("id") int id,Project project) {
//		Project project_entity = ProjectDAO.getInstance().findById(id);
//		if(project_entity!=null) {
//			ProjectDAO.getInstance().update(id, project);
//			return Response.status(200).entity(project).build();
//		}
//		throw new RecursoNoExiste(id);
//	}
}
