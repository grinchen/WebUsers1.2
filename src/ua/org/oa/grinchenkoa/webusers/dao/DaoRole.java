package ua.org.oa.grinchenkoa.webusers.dao;

import java.io.IOException;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ua.org.oa.grinchenkoa.webusers.entities.Role;
import ua.org.oa.grinchenkoa.webusers.managers.HibernateManager;

/**
 * Class DaoRole extends Dao and add special operations for Role Entity
 * 
 * @see Dao
 * 
 * @author Andrei Grinchenko
 *
 * @param <T> Role
 */
public class DaoRole<T extends Role> extends Dao<T> {
	
	/**
	 * Getting Entity Role with Role name
	 * 
	 * @param roleName Role name
	 * @return Entity Role
	 * @throws SQLException
	 * @throws IOException
	 */
	public Role read(String roleName) throws SQLException, IOException  {
		Role role = null;
		Session session = null;
		try {
			session = HibernateManager.getSessionFactory().openSession();
			role = (Role)session.createCriteria(Role.class).add(Restrictions.like("roleName", roleName)).uniqueResult();
		} catch (Exception e) {
		}
		finally {
			if ((session != null) && (session.isOpen()))
					session.close();
		}
		return role;
	}
		
	
	/**
	 * Getting Role's id with Role name
	 * 
	 * @param roleName Role name
	 * @return Entity Role's id
	 * @throws SQLException
	 * @throws IOException
	 */
	public int readId(String roleName) throws SQLException, IOException {
		Role role = null;
		int id = 0;
		Session session = null;
		try {
			session = HibernateManager.getSessionFactory().openSession();
			role = (Role)session.createCriteria(Role.class).add(Restrictions.like("roleName", roleName)).uniqueResult();
			id = role.getId();
		} finally {
			if ((session != null) && (session.isOpen()))
					session.close();
		}
		return id;
	}
}

