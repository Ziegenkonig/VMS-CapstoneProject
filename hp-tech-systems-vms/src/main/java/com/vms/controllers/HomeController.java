package com.vms.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vms.models.Timesheet;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.TimesheetService;
import com.vms.services.VendorService;

@Controller
public class HomeController {
	//Just throwing this in for testing
	@Autowired
	private VendorService vendorService = new VendorService();
	
	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	
	@Autowired
	private EmployeeService employeeService = new EmployeeService();
	
	@RequestMapping("/")
    public String index() {
		
		//Testing for Cascade
		//Testing for weekly cascade
		Timesheet timesheet1 = new Timesheet(employeeService.findOne(1), LocalDate.now());
		timesheetService.create(timesheet1);
		//Testing for biweekly cascade
		Timesheet timesheet2 = new Timesheet(employeeService.findOne(2), LocalDate.now());
		timesheetService.create(timesheet2);
		
		//Testing if this controller can add an entry into the DB
		Vendor test = new Vendor();
		test.setAddress("address");
		test.setCity("city");
		test.setContactName("contact_name");
		test.setName("name");
		test.setPhone("phone");
		test.setPrimaryEmail("email");
		test.setState("tn");
		vendorService.create(test);
		
        return "index";
        
    }
}
