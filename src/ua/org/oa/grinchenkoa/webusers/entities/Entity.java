package ua.org.oa.grinchenkoa.webusers.entities;

/**
 * 
 * Entity's interface. Every entity has to implement this interface.
 * 
 * @author Andrei Grinchenko
 *
 *
 */

public interface Entity {
	
	/**
	 * 
	 * @return Entity's id
	 */
	public int getId();
	
	/**
	 * 
	 * @param id Entity's id
	 */
	public void setId(int id);
	
}
