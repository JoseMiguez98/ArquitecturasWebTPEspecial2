package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entities.Project;

public class ProjectDAO implements DAO<Project, Integer>{
	private static ProjectDAO daoProject;

	public static ProjectDAO getInstance() {
		if(daoProject==null) {
			daoProject = new ProjectDAO();
		}
		return daoProject;
	}

	@Override
	public Project persist(Project entity) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
		return entity;
	}

	@Override
	public Project update(Integer id, Project updatedProject) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		Project project = em.find(Project.class, id);
		try {
			if(project!=null) {
				project.setAuthor(updatedProject.getAuthor());
				project.setCategory(updatedProject.getCategory());
				project.setName(updatedProject.getName());
				project.setTopics(updatedProject.getTopics());
			}
			else {
				em.persist(updatedProject);
			}
		}
		finally {
			em.flush();
			em.getTransaction().commit();
			em.close();
		}
		return updatedProject;
	}

	@Override
	public Project findById(Integer id) {
		EntityManager em=ContextListener.createEntityManager();
		Project project = em.find(Project.class, id);
		em.close();
		return project;
	}

	@Override
	public List<Project> findAll() {
		EntityManager em=ContextListener.createEntityManager();
		TypedQuery<Project>query = em.createQuery("Select p from Project p", Project.class);
		List<Project>projects = query.getResultList();
		em.close();
		return projects;
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		Project project = em.find(Project.class, id);
		try {
			em.remove(project);
		}
		finally {
			em.getTransaction().commit();
			em.close();
		}
		return project!=null;
	}
	
	/**
	 * Consultar trabajos de investigación y sus propiedades.
	 * @param em
	 * @return List with all works properties
	 */
	public List<String>getAllResearchWorksInfo(){
		List<String>worksInfo = new ArrayList<String>();
		List<Project>projects = this.findAll();
		
		for(Project p : projects) {
			worksInfo.add(p.toString());
		}
		
		return worksInfo;
	}
}
