package com.Project.Scope.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.Scope.model.City;
import com.Project.Scope.model.State;
import com.Project.Scope.repository.CityRepository;

@Service
public class CityService {
		@Autowired
		private CityRepository cityRepository;
	
		public List<City> getCityBy(State stateid){
			return cityRepository.findByState(stateid);
		}

}