package com.Project.Scope.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="contact")
public class Contact {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
@jakarta.validation.constraints.NotNull
	  private int contactid;
	@NotBlank(message="Not Empty")
	private String name;
	@Email(message="Not Empty")
	private String email;
	private String subject;
	private String message;
	public int getContactid() {
		return contactid;
	}
	public void setContactid(int contactid) {
		this.contactid = contactid;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Contact(@NotNull int contactid, @NotBlank(message = "Not Empty") String name,
			@Email(message = "Not Empty") String email, String subject, String message) {
		super();
		this.contactid = contactid;
		this.name = name;
		this.email = email;
		this.subject = subject;
		this.message = message;
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}


}
