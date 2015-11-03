package ua.org.oa.grinchenkoa.webusers.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 
 * Class describes objects "role" stored in database, implements Entity interface,
 * is bean-component in JSP
 * 
 * @author Andrei Grinchenko
 * 
 */

@Entity
@Table(name="role")
public class Role implements ua.org.oa.grinchenkoa.webusers.entities.Entity{

	@Id
	@Column(name="id", unique = true, nullable = false)
	private int id;
	
	@Column(name="roleName")
	private String roleName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<User> users; 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String role) {
		this.roleName = role;
	}
	
	
	
	public Set<User> getUsers() {
		return users;
	}
	
	
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}
}
