package com.vms.controllers;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
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

	//renders timesheet/approve
	//linksto: ??
	@RequestMapping(value = "/timesheet/approve", method = RequestMethod.GET)
	public String approveTimesheet(Model model) {
		return "timesheet/approve";
	}
	
	//renders admin
	//linksto: a lot and i dont wanna write them out right now
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminDashboard(Model model) {
		return "admin";
	}
}
