package pkg.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
 public class usar {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	//below  are some additional annotations for validation got form  maven dependency lik 'hibernate-validator' &'javax.validation'
    @NotBlank(message="user name must not be null")
    @Size(min=3,max=12 ,message="need_atlest_3_to_12_characters")
 	private String name;
	 @Column(unique=true)
	 @Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid email type")  	
private String email;
	private String password;
	private String role;
	//@AssertTrue(message="please agree terms and conditions")
	private boolean enabled;
	@Column(length=1000)
	private String about;  
    @OneToMany(cascade=CascadeType.ALL,mappedBy="usr",fetch=FetchType.EAGER)//oprphanRemoval means [,orphanRemoval= true]:if the child entity is disconnected(by mkiing this null) the it gets deleted
	private List<contact> contacts=new ArrayList<contact>();
	
	
	public usar() {
		super();
 	}


	public usar(int id, String name, String email, String password, String role, boolean enabled, String about,
			List<contact> contacts) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.about = about;
		this.contacts = contacts;
	}


	public usar(String name, String email, String password, String role, boolean enabled, String about,
			List<contact> contacts) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.about = about;
		this.contacts = contacts;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public List<contact> getContacts() {
		return contacts;
	}


	public void setContacts(List<contact> contacts) {
		this.contacts = contacts;
	}


	@Override
	public String toString() {
		return "usar [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enabled=" + enabled + ", about=" + about + ", contacts=" + contacts + "]";
	}

 
	 
	

}
