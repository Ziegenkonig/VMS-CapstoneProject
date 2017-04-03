package com.vms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Timesheet;
import com.vms.services.TimesheetService;
import com.vms.utilities.mail.Mail;

@Controller
public class TimesheetController {

	@Autowired
	TimesheetService tSService = new TimesheetService();
	
	@GetMapping(value = "/timesheets")
	public String viewTimesheets(Model model) {
		List<Timesheet> timesheets = tSService.findAll();
		model.addAttribute("timesheets", timesheets);
		return "timesheet/timesheets";
	}
	
	@GetMapping(value = "/timesheet/{id}")
	public String viewTimesheet(@PathVariable("id") Integer tsId, Model model) {
		Timesheet t = tSService.findById(tsId);
		model.addAttribute("timesheet", t);
		return "timesheet/timesheets";
	}
	
//  @GetMapping("/timesheets")
//  public String timesheetForm(Model model) {
//	  return null;
//  }

  @PostMapping("/submitTimesheet")
  public String timesheetSubmit() {
	  Mail.sendEmail("jbhtcher@memphis.edu");
	  return null;
  }
  
}
