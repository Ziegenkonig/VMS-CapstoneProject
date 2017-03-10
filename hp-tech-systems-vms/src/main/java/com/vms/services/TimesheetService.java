package com.vms.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.models.Timesheet;
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
	
	//Returns timesheets from most recent period for specified employee
	public List<Timesheet> currentTimesheets(Employee employee) {
		List<LocalDate> uniquePeriods = timesheetRepo.uniqueWeekStarting(employee.getEmpId());
		
		return timesheetRepo.timesheetsMatchToPeriod(uniquePeriods.get(0), employee.getEmpId());
	}
	
	//returns a list of all timesheets organized by weekStarted
	public HashMap<LocalDate, List<Timesheet>> timesheetHistory(Employee employee) {
		List<LocalDate> uniquePeriods = timesheetRepo.uniqueWeekStarting(employee.getEmpId());
		
		HashMap<LocalDate, List<Timesheet>> timesheetMap = new HashMap<LocalDate, List<Timesheet>>();
		for(LocalDate date : uniquePeriods) 
			timesheetMap.put(date, timesheetRepo.timesheetsMatchToPeriod(date, employee.getEmpId()));

		return timesheetMap;
	}
}

