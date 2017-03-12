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
	
	//renders paystub/history
	//linksto: employee/dashboard(this)
	@RequestMapping(value = "/paystub/paystubHistory", method = RequestMethod.GET)
	public String historyPaystub(Model model) {
		return "paystub/history";
	}
	
	//renders paystub/new
	//linksto: admin(this)
	@RequestMapping(value = "/paystub/newPaystub", method = RequestMethod.GET)
	public String newPaystub(Model model) {
		return "paystub/new";
	}
	
	//renders paystub/view
	//linksto: ???
	@RequestMapping(value = "/paystub/viewPaystub", method = RequestMethod.GET)
	public String viewPaystub(Model model) {
		return "paystub/view";
	}
	
	//renders timesheet/history
	//linksto: employee/dashboard(this)
	@RequestMapping(value = "/timesheet/timesheetHistory", method = RequestMethod.GET)
	public String historyTimesheet(Model model) {
		return "timesheet/history";
	}
	
	//renders timesheet/new
	//linksto: admin(this)
	@RequestMapping(value = "/timesheet/submitTimesheet", method = RequestMethod.GET)
	public String newTimesheet(Model model) {
		return "timesheet/new";
	}
	
	//renders timesheet/edit
	//linksto: admin(this)
	@RequestMapping(value = "/timesheet/editTimesheet", method = RequestMethod.GET)
	public String editTimesheet(Model model) {
		return "timesheet/edit";
	}
	
	//renders timesheet/view
	//linksto: ??
	@RequestMapping(value = "/timesheet/viewTimesheet", method = RequestMethod.GET)
	public String viewTimesheet(Model model) {
		return "timesheet/view";
	}
	
	//renders timesheet/approve
	//linksto: ??
	@RequestMapping(value = "/timesheet/approveTimesheet", method = RequestMethod.GET)
	public String approveTimesheet(Model model) {
		return "timesheet/approve";
	}
	
	//renders project/history
	//linksto: employee/dashboard(this)
	@RequestMapping(value = "/project/projectHistory", method = RequestMethod.GET)
	public String historyProject(Model model) {
		return "project/history";
	}
	
	//renders project/new
	//linksto: admin(this)
	@RequestMapping(value = "/project/newProject", method = RequestMethod.GET)
	public String newProject(Model model) {
		return "project/new";
	}
	
	//renders project/edit
	//linksto: admin(this)
	@RequestMapping(value = "/project/editProject", method = RequestMethod.GET)
	public String editProject(Model model) {
		return "project/edit";
	}
	
	//renders vendor/history
	//linksto: admin(this)
	@RequestMapping(value = "/vendor/vendorHistory", method = RequestMethod.GET)
	public String historyVendor(Model model) {
		return "vendor/history";
	}
	
	//renders vendor/new
	//linksto: admin(this)
	@RequestMapping(value = "/vendor/newVendor", method = RequestMethod.GET)
	public String newVendor(Model model) {
		return "vendor/new";
	}
	
	//renders vendor/edit
	//linksto: admin(this)
	@RequestMapping(value = "/vendor/editVendor", method = RequestMethod.GET)
	public String editVendor(Model model) {
		return "vendor/edit";
	}
	
	//renders invoice/history
	//linksto: admin(this)
	@RequestMapping(value = "/invoice/invoiceHistory", method = RequestMethod.GET)
	public String historyInvoice(Model model) {
		return "invoice/history";
	}
	
	//renders invoice/new
	//linksto: admin(this)
	@RequestMapping(value = "/invoice/newInvoice", method = RequestMethod.GET)
	public String newInvoice(Model model) {
		return "invoice/new";
	}
	
	//renders invoice/view
	//linksto: ???
	@RequestMapping(value = "/invoice/viewInvoice", method = RequestMethod.GET)
	public String viewInvoice(Model model) {
		return "invoice/view";
	}
	
	//renders admin
	//linksto: a lot and i dont wanna write them out right now
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminDashboard(Model model) {
		return "admin";
	}
}
