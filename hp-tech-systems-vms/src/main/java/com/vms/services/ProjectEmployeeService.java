package com.vms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.models.Project;
import com.vms.models.ProjectEmployee;
import com.vms.repositories.ProjectEmployeeRepository;

@Service
public class ProjectEmployeeService {

	//pulling in repository
	@Autowired
	ProjectEmployeeRepository projEmpRepo;
	@Autowired
	EmployeeService empService;
	
	
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
	
	public List<Employee> findEmployeesForTimesheets() {
		return projEmpRepo.findDistinctEmployeeByDateEndedIsNull();
	}
	
	public List<Employee> findEmployeesForCustomTimesheets() {
		return projEmpRepo.findDistinctEmployee();
	}
	
	public List<Employee> findRemainingEmployeesForProject(Project p) {
		List<Employee> emps = projEmpRepo.findDistinctEmployeeOnProject(p.getProjectId());
		List<Employee> active = empService.findActiveEmployees();
		List<Employee> result = new ArrayList<Employee>();
		for(Employee e : active) {
			if(!emps.contains(e)) {
				result.add(e);
			}
		}
		return result;
	}
		
}
