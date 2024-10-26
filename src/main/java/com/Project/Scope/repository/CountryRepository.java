package com.Project.Scope.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.Scope.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
	

}

