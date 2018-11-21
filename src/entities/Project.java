package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import converters.ListToStringConverter;
@Entity
public class Project implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id_project;
	private String name;
	@Convert(converter = ListToStringConverter.class)
	private List<String> topics = new ArrayList<String>();
	private String category;
	@ManyToOne(cascade=CascadeType.ALL)
	private User author;
	private static final long serialVersionUID = 1L;
	@Transient
	List<User>revisors  = new ArrayList<User>();

	public Project() {
		super();
	}   
	public int getId_project() {
		return this.id_project;
	}

	public void setId_project(int id_project) {
		this.id_project = id_project;
	}   
	public List<String> getTopics() {
		return new ArrayList<String>(this.topics);
	}

	public void addTopic(String topic) {
		this.topics.add(topic);
	}  

	public void setTopics(List<String>newTopics) {
		this.topics = newTopics;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		if(category.equals("Article") || category.equals("Summary") || category.equals("Poster")) {
			this.category = category;
		}
		else {
			this.category = "Invalid Category";
		}
	}

	public User getAuthor() {
		return author;
	}

	public boolean setAuthor(User user) {
		if(user.isAuthor()) {
			this.author = user;
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean canBeEvaluated(User user) {
		return user.isEvaluator() && user.getRevisionsSize()<3 && !this.author.equals(user) && user.hasSufficientKnowledges(this) && this.revisors.size()<3;
	}

	public String toString() {
		String topics = String.join(", ", this.topics);
		return "ID: "+this.id_project+
				"| Name: "+this.name+
				"| Topics: "+topics+
				"| Category: "+this.category+
				"| Author: "+this.author.getName();
	}
	
	public void addRevisor(User _u) {
		if(this.revisors.size() < 3) {
			this.revisors.add(_u);
		}
	}
	
	@JsonProperty("revisors")
	public void setRevisors(List<User>_r) {
		this.revisors = _r;
	}
	
	@JsonIgnore
	public List<User> getRevisors() {
		return this.revisors;
	}
}
