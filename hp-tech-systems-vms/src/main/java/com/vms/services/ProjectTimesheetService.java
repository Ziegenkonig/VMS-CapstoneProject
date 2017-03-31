package com.vms.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Project;
import com.vms.models.ProjectTimesheet;
import com.vms.repositories.ProjectTimesheetRepository;

@Service
public class ProjectTimesheetService {

	@Autowired
	private ProjectTimesheetRepository projTSRepo;
	
	//create/edit methods
	
	public ProjectTimesheet create(ProjectTimesheet pt) {
		return projTSRepo.save(pt);
	}
	
	public ProjectTimesheet edit(ProjectTimesheet pt) {
		return projTSRepo.save(pt);
	}
	
	//Search methods
	
	//returns all objects
	public List<ProjectTimesheet> findAll() {
		return projTSRepo.findAll();
	}
	
	//returns a ProjectTimesheet that matches the given id
	public ProjectTimesheet findById(Integer id) {
		return projTSRepo.findOne(id);
	}
	
	//return all ProjectTimesheets for a period - used to make an invoice
	public List<ProjectTimesheet> timesheetsForInvoice(Project p, LocalDate weekStarting) {
		return projTSRepo.findByProjectIdAndWeekStarting(p.getProjectId(), weekStarting);
	}
	
	//return all unique weekStarting values sorted in descending order
	//Greatly decreases runtimes
	public List<LocalDate> uniqueDates() {
		List<Date> dates = projTSRepo.findUniqueWeekStarting();
		List<LocalDate> localDates = new ArrayList<LocalDate>();
		
		for (Date date : dates)
			localDates.add(date.toLocalDate());
		
		return localDates;
	}
	
}
