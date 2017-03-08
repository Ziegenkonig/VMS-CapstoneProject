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
    //go through all of the setters and set the values..

    // pretty sure this is where validation is needed.
          //Commenting out, not sure if we need these.
    // persistEmployee.setId(employee.getId());
    // persistEmployee.setUsername(employee.getUsername());
    // persistEmployee.setPermissionLevel(employee.getPermissionLevel());
    // persistEmployee.setUsername(employee.getUsername());
    // persistEmployee.setPassword(employee.getPassword());
    // persistEmployee.setFirstName(employee.getFirstName());
    // persistEmployee.setLastName(employee.getLastName());
    // persistEmployee.setEmail(employee.getEmail());
    // persistEmployee.setAddress(employee.getAddress());
    // persistEmployee.setCity(employee.getCity());
    // persistEmployee.setState(employee.getState());
    // persistEmployee.setHireDate(employee.getHireDate());

    //store in the service
    employeeService.create(employee);
    //Need to create an array of stuff to be persisted to the database. Apparently it's not great to constantly have to make DB connections as it's very slow. Better to do it all at once.

    //this returns an html page (result.html) that is populated with data
    return "employeeResult";
  }
}
