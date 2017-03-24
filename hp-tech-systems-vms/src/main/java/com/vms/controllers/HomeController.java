package com.vms.controllers;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.vms.models.Employee;
//import com.vms.models.Timesheet;
//import com.vms.models.Vendor;
//import com.vms.services.EmployeeService;
//import com.vms.services.InvoiceService;
//import com.vms.services.PaystubService;
//import com.vms.services.ProjectEmployeeService;
//import com.vms.services.ProjectTimesheetService;
//import com.vms.services.TimesheetRowService;
//import com.vms.services.TimesheetService;
//import com.vms.services.VendorService;

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
	/*
	//renders paystub/history
	//linksto: employee/dashboard(this)
	@RequestMapping(value = "/paystubs", method = RequestMethod.GET)
	public String historyPaystub(Model model) {
		return "paystub/paystubs";
	}
	
	//renders paystub/view
	//linksto: ???
	@RequestMapping(value = "/paystub/view", method = RequestMethod.GET)
	public String viewPaystub(Model model) {
		return "paystub/viewPs";
	}
	/*
	//renders timesheet/history
	//linksto: employee/dashboard(this)
	@RequestMapping(value = "/timesheets", method = RequestMethod.GET)
	public String historyTimesheet(Model model) {
		return "timesheet/timesheets";
	}
	*/
	//renders timesheet/new
	//linksto: admin(this)
	@RequestMapping(value = "/timesheet/new", method = RequestMethod.GET)
	public String newTimesheet(Model model) {
		return "timesheet/newT";
	}
	
	//renders timesheet/edit
	//linksto: admin(this)
	@RequestMapping(value = "/timesheet/edit", method = RequestMethod.GET)
	public String editTimesheet(Model model) {
		return "timesheet/editT";
	}
	
	//renders timesheet/view
	//linksto: ??
	@RequestMapping(value = "/timesheet/view", method = RequestMethod.GET)
	public String viewTimesheet(Model model) {
		return "timesheet/viewT";
	}
	
	//renders timesheet/approve
	//linksto: ??
	@RequestMapping(value = "/timesheet/approve", method = RequestMethod.GET)
	public String approveTimesheet(Model model) {
		return "timesheet/approve";
	}
	/*
	//renders project/history
	//linksto: employee/dashboard(this)
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String viewProjects(Model model) {
		return "project/projects";
	}
	
	//renders project/new
	//linksto: admin(this)
	@RequestMapping(value = "/project/new", method = RequestMethod.GET)
	public String newProject(Model model) {
		return "project/newP";
	}
	
	//renders project/edit
	//linksto: admin(this)
	@RequestMapping(value = "/project/edit", method = RequestMethod.GET)
	public String editProject(Model model) {
		return "project/editP";
	}
	
	//renders project/view - details of the project like rails show
	//linksto: admin(this)
	@RequestMapping(value = "/project/view", method = RequestMethod.GET)
	public String viewProject(Model model) {
		return "project/viewP";
	}
	/*
	//renders vendor/history
	//linksto: admin(this)
	@RequestMapping(value = "/vendors", method = RequestMethod.GET)
	public String historyVendor(Model model) {
		return "vendor/vendors";
	}
	
	//renders vendor/new
	//linksto: admin(this)
	@RequestMapping(value = "/vendor/new", method = RequestMethod.GET)
	public String newVendor(Model model) {
		return "vendor/newV";
	}
	
	//renders vendor/edit
	//linksto: admin(this)
	@RequestMapping(value = "/vendor/edit", method = RequestMethod.GET)
	public String editVendor(Model model) {
		return "vendor/editV";
	}
	
	//renders vendor/view - details of the project like rails show
	//linksto: admin(this)
	@RequestMapping(value = "/vendor/view", method = RequestMethod.GET)
	public String viewVendor(Model model) {
		return "vendor/viewV";
	}
	*/
	
	//renders invoice/history
	//linksto: admin(this)
	@RequestMapping(value = "/invoices", method = RequestMethod.GET)
	public String historyInvoice(Model model) {
		return "invoice/invoices";
	}
	
	//renders invoice/new
	//linksto: admin(this)
	@RequestMapping(value = "/invoice/new", method = RequestMethod.GET)
	public String newInvoice(Model model) {
		return "invoice/newI";
	}
	
	//renders invoice/view
	//linksto: ???
	@RequestMapping(value = "/invoice/view", method = RequestMethod.GET)
	public String viewInvoice(Model model) {
		return "invoice/viewI";
	}
	
	//renders admin
	//linksto: a lot and i dont wanna write them out right now
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminDashboard(Model model) {
		return "admin";
	}
}
