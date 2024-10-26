package com.Project.Scope.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.Scope.model.Course;
import com.Project.Scope.repository.CourseRepository;
@Service
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
	public List<Course> courselist(){
		return courseRepository.findAll();
		
	}
public Course getCourseByCourseId(Integer Courseid) {
	return courseRepository.findById(Courseid).orElse(null);
	
}
}
