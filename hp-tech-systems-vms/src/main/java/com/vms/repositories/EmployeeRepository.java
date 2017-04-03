package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vms.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	@Query
	public List<Employee> findAllByOrderByLastnameAsc();
	
	Employee findByUsername(String username);
	
	//Grabs all first and last names from the database
	@Query (
			value = "SELECT firstname, lastname FROM employees ORDER BY lastname ASC",
			nativeQuery = true
		)
	public List<String[]> findAllNames();
	
	//Finds the employee associated with the given first and last name
	@Query (
			value = "SELECT * FROM employees WHERE firstname = :firstname AND lastname = :lastname",
			nativeQuery = true
		)
	public Employee findByName(@Param("firstname") String firstname, @Param("lastname") String lastname);
}
