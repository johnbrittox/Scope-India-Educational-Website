package com.Project.Scope.repository;

import org.springframework.data.repository.CrudRepository;

import com.Project.Scope.model.Registration;

public interface RegistraionRepository extends CrudRepository<Registration, Integer> {
	public Registration findByVerificationcode(String code);
	public Registration findByEmail(String email);
	public Registration findByEmailAndPassword(String email, String password);
	

}
