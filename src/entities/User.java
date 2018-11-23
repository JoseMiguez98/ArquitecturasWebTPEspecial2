package entities;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.String;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import converters.ListToStringConverter;
import sistemaCacic2018.SistemaCacic;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable { 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id_user;
	private String name;
	@Column(name="is_evaluator")
	private boolean isEvaluator = false;
	@Column(name="is_author")
	private boolean isAuthor = false;
	@Convert(converter = ListToStringConverter.class)
	private List<String> knowledge = new ArrayList<String>();
	@JsonIgnore
	@OneToMany(mappedBy="user",fetch = FetchType.EAGER)
	private List<Revision> revisions = new ArrayList<Revision>();
	private String qualification;
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}
	
	public int getId_user() {
		return this.id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}  
	
	public Boolean isEvaluator() {
		return this.isEvaluator;
	}
	
	public Boolean getIsEvaluator() {
		return this.isEvaluator;
	}
	
	public void setIsEvaluator(Boolean _e) {
		this.isEvaluator = _e;
	}
	
	public void switchEvaluator() {
		this.isEvaluator = !isEvaluator;
	} 
	
	public Boolean isAuthor() {
		return this.isAuthor;
	}
	
	public Boolean getIsAuthor() {
		return this.isAuthor();
	}
	
	public void setIsAuthor(Boolean _a) {
		this.isAuthor = _a;
	}
	
	public void switchAuthor() {
		
		
		this.isAuthor = !isAuthor;
	} 
	
	public List<String> getKnowledge() {
		return new ArrayList<String>(this.knowledge);
	}
	

	public boolean addKnowledge(String knowledge, String[]generalsK, String[]expertsK) {
		if(Arrays.asList(generalsK).contains(knowledge) || Arrays.asList(expertsK).contains(knowledge)) {
			this.knowledge.add(knowledge);	
			return true;
		}
		return false;
	} 
	
	public void setKnowledge(List<String>newKnowledges) {
		this.knowledge = newKnowledges;
	}
	
	@JsonIgnore
	public String getQualification() {
		return this.qualification;
	}


	@JsonProperty("qualification")
	public String setQualification() {
		for(String k : SistemaCacic.temas_expertos) {
			if(this.knowledge.contains(k)) {
				this.qualification = "Expert";
				return qualification;
			}
		}
		this.qualification = "General";
		return qualification;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Revision> getRevisions() {
		return this.revisions;
	}
	
	public Revision addRevision(Project project, String str_date) throws ParseException {
		if(project.canBeEvaluated(this)) {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str_date);
			Date formattedDate = new Date(date.getYear(),date.getMonth(),date.getDate());
			Revision rev = new Revision();
			rev.setUser(this);
			rev.setProject(project);
			rev.setRevisionDate(formattedDate);
			this.revisions.add(rev);
			project.addRevisor(this);
			return rev;
		}
		return null;
	}
	
	@JsonIgnore
	public int getRevisionsSize() {
		return this.revisions.size();
	}
	
	@JsonIgnore
	public void setRevisions(List<Revision>_r) {
		this.revisions = _r;
	}
	
	public boolean hasSufficientKnowledges(Project project) {
		List<String>topics = project.getTopics();
		if(project.getCategory().equals("Article") || project.getCategory().equals("Summary")) {
			for(String topic : topics) {
				if(!this.knowledge.contains(topic)) {
					return false;
				}
			}
			return true;
		}
		else if(project.getCategory().equals("Poster")) {
			for(String topic : topics) {
				if(this.knowledge.contains(topic)) {
					return true;
				}
			}
			return false;
		}
		//Si posee una categoria invalida puede ser evaluado por cualquiera
		return true;
	}
	
	public String toString() {
		String knowledges = String.join(", ", this.knowledge);
		
		return "ID: "+this.id_user+
				" |Name: "+this.name+
				" |Knowledges: "+knowledges+
				" |Qualification: "+this.qualification;
	}
}
