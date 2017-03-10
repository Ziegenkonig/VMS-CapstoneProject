package com.vms.controllers;
import java.util.ArrayList;

//Spring imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Employee;

@Controller
public class EmployeeController{
  //declare global variables

  //Want a global list of all of the employees that we are dealing with.. pretty much a cache. 
  private ArrayList<Employee> employees = new ArrayList<Employee>();

  @GetMapping("/employee")
  public String employeeForm(Model model){
    model.addAttribute("employee", new Employee());
    return "employee";
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

  @PostMapping("/employee")
  public String employeeSubmit(@ModelAttribute Employee employee){
    //THIS IS WHERE THE PIPE OF DATA ENDS.
    //Anything that needs to be persisted needs to happen IN THIS METHOD.

    //create a new employee
    Employee persistEmployee = new Employee();
//    persistEmployee.setId(employee.getId());
//    persistEmployee.setUsername(employee.getUsername());
//    persistEmployee.setPermissionLevel(employee.getPermissionLevel());
//    persistEmployee.setUsername(employee.getUsername());
//    persistEmployee.setPassword(employee.getPassword());
//    persistEmployee.setFirstName(employee.getFirstName());
//    persistEmployee.setLastName(employee.getLastName());
//    persistEmployee.setEmail(employee.getEmail());
//    persistEmployee.setAddress(employee.getAddress());
//    persistEmployee.setCity(employee.getCity());
//    persistEmployee.setState(employee.getState());
//    persistEmployee.setHireDate(employee.getHireDate());

    //then add it to the array
    employees.add(persistEmployee);
    System.out.println(persistEmployee);

    //this returns an html page (result.html) that is populated with data
    return "result";
  }
}
