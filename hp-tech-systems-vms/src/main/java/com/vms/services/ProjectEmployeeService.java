package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vms.models.ProjectEmployee;
import com.vms.repositories.ProjectEmployeeRepository;

public class ProjectEmployeeService {

	//pulling in repository
	@Autowired
	ProjectEmployeeRepository projEmpRepo;
	
	//General methods
	
	//creates a new object in table
	void create(ProjectEmployee projEmp) {
		projEmpRepo.save(projEmp);
	}
	
	//update a new object in table
	void update(ProjectEmployee projEmp) {
		projEmpRepo.save(projEmp);
	}
	
	//Search methods
	
	//returns one object with specified id
	ProjectEmployee findOne(Integer id) {
		return projEmpRepo.findOne(id);
	}
	
	//returns all objects
	List<ProjectEmployee> findAll() {
		return projEmpRepo.findAll();
	}
		
}