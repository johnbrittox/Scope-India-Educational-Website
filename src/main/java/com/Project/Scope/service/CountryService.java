package com.Project.Scope.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.Scope.model.Country;
import com.Project.Scope.repository.CountryRepository;


@Service
public class CountryService {
	@Autowired
	private CountryRepository countryRepository;
	public List<Country>countrylist(){
		return countryRepository.findAll();
		
	}

}
