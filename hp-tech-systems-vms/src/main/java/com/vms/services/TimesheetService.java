package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.models.Timesheet;
import com.vms.models.TimesheetStatus;
import com.vms.repositories.TimesheetRepository;

@Service
public class TimesheetService {
	
	@Autowired
	private TimesheetRepository timesheetRepo;
	
	public List<Timesheet> findAll() {
		return timesheetRepo.findAll();
	}
	
	public Timesheet findById(Integer id) {
		return timesheetRepo.findOne(id);
	}

	public Timesheet create(Timesheet timesheet) {
		return timesheetRepo.save(timesheet);
	}
	
	public Timesheet edit(Timesheet timesheet) {
		return timesheetRepo.save(timesheet);
	}
	
	//Returns open timesheets for specified employee - current timesheet would be thisList.get(0)
	public List<Timesheet> openTimesheets(Employee e) {
		return timesheetRepo.findByEmployeeAndStatusOrderByWeekStartingDesc(e, com.vms.models.TimesheetStatus.NOT_SUBMITTED);
	}
	
	//returns a list of all timesheets for an employee organized by weekStarted
	public List<Timesheet> timesheetHistory(Employee e) {
		return timesheetRepo.findByEmployeeOrderByWeekStartingDesc(e);
	}
	
	//find pending timesheets for admin review
	public List<Timesheet> pendingTimesheets() {
		return timesheetRepo.findByStatusOrderByWeekStartingDesc(com.vms.models.TimesheetStatus.PENDING);
	}
	
	public List<Timesheet> approvedTimesheets() {
		return timesheetRepo.findByStatusOrderByWeekStartingDesc(com.vms.models.TimesheetStatus.VERIFIED);
	}
	
	public Timesheet returnTimesheet(Timesheet t) {
		t.setStatus(TimesheetStatus.NOT_SUBMITTED);
		return timesheetRepo.save(t);
	}
}

