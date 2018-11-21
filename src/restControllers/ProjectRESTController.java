package restControllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ProjectDAO;
import entities.Project;

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
}
