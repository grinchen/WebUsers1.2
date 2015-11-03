/**
 * Class describes compound table record's("user" and "musicType") object
 */

package ua.org.oa.grinchenkoa.webusers.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Class describes objects "usermusictype" stored in database, compound tables "user" and "musictype",
 * many-to-many relationship, implements Entity interface
 * 
 * @author Andrei Grinchenko
 * 
 * 
 */

@Entity
@Table(name="usermusictype")
public class UserMusicType implements ua.org.oa.grinchenkoa.webusers.entities.Entity{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_user", referencedColumnName="id", nullable=false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_musicType", referencedColumnName="id", nullable=false)
	private MusicType musicType;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public MusicType getMusicType() {
		return musicType;
	}
	public void setMusicType(MusicType musicType) {
		this.musicType = musicType;
	}
	
}
