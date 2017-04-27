package com.vms.controllers;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.models.States;
import com.vms.models.Timesheet;
import com.vms.services.EmployeeService;
import com.vms.services.PaystubService;
import com.vms.services.TimesheetService;
import com.vms.utilities.Encoder;
import com.vms.utilities.MailService;

@Controller
@SessionAttributes({"employee", "states"})
public class EmployeeController{

	//Hooking up the EmployeeService to the EmployeeController
	@Autowired
	EmployeeService employeeService = new EmployeeService();
	@Autowired
	TimesheetService timesheetService = new TimesheetService();
	@Autowired
	PaystubService paystubService = new PaystubService();
	@Autowired
	MailService mailService;
  
  @GetMapping("/register")
  public String employeeForm(Model model){
	  
	model.addAttribute("states", States.values());
    model.addAttribute("employee", new Employee());
    return "employee/newE";
  }

  @PostMapping("/register")
  public String employeeSubmit(@ModelAttribute@Valid Employee employee, 
		  					   BindingResult bindingResult, 
		  					   Model model){
    //Checks for input validation and returns to registration page if validation fails
	if (bindingResult.hasErrors())
	  return "employee/newE";
		
	//setting hiredate to present time
    employee.setHireDate(LocalDate.now());
    
    //throw this new employee into the database
    employeeService.create(employee);
    
    model.addAttribute("states", States.values());
    
    //takes us straight to user profile page (not implemented yet)
    return "employee/dashboard";
  }
  
  //employeeService.findOne(1) here just populates the editProfile page with the correct information
  @GetMapping("/editUserProfile/{id}")
  public String employeeEditForm(@PathVariable("id") Integer id, 
		  						 Model model) {
	  
	  model.addAttribute("states", States.values());
	  model.addAttribute("employee", employeeService.findOne(id));
	  return "employee/editE";
  }
  
 
  @PostMapping("/editUserProfile/{id}")
  public String employeeEdit(@ModelAttribute("employee")@Valid Employee employee,
		  					 BindingResult bindingResult,
		  					 SessionStatus sessionStatus, 
		  					 Model model){
	//Checks for validation errors and renders this page again if any exist
	if (bindingResult.hasErrors())
		return "employee/editE";
	  
	model.addAttribute("states", States.values());
	
    //update this employee in the database
    employeeService.update(employee);
    
    sessionStatus.setComplete();
    
    return "redirect:/dashboard";
  }
  
  @GetMapping("/dashboard")
  public String dashboard(Model model) {
	  
	  Employee e = employeeService.findOne(1);
	  
	  List<Paystub> issuedPaystubs = paystubService.findIssued(e.getEmpId());
	  List<Timesheet> openTimesheets = timesheetService.dashboardTimesheets(e);
	  
	  model.addAttribute("e", e);
	  model.addAttribute("openTimesheets", openTimesheets);
	  model.addAttribute("issuedPaystubs", issuedPaystubs);
	  
	  return "employee/dashboard";
  }
  
  	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminDashboard(Model model) {
  		Employee admin = employeeService.findOne(6);
  		//Employee owner =  employeeService.findOne(11);
  		//model.addAttribute("emp", owner);
  		model.addAttribute("emp", admin);
		return "admin";
	}
  	
  	@GetMapping("/employees")
  	public String viewEmployees(Model model) {
  		model.addAttribute("employees", employeeService.findAll());
  		return "employee/employees";
  	}
  	
  	//The admin must send an email to an employee before the employee is allowed to register
  	@GetMapping("/inviteEmployee")
  	public String sendRegistrationEmail(Model model) {
  		
  		Employee employee = new Employee();
  		
  		model.addAttribute("employee", employee);
  		
  		return "employee/registrationEmail";
  	}
  	
  	@PostMapping("/inviteEmployee")
  	public String sentRegistrationEmail(@ModelAttribute Employee employee) {

  		//Taking care of basic declaration for the new employee
  		employee.setPayPeriod(1);
  		employee.setConfirmEmail(false);
  		employee.setActive(true);
  		employee.setHireDate(LocalDate.now());
  		employee.setPermissionLevel(1);
  		
  		//Taking care of encrypting and setting the confirmation url
  		Encoder encoder = new Encoder();
  		String registrationUrl = encoder.secureRegistration();
  		//setting hashed registration url
  		employee.setRegistrationUrl( encoder.encode(registrationUrl) );
  		//updating new employee
    	employeeService.create(employee);
  		
  		//sending email to new employee
  		//MailService mail = new MailService();
  		mailService.sendEmail(employee, "employeeRegistration");
  		
  		return "admin";
  	}
}
