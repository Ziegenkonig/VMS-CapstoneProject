package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vms.models.Employee;
import com.vms.models.ProjectEmployee;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Integer> {
	
	List<ProjectEmployee> findByEmployeeOrderByDate_started(Employee emp);
	
}
