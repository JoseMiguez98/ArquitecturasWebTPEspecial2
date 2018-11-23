package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entities.Project;
import entities.Revision;
import entities.User;

/**
 * 
 * @author <a href="https://github.com/JoseMiguez98/">Jose Miguez</a>
 *
 */
public class UserDAO implements DAO<User, Integer> {
	public static UserDAO daoUser;

	public static UserDAO getInstance() {
		if(daoUser==null) {
			daoUser = new UserDAO();
		}
		return daoUser;
	}

	@Override
	public User persist(User entity) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);	
		em.getTransaction().commit();
		em.close();
		return entity;
	}

	/** Update an existent user or create if not exists
	 * 
	 * 	@return Updated/Created User
	 */
	@Override
	public User update(Integer id, User updatedUser) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		User user = em.find(User.class, id);
		try {
			if(user!=null) {
				user.setName(updatedUser.getName());
				user.setKnowledge(updatedUser.getKnowledge());
				user.setIsEvaluator(updatedUser.getIsEvaluator());
				user.setIsAuthor(updatedUser.getIsAuthor());
				user.setRevisions(updatedUser.getRevisions());
				user.setQualification();
			}
			else {
				em.persist(updatedUser);
			}
		}
		finally {
			System.out.println("Here!!");
			em.flush();
			em.getTransaction().commit();
			em.close();
		}
		return updatedUser;
	}

	@Override
	public User findById(Integer id) {
		EntityManager em=ContextListener.createEntityManager();
		User user = em.find(User.class, id);
		em.close();
		return user;
	}

	@Override
	public List<User> findAll() {
		EntityManager em=ContextListener.createEntityManager();
		TypedQuery<User>query = em.createQuery("Select u from User u", User.class);
		List<User>users = query.getResultList();
		return users;
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		User user = em.find(User.class, id);
		try {
			em.remove(user);
		}
		finally {
			em.getTransaction().commit();
			em.close();
		}
		return user!=null;
	}

	/**
	 * Consulta de todos los datos de un autor/revisor.
	 * @param id
	 * @param em
	 * @return User data or null if user not exists
	 */
	public String getData(Integer id) {
		User user = this.findById(id);
		if(user!=null) {
			return user.toString();
		}
		return null;
	}

	/**
	 * Dado un revisor, retornar todos sus trabajos asignados.
	 * @param id
	 * @param em
	 * @return List with user revisions (Project.class) or null if not exists o isn't evaluator
	 */
	public List<Project> getRevisions(Integer id){
		User user = this.findById(id);
		if(user!=null && user.isEvaluator()) {
			List<Project>projects = new ArrayList<Project>();
			List<Revision>revisions = user.getRevisions();
			for(Revision rev : revisions) {
				projects.add(rev.getProject());
			}
			return projects;
		}
		return null;
	}

	/**
	 * Dado un revisor y un rango de fechas, retornar todas sus revisiones.

	 * @param id
	 * @param from
	 * @param to
	 * @param em
	 * @return Projects with revisions between :from and :to or null if user not exists or isn't evaluator
	 * @throws ParseException
	 */
	public List<Project> getRevisionsBetween(Integer id, String from, String to) throws ParseException{
		User user = this.findById(id);

		if(user!=null && user.isEvaluator()) {
			Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(from);
			Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(to);
			Date formattedDateFrom = new Date(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate());
			Date formattedDateTo = new Date(dateTo.getYear(),dateTo.getMonth(),dateTo.getDate());
			List<Revision>revisions = user.getRevisions();
			List<Project>projects = new ArrayList<Project>();
			for(Revision rev : revisions) {
				if(rev.getRevisionDate().after(formattedDateFrom) && 
						rev.getRevisionDate().before(formattedDateTo) ||
						rev.getRevisionDate().compareTo(formattedDateTo) == 0
						|| rev.getRevisionDate().compareTo(formattedDateFrom) == 1) {
					projects.add(rev.getProject());
				}
			}
			return projects;
		}
		return null;
	}
	
	/**
	 * Dado un autor, retornar todos los trabajos de investigación enviados.
	 * @param id
	 * @param em
	 * @return All research works associated with given user or null if user not exists or isn't author
	 */
	public List<Project> getAllResearchWorks(Integer id){
		EntityManager em=ContextListener.createEntityManager();
		User user = this.findById(id);
		
		if(user!=null && user.isAuthor()) {
			TypedQuery<Project>query = em.createQuery("SELECT p FROM Project p WHERE p.author=?0", Project.class);
			query.setParameter(0, user);
			List<Project>projects = query.getResultList();
			em.close();
			return projects;
		}
		em.close();
		return null;
	}
	
	/**
	 * Seleccionar trabajos de investigación de un autor y revisor en 
	 * una determinada área de investigación utilizando consultas JPQL.
	 * @param id
	 * @param area
	 * @param em
	 * @return
	 */
	public List<Project> getAllResearchWorksOnArea(Integer id, String area){
		EntityManager em=ContextListener.createEntityManager();
		User user = this.findById(id);
		if(user!=null&&user.isEvaluator()) {
			TypedQuery<User>query = em.createQuery("SELECT u FROM User u WHERE u.id_user=?0", User.class);
			query.setParameter(0, user.getId_user());
			User result_user = query.getSingleResult();
			List<Project>filtered_projects = new ArrayList<Project>();
			
			for(Revision rev: result_user.getRevisions()) {
				if(rev.getProject().getTopics().contains(area)) {
					filtered_projects.add(rev.getProject());
				}
			}
			em.close();
			return filtered_projects;
		}
		em.close();
		return null;
	}
}
