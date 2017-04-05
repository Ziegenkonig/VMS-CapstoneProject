package com.vms.controllers;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//Spring imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.models.Timesheet;
import com.vms.services.EmployeeService;
import com.vms.services.PaystubService;
import com.vms.services.TimesheetService;

@Controller
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
	  	model.addAttribute("employee", new Employee());
    	return "employee/newE";
  	}

	@PostMapping("/register")
	public String employeeSubmit(@ModelAttribute Employee employee){
		//setting hiredate to present time
		employee.setHireDate(LocalDate.now());
	
		//throw this new employee into the database
		employeeService.create(employee);
	
		//takes us straight to user profile page (not implemented yet)
		return "employee/dashboard";
	}
	
	//employeeService.findOne(1) here just populates the editProfile page with the correct information
	@GetMapping("/editUserProfile")
	public String employeeEditForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "employee/editE";
	}
	
	
	@PostMapping("/editUserProfile")
	public String employeeEdit(@ModelAttribute Employee employee){
	
		//We need to set uneditable values of the employee we are editing manually
		Employee editEmployee = employeeService.findByUsername(employee.getUsername());
		employee.setEmpId(editEmployee.getEmpId());
		employee.setHireDate(editEmployee.getHireDate());
		employee.setPermissionLevel(editEmployee.getPermissionLevel());
		employee.setProjemps(editEmployee.getProjemps());
		employee.setActive(editEmployee.isActive());
		employee.setTimesheets(editEmployee.getTimesheets());
		employee.setPayPeriod(employee.getPayPeriod());
	
		//update this employee in the database
		employeeService.update(employee);
	
		return "dashboard";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		  
		  int empIdPlaceholder = 2;
		  
		  List<Paystub> issuedPaystubs = paystubService.findIssued(empIdPlaceholder);
		  List<Timesheet> openTimesheets = timesheetService.dashboardTimesheets(employeeService.findOne(empIdPlaceholder));
		  Employee e = employeeService.findOne(empIdPlaceholder);
		  
		  model.addAttribute("e", e);
		  model.addAttribute("eId", empIdPlaceholder);
		  model.addAttribute("openTimesheets", openTimesheets);
		  model.addAttribute("issuedPaystubs", issuedPaystubs);
		  
		  return "employee/dashboard";
	}
}
