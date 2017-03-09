package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.models.ProjectEmployee;
import com.vms.repositories.EmployeeRepository;
import com.vms.repositories.ProjectEmployeeRepository;

/*
 * Method to check whether an employee with the given username and password exists (login/register)
 * Method to check whether an employee is currently active (login/register)
 * 
 */

@Service
public class EmployeeService {
	
	//Pulling in the repository
	@Autowired
	EmployeeRepository employeeRepo;

	//General methods
	
	//creates a new object in table
	void create(Employee employee) {
		employeeRepo.save(employee);
	}
	
	//update a new object in table
	void update(Employee employee) {
		employeeRepo.save(employee);
	}
	
	//Search methods
	
	//returns one object with specified id
	Employee findOne(Integer id) {
		return employeeRepo.findOne(id);
	}
	
	//returns all objects
	List<Employee> findAll() {
		return employeeRepo.findAll();
	}
	
	//login() checks to see if an employee exists in the database with the given username
	//if an employee is found, the password is checked against the given password
	//returns true if employee exists and password is correct
	public boolean login(String username, String password) {
		
		Employee employee;
		employee = employeeRepo.findByUsername(username);
		
		if (employee != null) {
			if (employee.getPassword() == password)
				return true;
		}
			
		return false;
	}
}
