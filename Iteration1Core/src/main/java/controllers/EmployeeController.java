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
  private ArrayList<Employee> employees = new ArrayList<Employee>();

  @GetMapping("/employee")
  public String employeeForm(Model model){
    model.addAttribute("employee", new Employee());
    return "employee";
  }

  /**
  @GetMapping("/allEmployees")
  //I want to make an array of all of the current employees, then iterate through that array and print everything to the view.
  public String getAllEmployees(){
    for(int i = 0; i < employees.size(); i ++){
      System.out.println(employees.get(i));
    }
    return "index";
  }
**/

  @PostMapping("/employee")
  public String employeeSubmit(@ModelAttribute Employee employee){
    //THIS IS WHERE THE PIPE OF DATA ENDS.
    //Anything that needs to be persisted needs to happen IN THIS METHOD.

    //create a new employee
    Employee test = new Employee();
    test.setId(employee.getId());
    test.setUsername(employee.getUsername());
    //then add it to the array
    employees.add(test);
    //then get the employee from the array
    System.out.println(employees.get(employee.getId()));

    //this returns an html page (result.html) that is populated with data
    return "result";
  }
}
