package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entities.Revision;

public class RevisionDAO implements DAO<Revision, Integer>{

	public static RevisionDAO daoRev;

	public static RevisionDAO getInstance() {
		if(daoRev==null) {
			daoRev = new RevisionDAO();
		}
		return daoRev;
	}
	
	@Override
	public Revision persist(Revision entity) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);	
		em.getTransaction().commit();
		em.close();
		return entity;
	}

	@Override
	public Revision update(Integer id, Revision newEntityValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Revision findById(Integer id) {
		EntityManager em=ContextListener.createEntityManager();
		Revision rev = em.find(Revision.class, id);
		em.close();
		return rev;
	}

	@Override
	public List<Revision> findAll() {
		EntityManager em=ContextListener.createEntityManager();
		TypedQuery<Revision>query = em.createQuery("Select r from Revision r", Revision.class);
		List<Revision>revs= query.getResultList();
		return revs;
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager em=ContextListener.createEntityManager();
		em.getTransaction().begin();
		Revision rev = em.find(Revision.class, id);
		try {
			em.remove(rev);
		}
		finally {
			em.getTransaction().commit();
			em.close();
		}
		return rev!=null;
	}

}
