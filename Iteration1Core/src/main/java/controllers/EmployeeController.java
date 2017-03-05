package controllers;
//Spring imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import models.Employee;
//arraylist imports
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class EmployeeController{
  //declare global variables
  ArrayList<Employee> employees = new ArrayList<Employee>();

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
    //This is where the pipe of data ends. Anything that needs to be persisted needs to happen here.
    System.out.println("" + employee.getId());
    System.out.println("" + employee.getUsername());
    System.out.println("" + employee.getPassword());
    return "result";
  }
}
