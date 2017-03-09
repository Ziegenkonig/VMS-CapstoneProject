package com.vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.vms.models.TimesheetRow;
import com.vms.repositories.TimesheetRowRepository;

@Service
@Primary
public class TimesheetRowServiceImpl implements TimesheetRowService{
	
	//Pulling the repository in
	@Autowired
	public TimesheetRowRepository timesheetRowRepo;
	
	@Override
	public void create(TimesheetRow timesheetrow) {
		timesheetRowRepo.save(timesheetrow);
	}
	
	
}
