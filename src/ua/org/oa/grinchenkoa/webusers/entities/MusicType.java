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
 * Class describes objects "musictype" stored in database, implements Entity interface,
 * is bean-component in JSP
 * 
 * @author Andrei Grinchenko
 *
 */

@Entity
@Table(name="musictype")
public class MusicType implements ua.org.oa.grinchenkoa.webusers.entities.Entity {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="typeName")
	private String musicType;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "musicType")
	private Set<UserMusicType> userMusicTypes; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMusicType() {
		return musicType;
	}
	public void setMusicType(String musicType) {
		this.musicType = musicType;
	}
	public Set<UserMusicType> getUserMusicTypes() {
		return userMusicTypes;
	}
	public void setUserMusicTypes(Set<UserMusicType> userMusicTypes) {
		this.userMusicTypes = userMusicTypes;
	}
	
	
}
