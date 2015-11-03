
package ua.org.oa.grinchenkoa.webusers.entities;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * Class describes objects "user" stored in database, implements Entity interface,
 * is bean-component in JSP
 * 
 * @author Andrei Grinchenko
 * 
 * 
 */


@Entity
@Table(name="user")
public class User implements Comparable<User>, ua.org.oa.grinchenkoa.webusers.entities.Entity{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;
	
	@Column(name="birthDate")
	private Date birthDate;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_role", referencedColumnName = "id", nullable = false)
	private Role role;
	
	@OneToOne(mappedBy = "user")
	private Adress adress;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserMusicType> userMusicTypes; 
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	
	
	public Set<UserMusicType> getUserMusicTypes() {
		return userMusicTypes;
	}

	public void setUserMusicTypes(Set<UserMusicType> userMusicTypes) {
		this.userMusicTypes = userMusicTypes;
	}

	
	@Override
	public String toString() {
		return "id: " + id + "\nlogin: " + login + "\npassword: " + password + "\nbirthDate: " + birthDate
			+ "\ne-mail: " + email;
	}

	@Override
	public int compareTo(User user) {
		if (this.id > user.id)
			return 1;
		else if (this.id == user.id)
			return 0;
			else 
				return -1;
	}
}
