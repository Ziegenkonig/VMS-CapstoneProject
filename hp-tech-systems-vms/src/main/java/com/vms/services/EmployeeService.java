package com.vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.repositories.EmployeeRepository;

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
