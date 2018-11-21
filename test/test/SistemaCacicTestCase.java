/**
 * 
 */
package test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import dao.ProjectDAO;
import dao.UserDAO;
import entities.Project;
import entities.Revision;
import entities.User;
import junit.framework.TestCase;
import sistemaCacic2018.SistemaCacic;

/**
 * @author jose
 *
 */
public class SistemaCacicTestCase extends TestCase {
	public static String[]temas_generales = new String[]{"ProgrammingLanguages","Algorithms","Maths","Algebra","IA","Heuristics","JPA"};
	public static String[]temas_expertos = new String[] {"Java","MySQL","C++","Assembly","Dijkstra","DeepLearning","MachineLearning","WebDevelop","Forensic","ImageProcessing","ObjectOrientedProgramming"};

	private	EntityManagerFactory emf;
	private	EntityManager em;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
//	@BeforeClass
//	protected void before() throws Exception {
//		emf = Persistence.createEntityManagerFactory("ArquiTPEspecial");
//		em = emf.createEntityManager();
//	}

	@Before
	/** Test Crear usuarios (10 usuarios)
	 * 	Test Crear trabajos de investigación (10 trabajos de investigación).
	 */
	protected void setUp() throws Exception{
		emf = Persistence.createEntityManagerFactory("ArquiTPEspecial");
		em = emf.createEntityManager();
		User walter = new User();
		User jesse = new User();
		User gus = new User();
		User mike = new User();
		User saul = new User();
		User hank = new User();
		User tuco = new User();
		User hector = new User();
		User brandon = new User();
		User skinny = new User();

		Project security = new Project();//1
		Project ia = new Project();//1
		Project arduino = new Project();
		Project imageProcessing = new Project();
		Project ux = new Project();//2
		Project methCook = new Project();
		Project deepLearning = new Project();
		Project soundProcessing = new Project();
		Project dataMining = new Project();
		Project cloud = new Project();//1

		walter.switchEvaluator();
		jesse.switchEvaluator();
		jesse.switchAuthor();
		mike.switchAuthor();
		mike.switchEvaluator();
		hank.switchEvaluator();
		hank.switchAuthor();
		brandon.switchAuthor();
		brandon.switchEvaluator();
		tuco.switchAuthor();
		tuco.switchEvaluator();
		saul.switchAuthor();
		saul.switchEvaluator();
		skinny.switchEvaluator();
		
		
		//---------------------------_______Seteo de Propiedades para los proyectos_______---------------------------------//
		
		security.setName("Security");
		//No se añade debido a una restricción, si no existe en la lista de temas entonces no es valido
		security.addTopic("Heuristicas");
		security.addTopic(temas_generales[0]);
		security.addTopic(temas_expertos[0]);
		security.addTopic(temas_expertos[5]);
		security.setCategory("Poster");
		security.setAuthor(hank);
		
		ia.setName("Artificial Intelligence");
		ia.addTopic(temas_generales[4]);
		ia.addTopic(temas_expertos[5]);
		ia.addTopic(temas_expertos[6]);
		ia.setCategory("Article");
		ia.setAuthor(brandon);
		
		arduino.setName("Arduino");
		arduino.addTopic(temas_generales[2]);
		arduino.addTopic(temas_expertos[2]);
		arduino.addTopic(temas_generales[5]);
		arduino.setCategory("Article");
		arduino.setAuthor(jesse);
		
		imageProcessing.setName("Image Processing");
		imageProcessing.addTopic("Music");
		imageProcessing.addTopic(temas_expertos[2]);
		imageProcessing.addTopic(temas_generales[1]);
		imageProcessing.setCategory("Summary");
		imageProcessing.setAuthor(hank);
		
		ux.setName("User experience");
		ux.addTopic(temas_expertos[6]);
		ux.addTopic(temas_expertos[1]);
		ux.addTopic(temas_expertos[7]);
		ux.setCategory("Poster");
		ux.setAuthor(mike);
		
		methCook.setName("Meth Cook");
		methCook.addTopic(temas_generales[1]);
		methCook.addTopic(temas_generales[6]);
		methCook.setCategory("Summary");
		methCook.setAuthor(saul);
		
		deepLearning.setName("Deep Learning");
		deepLearning.addTopic(temas_generales[2]);
		deepLearning.addTopic(temas_expertos[2]);
		deepLearning.addTopic(temas_expertos[5]);
		deepLearning.setCategory("Article");
		deepLearning.setAuthor(tuco);
		
		soundProcessing.setName("Sound Processing");
		soundProcessing.addTopic(temas_generales[2]);
		soundProcessing.addTopic(temas_generales[1]);
		soundProcessing.setCategory("Summary");
		soundProcessing.setAuthor(saul);
		
		dataMining.setName("Data Mining");
		dataMining.addTopic(temas_expertos[1]);
		dataMining.addTopic(temas_expertos[6]);
		dataMining.addTopic(temas_generales[6]);
		dataMining.setCategory("Poster");
		dataMining.setAuthor(jesse);
		
		cloud.setName("Cloud Computing");
		cloud.addTopic(temas_generales[2]);
		cloud.addTopic(temas_generales[4]);
		cloud.setCategory("Article");
		cloud.setAuthor(hank);
		
		//-----------------------______Seteo de Propiedades para los usuarios_______---------------------------------//
		walter.setName("Walter White");
		walter.addKnowledge(temas_generales[4], temas_generales, temas_expertos);
		walter.addKnowledge(temas_expertos[5], temas_generales, temas_expertos);
		walter.addKnowledge(temas_expertos[6], temas_generales, temas_expertos);
		walter.addKnowledge(temas_generales[1], temas_generales, temas_expertos);
		walter.addKnowledge(temas_generales[6], temas_generales, temas_expertos);
		walter.setQualification();
		walter.addRevision(ia, "03/07/2016");
		walter.addRevision(ux, "04/12/2016");
		walter.addRevision(methCook, "03/10/2017");

		jesse.setName("Jesse pinkman");
		jesse.addKnowledge(temas_expertos[1], temas_generales, temas_expertos);
		jesse.addKnowledge(temas_generales[0], temas_generales, temas_expertos);
		jesse.addKnowledge(temas_generales[5], temas_generales, temas_expertos);
		jesse.addKnowledge(temas_generales[3], temas_generales, temas_expertos);
		jesse.setQualification();
		//Se agrega por que ux es Poster tiene conocimiento de 1 de los temas
		jesse.addRevision(ux, "02/01/2019");
		//Se agrega por que posee conocimiento en uno de los temas
		jesse.addRevision(security, "05/02/2020");
		//No se agrega por que no posee conocimiento en todos los temas
		jesse.addRevision(methCook, "06/01/2015");
		//No se agrega por que es el autor del mismo
		jesse.addRevision(arduino, "01/05/2019");

		gus.setName("Gustavo Fring");
		gus.addKnowledge(temas_expertos[0], temas_generales, temas_expertos);
		gus.addKnowledge(temas_expertos[4], temas_generales, temas_expertos);
		gus.setQualification();
		//No se agregan por que no es evaluador
		gus.addRevision(deepLearning, "06/01/2015");
		gus.addRevision(cloud, "01/05/2019");

		mike.setName("Mike Ermanthraut");
		//No se agregara ya que no es un conocimiento valido
		mike.addKnowledge("Dance", temas_generales, temas_expertos);
		mike.addKnowledge(temas_expertos[2], temas_generales, temas_expertos);
		mike.addKnowledge(temas_generales[2], temas_generales, temas_expertos);
		mike.addKnowledge(temas_generales[4], temas_generales, temas_expertos);
		mike.addKnowledge(temas_generales[1], temas_generales, temas_expertos);
		mike.setQualification();
		//Se agrega
		mike.addRevision(cloud, "02/03/2020");
		
		saul.setName("Saul Goodman");
		saul.addKnowledge(temas_expertos[2], temas_generales, temas_expertos);
		saul.addKnowledge(temas_expertos[1], temas_generales, temas_expertos);
		saul.addKnowledge(temas_generales[2], temas_generales, temas_expertos);
		saul.addKnowledge(temas_expertos[4], temas_generales, temas_expertos);
		saul.setQualification();
		//No se agrega - Es autor de la misma
		saul.addRevision(soundProcessing, "04/08/2017");
		saul.addRevision(imageProcessing, "15/07/2020");
		saul.addRevision(dataMining, "08/09/2017");
		
		hank.setName("Hank Schrader");
		hank.addKnowledge(temas_generales[2], temas_generales, temas_expertos);
		hank.addKnowledge(temas_generales[1], temas_generales, temas_expertos);
		hank.addKnowledge(temas_generales[6], temas_generales, temas_expertos);
		hank.setQualification();
		//Es autor
		hank.addRevision(cloud, "08/04/2019");
		//No posee conocimientos expertos - No se agrega
		hank.addRevision(ux, "02/03/2019");
		//No posee conocimientos expertos - No se agrega
		hank.addRevision(ia, "05/04/2018");
		//Se agrega
		hank.addRevision(dataMining, "05/04/2018");
		
		tuco.setName("Tuco Salamanca");
		tuco.addKnowledge(temas_generales[2], temas_generales, temas_expertos);
		tuco.addKnowledge(temas_generales[5], temas_generales, temas_expertos);
		tuco.addKnowledge(temas_expertos[1], temas_generales, temas_expertos);
		tuco.addKnowledge(temas_expertos[4], temas_generales, temas_expertos);
		tuco.setQualification();
		//No se añade ya que es autor de la misma
		tuco.addRevision(deepLearning, "01/03/2018");
		
		hector.setName("Hector Salamanca");
		hector.addKnowledge(temas_expertos[3], temas_generales, temas_expertos);
		hector.addKnowledge(temas_expertos[1], temas_generales, temas_expertos);
		hector.addKnowledge(temas_expertos[2], temas_generales, temas_expertos);
		hector.addKnowledge(temas_expertos[5], temas_generales, temas_expertos);
		hector.addKnowledge(temas_generales[2], temas_generales, temas_expertos);
		hector.setQualification();
		//No se añade ninguno, no es evaluador
		hector.addRevision(dataMining , "03/04/2018");
		hector.addRevision(soundProcessing , "01/10/2019");
		hector.addRevision(imageProcessing , "15/06/2018");
		
		brandon.setName("Brandon Mayhew");
		brandon.addKnowledge(temas_expertos[5], temas_generales, temas_expertos);
		brandon.addKnowledge(temas_expertos[1], temas_generales, temas_expertos);
		brandon.addKnowledge(temas_generales[6], temas_generales, temas_expertos);
		brandon.setQualification();
		brandon.addRevision(arduino, "15/06/2018");
		brandon.addRevision(cloud, "10/03/2017");
		brandon.addRevision(imageProcessing, "06/07/2019");
		brandon.addRevision(ux, "08/12/2019");
		
		skinny.setName("Skinny Pete");
		skinny.addKnowledge(temas_generales[6], temas_generales, temas_expertos);
		skinny.addKnowledge(temas_generales[0], temas_generales, temas_expertos);
		skinny.addKnowledge(temas_generales[2], temas_generales, temas_expertos);
		skinny.addKnowledge(temas_generales[1], temas_generales, temas_expertos);
		skinny.setQualification();
		skinny.addRevision(dataMining, "01/02/2018");
		skinny.addRevision(methCook, "03/08/2020");
		skinny.addRevision(soundProcessing, "04/07/2020");
		//No se agrega por que ya tiene 3 revisiones asignadas
		skinny.addRevision(security, "10/02/2019");
		

		
		UserDAO.getInstance().persist(walter);
		UserDAO.getInstance().persist(jesse);
		UserDAO.getInstance().persist(gus);
		UserDAO.getInstance().persist(saul);
		UserDAO.getInstance().persist(mike);
		UserDAO.getInstance().persist(hank);
		UserDAO.getInstance().persist(tuco);
		UserDAO.getInstance().persist(hector);
		UserDAO.getInstance().persist(skinny);
		UserDAO.getInstance().persist(brandon);
		
		ProjectDAO.getInstance().persist(cloud);
		ProjectDAO.getInstance().persist(dataMining);
		ProjectDAO.getInstance().persist(soundProcessing);
		ProjectDAO.getInstance().persist(imageProcessing);
		ProjectDAO.getInstance().persist(arduino);
		ProjectDAO.getInstance().persist(ia);
		ProjectDAO.getInstance().persist(ux);
		ProjectDAO.getInstance().persist(methCook);
		ProjectDAO.getInstance().persist(deepLearning);
		ProjectDAO.getInstance().persist(security);
	}

	@Test
	/*
	 * Crear usuarios (10 usuarios).
	 */
	public void testCreateTenUsers() {
		List<User>users = UserDAO.getInstance().findAll();
		assertEquals(10,users.size());
	}
	@Test
	/**
	 * Crear trabajos de investigación (10 trabajos de investigación).
	 */
	public void testCreateTenProjects() {
		List<Project>projects = ProjectDAO.getInstance().findAll();
		assertEquals(10,projects.size());
	}

	@Test
	/**
	 * Consulta de todos los datos de un autor/revisor.
	 */
	public void testConsultarDatosDeAutorRevisor() {
		User user = UserDAO.getInstance().findById(1);
		String user_data = user.toString();
		String user_data_from_database = UserDAO.getInstance().getData(1);
		assertEquals(user_data,user_data_from_database);
	}
	
	@Test
	/**
	 * Dado un revisor, retornar todos sus trabajos asignados.
	 */
	public void testGetTodosLosTrabajosAsignadosARevisor() {
		User user = UserDAO.getInstance().findById(25);
		assertNotNull(user);
		List<Revision>revisions= user.getRevisions();
		List<Project>projects = new ArrayList<Project>();
		for(Revision rev:revisions) {
			projects.add(rev.getProject());
		}
		List<Project>user_projects_from_database = UserDAO.getInstance().getRevisions(25);
		assertEquals(projects.size(),user_projects_from_database.size());
		assertNotSame(projects,user_projects_from_database);
		assertEquals(projects,user_projects_from_database);
	}
	
	@Test
	/*
	 * Dado un revisor y un rango de fechas, retornar todas sus revisiones.
	 */
	public void testGetRevisionesRangoFechas() throws ParseException {
		//Este rango de fecha abarca 2 revisiones del usuario 1 (Walter)
		//Por lo tanto chequeo que el size de la lista resultante sea = 2
		List<Project>user_projects = UserDAO.getInstance().getRevisionsBetween(1, "02/10/2016", "05/11/2017");
		assertEquals(2,user_projects.size());
	}
	
	@Test
	/**
	 * Dado un autor, retornar todos los trabajos de investigación enviados.
	 */
	public void testGetTrabajosEnviados() {
		List<Project>projects=UserDAO.getInstance().getAllResearchWorks(10);
		assertEquals(3,projects.size());
	}
	
	@Test
	/*
	 * Consultar trabajos de investigación y sus propiedades.
	 */
	public void testGetInformacionTodosLosTrabajos() {
		List<Project>projects = ProjectDAO.getInstance().findAll();
		List<String>projects_info = new ArrayList<String>();
		List<String>projects_info_from_database = ProjectDAO.getInstance().getAllResearchWorksInfo();
		
		for(Project p : projects) {
			projects_info.add(p.toString());
		}
		
		assertEquals(projects_info,projects_info_from_database);
	}
	
	@Test
	/**
	 * Seleccionar trabajos de investigación de un autor y revisor en una 
	 * determinada área de investigación utilizando consultas JPQL.
	 */
	public void testGetTrabajosEnAreaEspecifica() {
		//El user 25 tiene 2 trabajos en el area temas_generales[1]
		List<Project>projects_from_database = UserDAO.getInstance().getAllResearchWorksOnArea(25, temas_generales[1]);
		assertEquals(2,projects_from_database.size());
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	
	/**
	 * Eliminar todos los datos de la base de datos para realizar otro testeo.
	 */
	public void tearDown() throws Exception {
		SistemaCacic.deleteAllData(em);
	}

	@AfterClass
	protected void after() throws Exception{
		emf.close();
		em.close();
	}
}
