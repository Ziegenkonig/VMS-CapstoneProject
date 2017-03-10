package com.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vms.models.Paystub;

public interface PaystubRepository extends JpaRepository<Paystub, Integer>{
	
	
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
