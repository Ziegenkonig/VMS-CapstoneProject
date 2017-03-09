package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TimesheetController{

  @GetMapping("/timesheet")
  public String timesheetForm(Model model){
    model.addAttribute("timesheet", new Timesheet());
    return "timesheet";
  }


  @PostMapping("/timesheet")
  public String timesheetSubmit(@ModelAttribute Timesheet timesheet){
    //store in the service
    timesheetService.create(timesheet);
    return "timesheetResult";
  }
}
