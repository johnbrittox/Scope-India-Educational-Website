package com.Project.Scope.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.Scope.model.Country;
import com.Project.Scope.model.State;
import com.Project.Scope.repository.StateRepository;
@Service
public class StateService {
	@Autowired
	private StateRepository stateRepository;
	public List<State>getstate(){
		return stateRepository.findAll();
	
	}
	public List<State>getStateBy(Country countryid){
		return stateRepository.findByCountry(countryid);
		
	}

}

