package com.Project.Scope.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
@Table(name="students")
public class Registration {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
@jakarta.validation.constraints.NotNull
	  private int regid;
	@NotBlank(message="Not Empty")
	private String firstName;
	@NotBlank(message="Not Empty")
	private String lastName;
	private String gender;
	@Past
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	@Email(message="Not Empty")
	private String email;
	private long phoneNumber;
	@ManyToOne
	@JoinColumn(name="country")
	private Country country;
	@ManyToOne
	@JoinColumn(name="state")
	private State state;
	@ManyToOne
	@JoinColumn(name="city")
	private City city;
    private String hobbies;
    private String verificationcode;
    private boolean enabled;
    private String fileupload;
	public String OTP;
	private String password;
	private String course;



	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getVerificationcode() {
		return verificationcode;
	}
	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getId() {
		return regid;
	}
	public void setId(int id) {
		this.regid = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public @Past @Past LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(@Past @Past LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phone) {
		this.phoneNumber = phone;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	
	
	public String getFileupload() {
		return fileupload;
	}
	public void setFileupload(String fileupload) {
		this.fileupload = fileupload;
	}
	public int getRegid() {
		return regid;
	}
	public void setRegid(int regid) {
		this.regid = regid;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public Registration(String firstName, String lastName, String gender, @Past @Past LocalDate dateOfBirth, String email,
			long phoneNumber, Country country, State state,String hobbies, String fileupload,City city) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.state = state;
		this.hobbies=hobbies;
		this.fileupload = fileupload;
		this.city=city;
		
	}

	public Registration() {
		// TODO Auto-generated constructor stub
	}
	public void setVerified(boolean b) {
		// TODO Auto-generated method stub
		
	}
	




}
