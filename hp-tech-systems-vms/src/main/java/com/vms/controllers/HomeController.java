package com.vms.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vms.models.Employee;
import com.vms.models.Timesheet;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.InvoiceService;
import com.vms.services.PaystubService;
import com.vms.services.ProjectEmployeeService;
import com.vms.services.ProjectTimesheetService;
import com.vms.services.TimesheetRowService;
import com.vms.services.TimesheetService;
import com.vms.services.VendorService;

@Controller
public class HomeController {
	//Hooking up this controller to all services
//	@Autowired
//	private VendorService vendorService = new VendorService();
//	@Autowired
//	private TimesheetService timesheetService = new TimesheetService();
//	@Autowired
//	private EmployeeService employeeService = new EmployeeService();
//	@Autowired
//	private InvoiceService invoiceService = new InvoiceService();
//	@Autowired
//	private PaystubService paystubService = new PaystubService();
//	@Autowired
//	private ProjectEmployeeService projEmpService = new ProjectEmployeeService();
//	@Autowired
//	private ProjectTimesheetService projTimesheetService = new ProjectTimesheetService();
//	@Autowired
//	private TimesheetRowService timesheetRowService = new TimesheetRowService();
	
	//Renders index
	@RequestMapping(value = "/")
    public String index() {	
        return "index";
    }
	
	//This Model object in the argument list is from the spring library, and is basically a hashmap containing
	//all of the values you can reference inside the html; the string inside the quotes is the key, 
	//the associated value is whatever comes after
	
	//Renders employee/login
	//linksto: employee/register(EmployeeController) | employee/dashboard(this)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		//model.addAttribute("employee", employeeService.findOne(1));
		return "employee/login";
	}
	
	//Renders employee/dashboard 
	//linksto: timesheet/history(this) | timesheet/new(this) | paystub/history(this) | employee/edit(this)
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String employeeDashboard(Model model) {
		return "employee/dashboard";
	}
	
	//renders employee/edit
	//linksto:
//	@RequestMapping(value = "/editUserProfile", method = RequestMethod.GET)
//	public String employeeEdit(Model model) {
//		return "employee/edit";
//	}
	
	//renders paystub/history
	//linksto: 
	@RequestMapping(value = "/dashboard/paystubHistory", method = RequestMethod.GET)
	public String paystubHistory(Model model) {
		return "paystub/history";
	}
	
	//renders timesheet/history
	//linksto:
	@RequestMapping(value = "/dashboard/timesheetHistory", method = RequestMethod.GET)
	public String timesheetHistory(Model model) {
		return "timesheet/history";
	}
	
	//renders timesheet/new
	//linksto:
	@RequestMapping(value = "/dashboard/submitTimesheet", method = RequestMethod.GET)
	public String newTimesheet(Model model) {
		return "timesheet/new";
	}
	
}
