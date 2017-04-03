package com.vms.controllers;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Employee;
import com.vms.models.Invoice;
import com.vms.models.Timesheet;
import com.vms.models.TimesheetStatus;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.TimesheetService;

@Controller
public class TimesheetController {

	@Autowired
	TimesheetService timesheetService = new TimesheetService();
	@Autowired
	EmployeeService employeeService = new EmployeeService();
	
	//VIEWING ALL TIMESHEETS
	@GetMapping("/timesheets")
	public String allTimesheets(Model model) {

		//Getting all timesheets
		List<Timesheet> timesheets = timesheetService.findAll();
		TimesheetStatus editCheck = TimesheetStatus.NOT_SUBMITTED;
		
		//Adding all invoices to the model
		model.addAttribute("timesheets", timesheets);
		model.addAttribute("editCheck", editCheck);
		
		//specifying which html file rendering
		return "timesheet/timesheets";
	}
	
	//CREATING NEW TIMESHEET
	@GetMapping("/timesheet/new")
	public String newTimesheetForm(Model model) {
		//Grabbing the local date, and setting it to the nearest future sunday (start of pay period)
		LocalDate now = LocalDate.now();
		while( !(now.getDayOfWeek().equals(DayOfWeek.SUNDAY)) ) {
			now = now.plusDays(1);
		}

		//Making a list of local dates with 30 available future pay periods (for use in dropdown box)
		List<LocalDate> periods = new ArrayList<LocalDate>();
		periods.add(now);
		for(int i = 0; i <= 30; i++)
			periods.add(periods.get(i).plusWeeks(1));

		//Also need to convert that list of dates into a list of strings
		List<String> dates = new ArrayList<String>();
		for(LocalDate date : periods)
			dates.add(date.toString());
		
		//We also need empty objects to pass to the post method so that the html has something to modify
		StringHolder selectedDate = new StringHolder();
		Employee selectedEmployee = new Employee();
		
		//Last thing we need to create a new timesheet is an employee to assign it to
		List<Employee> employees = employeeService.findAllSorted();
		
		//Now we just add everything to the model
		model.addAttribute("dates", dates);
		model.addAttribute("employees", employees);
		model.addAttribute("selectedDate", selectedDate);
		model.addAttribute("selectedEmployee", selectedEmployee);
		
		//returning html file to render
		return "timesheet/newT";
	}
	
	@PostMapping("/timesheet/new")
	public String newTimesheetSubmit(@ModelAttribute("selectedDate") StringHolder selectedDate,
									 @ModelAttribute("selectedEmployee") Employee selectedEmployee) {
		//Convert selectedDate from String to LocalDate
		LocalDate finalDate = LocalDate.parse(selectedDate.getString());
		
		//Now we have all we need to create a new timesheet, and add it to the database
		Timesheet newTimesheet = new Timesheet(selectedEmployee, finalDate);
		timesheetService.create(newTimesheet);
		
		//render the view page for our new timesheet
		return "redirect:" + "http://localhost:8080/timesheet/view/" + newTimesheet.getTimesheetId();
	}
	
	//VIEWING ONE TIMESHEET
	@GetMapping("/timesheet/view/{id}")
	public String viewTimesheet(Model model) {
		
		return "timesheet/viewT";
	}
	
}
