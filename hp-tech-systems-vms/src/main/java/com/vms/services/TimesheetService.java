package com.vms.services;

import java.util.Comparator;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.ProjectEmployee;
import com.vms.models.Timesheet;
import com.vms.repositories.ProjectEmployeeRepository;
import com.vms.repositories.TimesheetRepository;

@Service
public class TimesheetService {
	
	@Autowired
	private ProjectEmployeeRepository projEmpRepo;
	
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
	
	public HashMap<LocalDate, ArrayList<Timesheet>> timesheetHistory(ProjectEmployee projemp) {
		//Filling a list of ProjectEmployees associated with a specific Employee
		List<ProjectEmployee> projEmpList = projEmpRepo.findByEmployeeOrderByDate_started(projemp.getEmployee());
		
		//Filling a list of timesheets associated with a given employee from the list of ProjectEmployees
		List<Timesheet> timesheets = new ArrayList<Timesheet>();
		for(ProjectEmployee currentTimesheet : projEmpList) {
			for(Timesheet j : currentTimesheet.getTimesheets())
				timesheets.add(j);
		}
		
		//Filling the hash table with timesheet
		HashMap<LocalDate, ArrayList<Timesheet>> timesheetHash = new HashMap<LocalDate, ArrayList<Timesheet>>();
		//Filling the map with the needed keys
		for(Timesheet currentTimesheet : timesheets)
			timesheetHash.putIfAbsent(currentTimesheet.getWeekStarting(), new ArrayList<Timesheet>());
		//Associating the timesheets with their given keys
		//cycling through timesheets list
		for(Timesheet currentTimesheet : timesheets) {	
			//cycling through hashmap
			for(Map.Entry<LocalDate, ArrayList<Timesheet>> entry : timesheetHash.entrySet()) { 
				LocalDate key = entry.getKey(); //assigning current hash key to variable
				ArrayList<Timesheet> value = entry.getValue(); //assigning current value to variable
				//if timesheet.getWeek_starting is equal to current key, add to the list associated with it
				if (currentTimesheet.getWeekStarting() == key) {
					value.add(currentTimesheet);
					timesheetHash.put(key, value);
				}
			}
		}
		
		return timesheetHash;
	}
}

