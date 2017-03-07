package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TimesheetController(){

  @GetMapping("/timesheet")
  public String timesheetForm(Model model){

  }


  @PostMapping("/timesheet")
  public String timesheetSubmit(){

  }
}
