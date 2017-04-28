package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.repositories.EmployeeRepository;
import com.vms.utilities.Encoder;

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
	public void create(Employee employee) {
		employeeRepo.save(employee);
	}
	
	//update a new object in table
	public void update(Employee employee) {
		employeeRepo.save(employee);
	}
	
	//Search methods
	
	//returns an employee that matches the given first and last name
	public Employee findByName(String firstname, String lastname) {
		return employeeRepo.findByName(firstname, lastname);
	}
	
	//returns an employee that has a matching registration url
	public Employee findByRegistrationUrl(String rawUrl) {
		Encoder encoder = new Encoder();
		
		List<Employee> employees = employeeRepo.findByRegistrationUrlIsNotNull();
		
		for (Employee e : employees) {
			if ( encoder.decode(rawUrl, e.getRegistrationUrl()) )
					return e;
		}
		
		return null;
	}
	
	public List<Employee> findUnregisteredEmployees() {
		return employeeRepo.findByRegistrationUrlIsNotNull();
	}
	
	//returns a list of all employee names
	public List<String[]> findAllNames() {
		return employeeRepo.findAllNames();
	}
	
	//returns one object with specified id
	public Employee findOne(Integer id) {
		return employeeRepo.findOne(id);
	}
	
	public Employee findByUsername(String name) {
		return employeeRepo.findByUsername(name);
	}
	
	//returns all employees sorted by last name in ascending order
	public List<Employee> findAllSorted() {
		return employeeRepo.findAllByOrderByLastnameAsc();
	}
	
	//returns all objects
	public List<Employee> findAll() {
		return employeeRepo.findAll();
	}
	
	//deactivates the specified employee, rendering account locked
	public void deactivate(Employee employee) {
		Employee emp = employeeRepo.findOne(employee.getEmpId());
		emp.setActive(false);
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
