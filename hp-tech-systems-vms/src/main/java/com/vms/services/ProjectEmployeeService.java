package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.ProjectEmployee;
import com.vms.repositories.ProjectEmployeeRepository;

@Service
public class ProjectEmployeeService {

	//pulling in repository
	@Autowired
	ProjectEmployeeRepository projEmpRepo;
	
	
	
	//General methods
	
	//creates a new object in table
	public void create(ProjectEmployee projEmp) {
		projEmpRepo.save(projEmp);
	}
	
	//update a new object in table
	public void update(ProjectEmployee projEmp) {
		projEmpRepo.save(projEmp);
	}
	
	//Search methods
	
	//returns one object with specified id
	public ProjectEmployee findOne(Integer id) {
		return projEmpRepo.findOne(id);
	}
	
	//returns all objects
	public List<ProjectEmployee> findAll() {
		return projEmpRepo.findAll();
	}
	
	public List<ProjectEmployee> findOpenProjects() {
		return projEmpRepo.findByDateEnded(null);
	}
		
}
