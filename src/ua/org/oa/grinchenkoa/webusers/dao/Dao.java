package ua.org.oa.grinchenkoa.webusers.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import ua.org.oa.grinchenkoa.webusers.entities.Entity;
import ua.org.oa.grinchenkoa.webusers.managers.HibernateManager;

/**
 * Class is used for abstraction and encapsulation CRUD operations for entity T.
 * DAO operates connection with data source using Hibernate for receiving and recording data.
 * 
 * @author Andrei Grinchenko
 *
 * @param <T> Entity
 */
public class Dao<T extends Entity> {

		/**
		 * Creating new record in DB
		 * 
		 * @param obj Creating Entity's object
		 * @throws SQLException
		 * @throws IOException
		 */
		public void create(T obj) throws SQLException, IOException {
			Session session = null;
			try {
				session = HibernateManager.getSessionFactory().openSession();
				session.beginTransaction();
				session.save(obj);
				session.getTransaction().commit();
			}
			finally {
				if ((session != null) && (session.isOpen()))
					session.close();
			}
		}

		/**
		 * 
		 * Getting Entity's object from the DB with id
		 * 
		 * @param id Entity's id
		 * @param cl Class of getting object
		 * @return Entity
		 * @throws SQLException
		 * @throws IOException
		 */
		public T read(int id, Class<T> cl) throws SQLException, IOException {
			Session session = null;
			T obj = null;
			try {
				session = HibernateManager.getSessionFactory().openSession();
				obj = (T)session.get(cl, id);
				
			} 
			finally {
				if ((session != null) && (session.isOpen()))
						session.close();
			}
			return obj;
		}
		
		/**
		 * Getting list of all Entity's objects
		 * 
		 * @param cl Class of getting objects list's
		 * @return list of all Entity's objects
		 * @throws SQLException
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		public List<T> readAll(Class<T> cl) throws SQLException, IOException {
			List<T> list;
			Session session = null;
			try {
				session = HibernateManager.getSessionFactory().openSession();
				list = session.createCriteria(cl).list();
			} 
			finally {
				if ((session != null) && (session.isOpen()))
						session.close();
			} 
			return list;
		}
		
		/**
		 * Updating Entity's object in the DB
		 * 
		 * @param obj Updating Entity's object
		 * @throws SQLException
		 * @throws IOException
		 */
		public void update(T obj) throws SQLException, IOException {
			Session session = null;
			try {
				session = HibernateManager.getSessionFactory().openSession();
				session.beginTransaction();
				session.update(obj);
				session.getTransaction().commit();
			} 
			finally {
				if ((session != null) && (session.isOpen()))
					session.close();
			}
		}
		
		/**
		 * 
		 * Deleting Entity's object from the DB
		 * 
		 * @param obj Deleting Entity's object
		 * @throws SQLException
		 * @throws IOException
		 */
		public void delete(T obj) throws SQLException, IOException {
			Session session = null;
			try {
				session = HibernateManager.getSessionFactory().openSession();
				session.beginTransaction();
				session.delete(obj);
				session.getTransaction().commit();
			} 
			finally {
				if ((session != null) && (session.isOpen()))
					session.close();
			}
		}
}
