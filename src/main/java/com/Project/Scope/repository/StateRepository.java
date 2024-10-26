package com.Project.Scope.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.Scope.model.Country;
import com.Project.Scope.model.State;

public interface StateRepository extends JpaRepository<State,Integer>{
	public List<State> findByCountry(Country countryid);
	

}

