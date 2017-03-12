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
	@Autowired
	private VendorService vendorService = new VendorService();
	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	@Autowired
	private EmployeeService employeeService = new EmployeeService();
	@Autowired
	private InvoiceService invoiceService = new InvoiceService();
	@Autowired
	private PaystubService paystubService = new PaystubService();
	@Autowired
	private ProjectEmployeeService projEmpService = new ProjectEmployeeService();
	@Autowired
	private ProjectTimesheetService projTimesheetService = new ProjectTimesheetService();
	@Autowired
	private TimesheetRowService timesheetRowService = new TimesheetRowService();
	
	//Renders index page
	@RequestMapping("/") // resources/templates/index.html
    public String index() {
		
//		//Commenting out testing so we dont clutter database
//		//Testing for Cascade
//		//Testing for weekly cascade
//		Timesheet timesheet1 = new Timesheet(employeeService.findOne(1), LocalDate.now());
//		timesheetService.create(timesheet1);
//		//Testing for biweekly cascade
//		Timesheet timesheet2 = new Timesheet(employeeService.findOne(2), LocalDate.now());
//		timesheetService.create(timesheet2);
//		
//		//Testing if this controller can add an entry into the DB
//		Vendor test = new Vendor();
//		test.setAddress("address");
//		test.setCity("city");
//		test.setContactName("contact_name");
//		test.setName("name");
//		test.setPhone("phone");
//		test.setPrimaryEmail("email");
//		test.setState("tn");
//		vendorService.create(test);
		
        return "index";
    }
	
	//This Model object in the argument list is from the spring library, and is basically a hashmap containing
	//all of the values you can reference inside the html the string inside the quotes is the key, 
	//the value is whatever comes after
	//Renders login page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("employee", employeeService.findOne(1));
		return "login";
	}
	
	//Renders register page
//	@RequestMapping(value = "/register", method = RequestMethod.GET)
//	public String register(Model model) {
//		return "register";
//	}
	
	//Renders employeeDashboard page
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String employeeDashboard(Model model) {
		return "employeeDashboard";
	}
	
}
