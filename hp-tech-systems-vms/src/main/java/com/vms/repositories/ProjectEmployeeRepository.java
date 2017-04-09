package com.vms.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vms.models.ProjectEmployee;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Integer> {
	
	public List<ProjectEmployee> findByDateEnded(LocalDate d);
}
