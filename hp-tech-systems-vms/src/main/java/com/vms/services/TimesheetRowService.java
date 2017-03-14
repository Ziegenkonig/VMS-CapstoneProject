package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.TimesheetRow;
import com.vms.repositories.TimesheetRowRepository;

@Service
public class TimesheetRowService {

	//pulling in repository
	@Autowired
	TimesheetRowRepository timeSheetRowRepo;
	
	//General methods
	
	//creates a new object in table
	void create(TimesheetRow timesheetRow) {
		timeSheetRowRepo.save(timesheetRow);
	}
	
	//update a new object in table
	void update(TimesheetRow timesheetRow) {
		timeSheetRowRepo.save(timesheetRow);
	}
	
	//Search methods
	
	//returns one object with specified id
	TimesheetRow findOne(Integer id) {
		return timeSheetRowRepo.findOne(id);
	}
	
	//returns all objects
	List<TimesheetRow> findAll() {
		return timeSheetRowRepo.findAll();
	}
	
}
