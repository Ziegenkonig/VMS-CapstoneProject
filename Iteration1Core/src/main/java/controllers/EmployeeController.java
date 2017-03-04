package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import models.Employee;

@Controller
public class EmployeeController{

  @GetMapping("/employee")
  public String employeeForm(Model model){
    model.addAttribute("employee", new Employee());
    return "employee";
  }

/**
  @GetMapping("/allEmployees")
  //I want to make an array of all of the current employees, then iterate through that array and print everything to the view.
  public String getAllEmployees(Model[] models){
    model.addAttribute("employees", new Employee());
    return "employees";
  }
**/

  @PostMapping("/employee")
  public String employeeSubmit(@ModelAttribute Employee employee){
    System.out.println("" + employee.getId());
    return "result";
  }
}
