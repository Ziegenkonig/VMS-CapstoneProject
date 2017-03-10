package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vms.models.Paystub;

public interface PaystubRepository extends JpaRepository<Paystub, Integer>{
	
	public List<Paystub> findByEmpIdOrderByPeriodStartDesc(int emp_id);
	/*
	public List<Timesheet> getCurrentTimesheets(Employee e) {
		List timesheets = new ArrayList<Timesheet>();
		for(ProjectEmployee pe: e.getProjemps())
			pe.getTimesheets();
		e.getProjemps();
		return 
	}
	*/
}
