package com.vms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TimesheetController {

  @GetMapping("/timesheet")
  public String timesheetForm(Model model) {
	  return null;
  }


  @PostMapping("/timesheet")
  public String timesheetSubmit() {
	  return null;
  }
  
}
