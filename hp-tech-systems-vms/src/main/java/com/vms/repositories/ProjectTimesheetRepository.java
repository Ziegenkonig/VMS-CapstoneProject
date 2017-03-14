package com.vms.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vms.models.ProjectTimesheet;

public interface ProjectTimesheetRepository extends JpaRepository<ProjectTimesheet, Integer>{

	@Query
	public List<ProjectTimesheet> findByProjectIdAndWeekStarting(Integer pId, LocalDate weekStarting);
	
}
