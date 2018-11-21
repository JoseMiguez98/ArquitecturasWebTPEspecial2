package sistemaCacic2018;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dao.ProjectDAO;
import dao.UserDAO;
import entities.Project;
import entities.User;
/**
 * 
 * @author <a href="https://github.com/JoseMiguez98/">Jose Miguez</a>
 *
 */
public class SistemaCacic {
	/*
	 * F.T.D: Hacer una entity Topic que diga si son expertos o especificos asi quedan
	 * persistidos en la BBDD;
	 */
	public static String[]temas_generales = new String[]{"ProgrammingLanguages","Algorithms","Maths","Algebra","IA","Heuristics","JPA"};
	public static String[]temas_expertos = new String[] {"Java","MySQL","C++","Assembly","Dijkstra","DeepLearning","MachineLearning","WebDevelop","Forensic","ImageProcessing","ObjectOrientedProgramming"};

	public static void main(String[] args) throws ParseException {
		User jose = new User();
		User belu = new User();
		User enzo = new User();
		Project arqui = new Project();
		Project ux = new Project();
		Project math = new Project();
		Project cacic = new Project();
		Project security = new Project();
		Project ia = new Project();
		Project arduino = new Project();
		//-------SwitchingAuthors-----------//
		jose.switchAuthor();
		belu.switchAuthor();
		enzo.switchAuthor();
		//-------ProjectsProperties-------//
		arqui.setCategory("Arquitecturas");
		arqui.addTopic("JPA");
		arqui.addTopic("Java");
		arqui.addTopic("Hibernate");
		arqui.addTopic("Jersey");
		arqui.setName("Arquitecturas Web");
		arqui.setAuthor(belu);

		//Tiene que quedar "Invalid Category"
		ux.setCategory("Poster");
		ux.addTopic("UI");
		ux.addTopic("UX");
		ux.addTopic("Heuristicas");
		ux.addTopic("Nielsen");
		ux.addTopic("Usabilidad");
		ux.addTopic(temas_expertos[2]);
		ux.setName("Interfaces de usuario");
		ux.setAuthor(jose);

		math.setName("Math");
		math.setCategory("Summary");
		math.addTopic("POW");
		math.addTopic("Numbers");
		math.addTopic("Sum");
		math.setAuthor(enzo);

		cacic.setName("CACIC2018");
		cacic.setCategory("Article");
		cacic.addTopic(temas_generales[4]);
		cacic.addTopic(temas_generales[1]);
		cacic.addTopic(temas_generales[6]);
		cacic.addTopic(temas_expertos[2]);
		cacic.setAuthor(enzo);

		arduino.setName("Arduino");
		arduino.setCategory("Article");
		arduino.addTopic(temas_generales[6]);
		arduino.addTopic(temas_generales[1]);
		arduino.addTopic(temas_generales[4]);
		arduino.addTopic(temas_expertos[1]);
		arduino.setAuthor(belu);

		security.setName("Security");
		security.setCategory("Poster");
		security.addTopic(temas_generales[4]);
		security.setAuthor(jose);

		ia.setName("Artifical intelligence");
		ia.setCategory("Poster");
		ia.addTopic(temas_expertos[6]);
		ia.addTopic("C++");
		ia.setAuthor(belu);
		//---------------------------------//

		//--------UsersProperties---------//
		jose.switchEvaluator();
		jose.setName("Jose");
		jose.addKnowledge("ProgrammingLanguages",temas_generales,temas_expertos);
		jose.addKnowledge("Java",temas_generales,temas_expertos);
		jose.addKnowledge("MySQL",temas_generales,temas_expertos);
		jose.addKnowledge("JPA",temas_generales,temas_expertos);
		jose.addKnowledge(temas_expertos[6],temas_generales,temas_expertos);
		jose.addKnowledge("WebDevelopment",temas_generales,temas_expertos);
		jose.setQualification();
		jose.addRevision(arqui,"04/05/2018");
		jose.addRevision(cacic,"04/05/2018");

		belu.switchEvaluator();
		belu.setName("Belu");
		belu.addKnowledge("Dance",temas_generales,temas_expertos);
		belu.addKnowledge(temas_generales[5],temas_generales,temas_expertos);
		belu.addKnowledge(temas_generales[2],temas_generales,temas_expertos);
		belu.addKnowledge("Psicology",temas_generales,temas_expertos);
		belu.addKnowledge("Music",temas_generales,temas_expertos);
		belu.setQualification();
		belu.addRevision(ux,"04/05/2018");
		belu.addRevision(math,"04/05/2018");

		enzo.switchEvaluator();
		enzo.setName("Enzo");
		enzo.addKnowledge(temas_generales[4], temas_generales, temas_expertos);
		enzo.addKnowledge(temas_generales[1], temas_generales, temas_expertos);
		enzo.addKnowledge(temas_generales[6], temas_generales, temas_expertos);
		enzo.addKnowledge(temas_expertos[2], temas_generales, temas_expertos);
		enzo.addKnowledge(temas_expertos[6], temas_generales, temas_expertos);
		enzo.setQualification();
		//No deberia agregarse ya que no posee conocimiento en todos los temas que abarca el proyecto
		enzo.addRevision(arduino,"02/07/2020");
		//Deberia agregarse ya que ux es poster y enzo posee conocimiento solo en 1 tema del proyecto
		enzo.addRevision(ux,"03/11/2016");
		//No deberia agregarse ya que es el autor de la misma
		enzo.addRevision(math,"03/06/2020");
		//Deberia agregarse ya que posee conocimiento en todos los temas
		enzo.addRevision(cacic,"04/05/2018");
		enzo.addRevision(security,"02/06/2018");
		//No se agrega por que enzo ya tiene 3 revisiones
		enzo.addRevision(ia, "02/06/2020");

		UserDAO.getInstance().persist(enzo);
		UserDAO.getInstance().persist(belu);
		UserDAO.getInstance().persist(jose);

		ProjectDAO.getInstance().persist(math);
		ProjectDAO.getInstance().persist(ia);
		ProjectDAO.getInstance().persist(ux);
		ProjectDAO.getInstance().persist(arduino);

		List<Project>researchWorks = UserDAO.getInstance().getAllResearchWorksOnArea(1,"C++");
		for(Project p : researchWorks) {
			System.out.println(p.getName());
		}

		//		deleteAllData(em);
		//		em.persist(ux);
		//		em.persist(arduino);
		//		em.persist(jose);
		//		em.persist(belu);
		//		em.persist(enzo);

		//-------------------------------------//
	}

	/**
	 * Eliminar todos los datos de la base de datos para realizar otro testeo.
	 * @param em
	 */
	public static void deleteAllData(EntityManager em) {
		em.getTransaction().begin();
		Query q1 = em.createQuery("DELETE FROM User");
		Query q2 = em.createQuery("DELETE FROM Project");
		Query q3 = em.createQuery("DELETE FROM Revision");

		q1.executeUpdate();
		q2.executeUpdate();
		q3.executeUpdate();
		em.getTransaction().commit();
	}
}
