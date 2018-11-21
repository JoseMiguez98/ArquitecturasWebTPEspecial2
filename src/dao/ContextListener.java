package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener{
	public static EntityManagerFactory emf;
	public static int testboolean = 2;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		emf = Persistence.createEntityManagerFactory("ArquiTPEspecial2");
	}
	
	public static EntityManager createEntityManager() {
		if(emf==null) {
			throw new IllegalStateException("Context not initialized yet!");
		}
		return emf.createEntityManager();
	}
	
}