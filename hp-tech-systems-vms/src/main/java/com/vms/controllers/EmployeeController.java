package com.vms.controllers;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
//Spring imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Employee;
import com.vms.services.EmployeeService;

@Controller
public class EmployeeController{
  //declare global variables
	
  //Hooking up the EmployeeService to the EmployeeController
  @Autowired
  EmployeeService employeeService = new EmployeeService();
	
  @GetMapping("/register")
  public String employeeForm(Model model){
    model.addAttribute("employee", new Employee());
    return "employee/new";
  }

/**
  @GetMapping("/employees")
  //I want to make an array of all of the current employees, then iterate through that array and print everything to the view.
  public String getAllEmployees(){
    for(int i = 0; i < employees.size(); i ++){
      System.out.println(employees.get(i));
    }
    return "employees";
  }
**/

  @PostMapping("/register")
  public String employeeSubmit(@ModelAttribute Employee employee){
    //THIS IS WHERE THE PIPE OF DATA ENDS.
    //Anything that needs to be persisted needs to happen IN THIS METHOD.

    //create a new employee
    Employee newEmployee = new Employee();
    newEmployee.setUsername(employee.getUsername());
    newEmployee.setPermissionLevel(employee.getPermissionLevel());
    newEmployee.setUsername(employee.getUsername());
    newEmployee.setPassword(employee.getPassword());
    newEmployee.setFirstname(employee.getFirstname());
    newEmployee.setLastname(employee.getLastname());
    newEmployee.setEmail(employee.getEmail());
    newEmployee.setAddress(employee.getAddress());
    newEmployee.setCity(employee.getCity());
    newEmployee.setState(employee.getState());
    newEmployee.setHireDate(LocalDate.now());
    
    //throw this new employee into the database
    employeeService.create(newEmployee);
    
    //this returns an html page (result.html) that is populated with data
    return "result";
  }
}
