package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController{
  //want this to get the employee's name
  @RequestMapping("/sayEmployeeName")
  public String sayEmployeeName(@RequestParam(value = "name", required = false, defaultValue="NO NAME SUPPLIED")String name, Model model){
    model.addAttribute("name", name);
    return "sayEmployeeName";
  }
}
