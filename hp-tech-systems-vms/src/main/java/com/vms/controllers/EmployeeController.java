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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.models.States;
import com.vms.models.Timesheet;
import com.vms.services.EmployeeService;
import com.vms.services.PaystubService;
import com.vms.services.TimesheetService;
import com.vms.utilities.HashSlingingSlasher;
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
	MailService mailService = new MailService();
  
  @GetMapping("/register/{registrationUrl}")
  public String employeeForm(@PathVariable("registrationUrl") String registrationUrl, 
		  					 Model model) {
	
	Employee newEmp = employeeService.findByRegistrationUrl(registrationUrl);
	if ( newEmp ==  null )  
		return "redirect:/";
	
	model.addAttribute("url", registrationUrl);
	model.addAttribute("states", States.values());
    model.addAttribute("employee", newEmp);
    
    return "employee/newE";
  }

  @PostMapping("/register/{registrationUrl}")
  public String employeeSubmit(@ModelAttribute("employee")@Valid Employee employee,
			   				   BindingResult bindingResult,
			   				   SessionStatus sessionStatus,
		  					   Model model) {
    HashSlingingSlasher encoder = new HashSlingingSlasher();
    
	//Checks for input validation and returns to registration page if validation fails
	if ( bindingResult.hasErrors() )
		return "employee/newE";
	
	//Check for whether or not the two password fields match
	if ( !(employee.getPassword().equals(employee.getConfirmPassword())) ) {
		model.addAttribute("employee", employee);
		model.addAttribute("passwordMatch", false);
		return "employee/newE";
	}
	
	//Hashing the password and removing confirmPassword from employee
	employee.setPassword( encoder.encode(employee.getPassword()) );
	employee.setConfirmPassword(null);
	
	//Check for whether or not the email selected matches the email on file currently
	if ( !(employee.getEmail().equals(employee.getConfirmEmail())) ) {
		employeeService.update(employee);
		sessionStatus.setComplete();
		return "redirect:/emailConfirmationNotification/" + employee.getEmpId();
	}
	
	//If we have gotten this far, the new employee is about to be created, we just need to delete
	//the used registration link, as well as delete the confirmEmail field
	employee.setRegistrationUrl(null);
	employee.setConfirmEmail(null);
	
    //throw this new employee into the database
    employeeService.update(employee);
    
    model.addAttribute("states", States.values());
    
    sessionStatus.setComplete();
    
    //takes us straight to user profile page (not implemented yet)
    return "redirect:/dashboard";
  }
  
  @GetMapping("/emailConfirmationNotification/{empId}")
  public String emailConfirmationNotification(@PathVariable("empId") Integer empId) {
	  
	  	Employee employee = employeeService.findOne(empId);
	  
		//Taking care of encrypting and setting the confirmation url
		HashSlingingSlasher encoder = new HashSlingingSlasher();
		String confirmationUrl = encoder.secureRegistration();
		
		//We have to send the email in between the time when we create the random url link
		//and the time we hash it, so that the user receives a valid url. therefore . . .
	
		//sending confirmation email to employee
		employee.setConfirmationUrl(confirmationUrl);
		mailService.sendEmail(employee, "emailConfirmation");
		
		//setting hashed registration url
		employee.setConfirmationUrl( encoder.encode(confirmationUrl) );
	  
		employeeService.update(employee);
		
	    return "employee/emailConfirmation";
  }
  
  @GetMapping("/emailConfirmation/{confirmationUrl}")
  public String emailConfirmation(@PathVariable("confirmationUrl") String confirmationUrl) {
	  
	  Employee newEmp = employeeService.findByConfirmationUrl(confirmationUrl);
	  if ( newEmp ==  null )  
		  return "redirect:/";
	  
	  newEmp.setEmail(newEmp.getConfirmEmail());
	  newEmp.setConfirmEmail(null);
	  newEmp.setRegistrationUrl(null);
	  newEmp.setConfirmationUrl(null);
	  
	  employeeService.update(newEmp);
	  
	  return "employee/emailConfirmed";
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
  
  @GetMapping("/confirmNewPassword/{id}")
  public String confirmNewPasswordGet(@PathVariable("id") Integer id,
		  						   Model model) {
	  
	  model.addAttribute("employee", employeeService.findOne(id));
	  
	  return "employee/confirmNewPassword";
  }
  
  @PostMapping("/confirmNewPassword/{id}")
  public String confirmNewPasswordPost(@ModelAttribute("employee") Employee employee,
		  							   SessionStatus sessionStatus,
		  							   Model model) {
	  HashSlingingSlasher encoder = new HashSlingingSlasher();
	  
	  if ( !(encoder.decode(employee.getConfirmPassword(), employee.getPassword())) ) {
		  model.addAttribute("employee", employee);
		  model.addAttribute("wrongPassword", false);
		  return "employee/confirmNewPassword";
	  }
		  
	  sessionStatus.setComplete();
	  
	  return "redirect:/newPassword/" + employee.getEmpId();
  }
  
  @GetMapping("/newPassword/{id}")
  public String newPasswordGet(@PathVariable("id") Integer id,
		  						   Model model) {
	  
	  model.addAttribute("employee", employeeService.findOne(id));
	  
	  return "employee/newPassword";
  }
  
  @PostMapping("/newPassword/{id}")
  public String newPasswordPost(@ModelAttribute("employee") Employee employee,
		  					 	SessionStatus sessionStatus,
		  						Model model) {
	  HashSlingingSlasher encoder = new HashSlingingSlasher();
	  
	  if ( !( employee.getPassword().equals(employee.getConfirmPassword()) ) ) {
		  model.addAttribute("employee", employee);
		  model.addAttribute("passwordMatch", false);
		  return "employee/newPassword";
	  }
	  
	  employee.setPassword( encoder.encode(employee.getPassword()) );
	  employee.setConfirmPassword(null);
	  
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
  		employee.setActive(true);
  		employee.setHireDate(LocalDate.now());
  		employee.setPermissionLevel(1);
  		
  		//Taking care of encrypting and setting the confirmation url
  		HashSlingingSlasher encoder = new HashSlingingSlasher();
  		String registrationUrl = encoder.secureRegistration();
  		
  		//We have to send the email in between the time when we create the random url link
  		//and the time we hash it, so that the user receives a valid url. therefore . . .
  
  		//sending email to new employee
  		employee.setRegistrationUrl(registrationUrl);
  		mailService.sendEmail(employee, "employeeRegistration");
  		
  		//setting hashed registration url
  		employee.setRegistrationUrl( encoder.encode(registrationUrl) );
  		//updating new employee
    	employeeService.create(employee);
  		

  		
  		return "redirect:/admin";
  	}
}
