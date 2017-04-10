package com.vms.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vms.models.Employee;
import com.vms.models.Timesheet;
import com.vms.models.TimesheetStatus;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer>{
	
	@Query( value = "SELECT DISTINCT(weekStarting) AS weekStarting FROM timesheets WHERE employeeId = :empId ORDER BY weekStarting DESC",
			nativeQuery = true)
	public List<LocalDate> uniqueWeekStarting(@Param("empId") Integer empId);
	
	/*
	@Query( value = "SELECT * FROM timesheets WHERE weekStarting = :period AND employeeId = :empId",
			nativeQuery = true)
	public List<Timesheet> timesheetsMatchToPeriod(@Param(":period") LocalDate period, @Param("empId") Integer empId); 
	*/
	
	//timesheetHistory
	@Query
	public List<Timesheet> findByEmployeeOrderByWeekStartingDesc(Employee e);
	
	//for the employee dashboard
	@Query
	public List<Timesheet> findByEmployeeAndStatusInOrderByWeekStartingDesc(Employee e, List<TimesheetStatus> statuses);
	
	//openTimesheets
	@Query
	public List<Timesheet> findByEmployeeAndStatusOrderByWeekStartingDesc(Employee e, TimesheetStatus status);
	
	//pendingTimesheets
	@Query
	public List<Timesheet> findByStatusOrderByWeekStartingDesc(TimesheetStatus status);

	@Query
	public List<Timesheet> findByOrderByWeekStartingDesc();
	
	//List<Timesheet> findByEmpIdOrderByWeekStartingDesc(Integer empId);
	
}
