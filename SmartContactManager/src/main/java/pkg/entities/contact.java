package pkg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class contact {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cid;
	private String contactname;
	private String nickname;
	private String job;
	private String email;
	private String phonenumber;
	@Column(length=1000)
	private String discription;
	@ManyToOne
	@JoinColumn
	@JsonIgnore//using this the data of user wont be serialized during the search process
 	private usar usr;
	
	public contact() {
		super();
 	}

	public contact(int cid, String contactname, String nickname, String job, String email, String phonenumber,
			String discription, usar usr) {
		super();
		this.cid = cid;
		this.contactname = contactname;
		this.nickname = nickname;
		this.job = job;
		this.email = email;
		this.phonenumber = phonenumber;
		this.discription = discription;
		this.usr = usr;
	}

	public contact(String contactname, String nickname, String job, String email, String phonenumber,
			String discription, usar usr) {
		super();
		this.contactname = contactname;
		this.nickname = nickname;
		this.job = job;
		this.email = email;
		this.phonenumber = phonenumber;
		this.discription = discription;
		this.usr = usr;
	}

	public contact(String contactname, String nickname, String job, String email, String phonenumber,
			String discription) {
		super();
		this.contactname = contactname;
		this.nickname = nickname;
		this.job = job;
		this.email = email;
		this.phonenumber = phonenumber;
		this.discription = discription;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public usar getUsr() {
		return usr;
	}

	public void setUsr(usar usr) {
		this.usr = usr;
	}
 
	
	

	
	
}
