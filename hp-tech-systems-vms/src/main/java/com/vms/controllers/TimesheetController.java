package com.vms.controllers;

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

import com.vms.models.Timesheet;
import com.vms.models.TimesheetStatus;
import com.vms.services.TimesheetService;

@Controller
@SessionAttributes("timesheet")
public class TimesheetController {

	@Autowired
	TimesheetService tSService = new TimesheetService();
	
	@GetMapping(value = "/timesheets/{mode}")
	public String viewTimesheets(@PathVariable String mode,
							   @RequestParam(required = false) TimesheetStatus status,
							   Model model) {
		List<Timesheet> timesheets;
		switch(mode) {
			case "all":
				timesheets = tSService.findAll();
				break;
			case "byStatus":
				timesheets = tSService.fi();
				break;
			default:
				timesheets = null;
		}
		model.addAttribute("timesheets", timesheets);
		return "timesheet/timesheets";
	}
	
	@GetMapping(value = "/timesheet/{id}")
	public String viewTimesheet(@PathVariable("id") Integer tsId, Model model) {
		Timesheet t = tSService.findById(tsId);
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
		tSService.edit(t);
		status.setComplete();
		return "redirect:/timesheet/" + t.getTimesheetId();
	}
	/*
	//return associated timesheet to employee for edit
	@GetMapping("/timesheet/return")
	public String returnTimesheet(@RequestParam Integer tsId) {
		Timesheet t = tSService.findById(tsId);
		t.setStatus(TimesheetStatus.NOT_SUBMITTED);
		tSService.edit(t);
		return "timesheet/timesheets";
	}
	
	/*
  @GetMapping("/timesheet")
  public String timesheetForm(Model model) {
	  return null;
  }


  @PostMapping("/timesheet")
  public String timesheetSubmit() {
	  return null;
  }
  */
}
