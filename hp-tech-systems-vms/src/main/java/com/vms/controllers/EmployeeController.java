package com.vms.controllers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//Spring imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.models.States;
import com.vms.models.Timesheet;
import com.vms.services.EmployeeService;
import com.vms.services.PaystubService;
import com.vms.services.TimesheetService;

@Controller
@SessionAttributes(value = {"employee"})
public class EmployeeController{

  //Hooking up the EmployeeService to the EmployeeController
  @Autowired
  EmployeeService employeeService = new EmployeeService();
  @Autowired
  TimesheetService timesheetService = new TimesheetService();
  @Autowired
  PaystubService paystubService = new PaystubService();
  
  @GetMapping("/register")
  public String employeeForm(Model model){
	  
	model.addAttribute("states", States.values());
    model.addAttribute("employee", new Employee());
    return "employee/newE";
  }

  @PostMapping("/register")
  public String employeeSubmit(@ModelAttribute@Valid Employee employee, 
		  					   BindingResult bindingResult){
    //Checks for input validation and returns to registration page if validation fails
	if (bindingResult.hasErrors())
	  return "employee/newE";
		
	//setting hiredate to present time
    employee.setHireDate(LocalDate.now());
    
    //throw this new employee into the database
    employeeService.create(employee);
    
    //takes us straight to user profile page (not implemented yet)
    return "employee/dashboard";
  }
  
  //employeeService.findOne(1) here just populates the editProfile page with the correct information
  @GetMapping("/editUserProfile/{id}")
  public String employeeEditForm(@PathVariable("id") Integer id, 
		  						 Model model) {
	  
	  model.addAttribute("employee", employeeService.findOne(id));
	  return "employee/editE";
  }
  
 
  @PostMapping("/editUserProfile/{id}")
  public String employeeEdit(@ModelAttribute("employee")@Valid Employee employee,
		  					 BindingResult bindingResult,
		  					 SessionStatus sessionStatus){
	//Checks for validation errors and renders this page again if any exist
	if (bindingResult.hasErrors())
		return "employee/editE";
	  
    //We need to set uneditable values of the employee we are editing manually
//	Employee editEmployee = employeeService.findByUsername(employee.getUsername());
//    employee.setEmpId(editEmployee.getEmpId());
//    employee.setHireDate(editEmployee.getHireDate());
//    employee.setPermissionLevel(editEmployee.getPermissionLevel());
//    employee.setProjemps(editEmployee.getProjemps());
//    employee.setActive(editEmployee.isActive());
//    employee.setTimesheets(editEmployee.getTimesheets());
//    employee.setPayPeriod(employee.getPayPeriod());
	
    //update this employee in the database
    employeeService.update(employee);
    
    sessionStatus.setComplete();
    
    return "redirect:/dashboard";
  }
  
  @GetMapping("/dashboard")
  public String dashboard(Model model) {
	  
	  Employee e = employeeService.findOne(1);
	  
	  List<Paystub> issuedPaystubs = paystubService.findIssued(e.getEmpId());
	  List<Timesheet> openTimesheets = timesheetService.openTimesheets(e);
	  
	  model.addAttribute("e", e);
	  model.addAttribute("openTimesheets", openTimesheets);
	  model.addAttribute("issuedPaystubs", issuedPaystubs);
	  
	  return "employee/dashboard";
  }
}
