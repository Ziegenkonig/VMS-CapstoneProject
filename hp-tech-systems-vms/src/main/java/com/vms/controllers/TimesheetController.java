package com.vms.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.models.Project;
import com.vms.models.ProjectTimesheet;
import com.vms.models.Timesheet;
import com.vms.models.TimesheetStatus;
import com.vms.services.EmployeeService;
import com.vms.services.PaystubService;
import com.vms.services.ProjectService;
import com.vms.services.TimesheetService;
import com.vms.services.VendorService;

@Controller
@SessionAttributes(value = {"editTS", "timesheet"})
public class TimesheetController {
	
	@Autowired
	VendorService vendorService = new VendorService();
	@Autowired
	TimesheetService timesheetService = new TimesheetService();
	@Autowired
	EmployeeService employeeService = new EmployeeService();
	@Autowired
	ProjectService projectService = new ProjectService();
	@Autowired
	PaystubService paystubService = new PaystubService();
	
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
	public String viewTimesheet(@PathVariable("id") Integer id, 
								Model model) {
		
		Timesheet timesheet = timesheetService.findById(id);
		
		//model.addAttribute("admin", admin);
		model.addAttribute("timesheet", timesheet);
		
		return "timesheet/viewT";
	}
	
	//EDITING A TIMESHEET
	@GetMapping("/timesheet/edit/{id}")
	public String editTimesheetForm(@PathVariable("id") Integer id,
									Model model) {
		//Setting timesheet to edit
		Timesheet timesheet = timesheetService.findById(id);
		
		//Getting all of the projects associated with this timesheet
		List<ProjectTimesheet> projTimesheets = timesheet.getProjTimesheets();
		List<Project> projects = new ArrayList<Project>();
		for (ProjectTimesheet projTS : projTimesheets)
			projects.add( projectService.findById(projTS.getProjectId()) );
		
		//Checking pay period of associated employee
		int payPeriod = timesheet.getEmployee().getPayPeriod();
		//Getting list of dates in string form for the pay period (now with weekly/biweekly support)
		List<String> datesList = new ArrayList<String>();
		LocalDate maxDate = timesheet.getWeekStarting().plusDays(7); //this is here cause i was getting weird errors
		//Checking for weekly/biweekly pay periods
		if (payPeriod == 1) {
			for (LocalDate date = timesheet.getWeekStarting(); (date.isBefore(maxDate)) ; date = date.plusDays(1))
				datesList.add(date.toString());
		} else if (payPeriod == 2) {
			maxDate = maxDate.plusDays(7);
			for (LocalDate date = timesheet.getWeekStarting(); (date.isBefore(maxDate)) ; date = date.plusDays(1))
				datesList.add(date.toString());
		}
		ArrayHolder dates = new ArrayHolder();
		dates.setList(datesList);
		
		model.addAttribute("id", id);
		model.addAttribute("editTS", timesheet);
		model.addAttribute("statuses", TimesheetStatus.values()); //all timesheet status values for dropdown box
		model.addAttribute("projects", projects);
		model.addAttribute("dates", dates);
		model.addAttribute("projTimesheets", projTimesheets);
		
		return "timesheet/editT";
	}
	
	//handles submitting the timesheet, rendering it uneditable to the employee
	@PostMapping(value = "/timesheet/edit/{id}", params = {"saveTimesheet", "!submit"})
	public String saveTimesheet(@ModelAttribute("editTS") Timesheet timesheet, 
								SessionStatus status) {
		
		timesheetService.edit(timesheet);
		
		status.setComplete();
		
		return "redirect:/timesheet/view/" + timesheet.getTimesheetId() + "/admin=" + false;
	}
	
	//handles saving the current timesheet
	@PostMapping(value = "/timesheet/edit/{id}", params = {"submit", "!saveTimesheet"})
	public String submitTimesheet(@ModelAttribute("editTS") Timesheet timesheet, 
								  SessionStatus status) {
		
		timesheet.setStatus(TimesheetStatus.PENDING);
		timesheetService.create(timesheet);
		
		status.setComplete();
		
		return "redirect:/dashboard";
	}
	
	//admin/katie extras
	@GetMapping(value = "/timesheets/{mode}")
	public String viewTimesheets(@PathVariable String mode,
							   @RequestParam(required = false) TimesheetStatus status,
							   Model model) {
		List<Timesheet> timesheets;
		switch(mode) {
			case "all":
				timesheets = timesheetService.findAll();
				break;
			case "byStatus":
				timesheets = timesheetService.findByStatus(status);
				break;
			default:
				timesheets = null;
		}
		model.addAttribute("timesheets", timesheets);
		return "timesheet/timesheets";
	}
	
	@GetMapping(value = "/timesheet/{id}")
	public String viewTimesheetK(@PathVariable("id") Integer tsId, Model model) {
		Timesheet t = timesheetService.findById(tsId);
		model.addAttribute("timesheet", t);
		/*
		List<String> statuses = new ArrayList<String>();
		for(TimesheetStatus s : TimesheetStatus.values()) {
			statuses.add(s.name());
		}
		*/
		model.addAttribute("statuses", TimesheetStatus.values());
		//String status = null;
		//model.addAttribute("status", status);		
		return "timesheet/viewT";
	}
	
	@PostMapping("/timesheet/updateStatus")
	public String updateStatus(@ModelAttribute("timesheet") Timesheet t,
							   SessionStatus status) {
		//t.setStatus(TimesheetStatus.valueOf(s));
		timesheetService.edit(t);
		status.setComplete();
		return "redirect:/timesheet/" + t.getTimesheetId();
	}
	
	@GetMapping("/timesheet/approve/{id}")
	public String approveTimesheet(@PathVariable Integer id, Model model) {
		
		Timesheet timesheet = timesheetService.findById(id);
		
		//model.addAttribute("admin", admin);
		model.addAttribute("timesheet", timesheet);
		
		return "timesheet/approve";

	}
	
	@PostMapping("/timesheet/approve/{id}")
	public String approveTimesheetPost(@ModelAttribute("timesheet") Timesheet timesheet) {
		
		timesheet.setStatus(TimesheetStatus.VERIFIED);
		timesheetService.create(timesheet);
		
		return "redirect:/paystub/new/" + timesheet.getTimesheetId();
	}
	
}
