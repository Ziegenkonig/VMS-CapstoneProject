package com.vms.services;

import java.time.LocalDate;
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
	
	public List<ProjectTimesheet> timsheetsForInvoice(Project p, LocalDate weekStarting) {
		return projTSRepo.findByProjectIdAndWeekStarting(p.getProjectId(), weekStarting);
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
