package entities;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Revision {
	@Id
	@GeneratedValue
	private int id_rev;
	@ManyToOne @JoinColumn(name="id_user")
	@JsonIgnore
	private User user;
	@ManyToOne @JoinColumn(name="id_project")
	@JsonIgnore
	private Project project;
	//Additional Column
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date revisionDate;
	@Transient
	private int id_user;
	@Transient
	private int id_project;
	

	public Revision() {}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	
	public int getIdUser() {
		return this.id_user;
	}
	
	public void setIdUser(int id) {
		this.id_user=id;
	}
	
	public int getIdProject() {
		return this.id_project;
	}
	
	public void setIdProject(int id) {
		this.id_project=id;
	}
}
