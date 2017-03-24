package com.vms.services;

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
	
	//return all ProjectTimesheets for a period - used to make an invoice
	public List<ProjectTimesheet> timesheetsForInvoice(Project p, LocalDate weekStarting) {
		return projTSRepo.findByProjectIdAndWeekStarting(p.getProjectId(), weekStarting);
	}
	
	//return all unique weekStarting values sorted in descending order
	public List<LocalDate> uniqueDates() {
		//The database retrieves time info in the form of a timestamp, so we have to account for that here
		List<Timestamp> dates = projTSRepo.findUniqueWeekStarting();
		List<LocalDate> localDates = new ArrayList<LocalDate>();
		//converting all timestamp values into localdate values
		for (Timestamp date : dates)
			localDates.add(date.toLocalDateTime().toLocalDate());
		
		return localDates;
	}
	
	//basic repo methods
	public List<ProjectTimesheet> findAll() {
		return projTSRepo.findAll();
	}
	
	public ProjectTimesheet findById(Integer id) {
		return projTSRepo.findOne(id);
	}

	public ProjectTimesheet create(ProjectTimesheet pt) {
		return projTSRepo.save(pt);
	}
	
	public ProjectTimesheet edit(ProjectTimesheet pt) {
		return projTSRepo.save(pt);
	}
}
