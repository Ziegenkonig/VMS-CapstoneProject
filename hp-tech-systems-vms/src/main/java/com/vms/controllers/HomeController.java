package com.vms.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vms.models.Employee;
import com.vms.services.EmployeeService;

@Controller
public class HomeController {
	
	@Autowired
	private EmployeeService employeeService = new EmployeeService();
	
	//Renders index
	/*
	@RequestMapping(value = "/")

    public String index() {	
        return "employee/dashboard";
    }*/
	
	//This Model object in the argument list is from the spring library, and is basically a hashmap containing
	//all of the values you can reference inside the html; the string inside the quotes is the key, 
	//the associated value is whatever comes after
	
	//Renders employee/login
	//linksto: employee/register(EmployeeController) | employee/dashboard(this)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Principal principal, Model model) {
		//Adding currently logged in employee to model
  		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
  		
		model.addAttribute("employee", employee);
		
		//model.addAttribute("employee", employeeService.findOne(1));
		return "employee/login";
	}
}
