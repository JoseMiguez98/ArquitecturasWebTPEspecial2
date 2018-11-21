package entities;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Revision {
	@Id
	@GeneratedValue
	private int id_rev;
	@ManyToOne(cascade=CascadeType.PERSIST) @JoinColumn(name="id_user")
	private User user;
	@ManyToOne(cascade=CascadeType.PERSIST) @JoinColumn(name="id_project")
	private Project project;
	//Additional Column
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date revisionDate;
	

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
}
