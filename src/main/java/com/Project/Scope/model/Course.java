package com.Project.Scope.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="course")
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@jakarta.validation.constraints.NotNull

	private int courseid;
	private String coursename;
	private String duration;
	private int fee;
	
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public Course(int courseid, String coursename, String duration, int fee) {
		super();
		this.courseid = courseid;
		this.coursename = coursename;
		this.duration = duration;
		this.fee = fee;
	}
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

}
