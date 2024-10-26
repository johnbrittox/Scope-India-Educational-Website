package com.Project.Scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.Project.Scope.model.Course;
@Service
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
