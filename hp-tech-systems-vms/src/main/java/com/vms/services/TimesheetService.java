package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vms.models.Timesheet;
import com.vms.repositories.TimesheetRepository;

@Controller
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
}