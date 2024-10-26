package com.Project.Scope.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Project.Scope.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
	public Contact findByEmail(String email);

}

