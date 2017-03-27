package com.vms.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vms.models.ProjectTimesheet;

public interface ProjectTimesheetRepository extends JpaRepository<ProjectTimesheet, Integer>{

	@Query
	public List<ProjectTimesheet> findByProjectIdAndWeekStarting(Integer pId, LocalDate weekStarting);
	
	@Query (
		value = "SELECT DISTINCT week_starting FROM project_timesheets ORDER BY week_starting DESC",
		nativeQuery = true
	)
	

	public List<Date> findUniqueWeekStarting();
	//public List<Timestamp> findUniqueWeekStarting();
}