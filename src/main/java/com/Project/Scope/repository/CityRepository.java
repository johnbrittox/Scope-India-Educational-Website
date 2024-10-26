package com.Project.Scope.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.Scope.model.City;
import com.Project.Scope.model.State;

public interface CityRepository extends JpaRepository<City,Integer>{

	public List<City> findByState(State stateid);

}