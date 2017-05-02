package com.vms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vms.models.Employee;
import com.vms.models.States;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.VendorService;

@Controller
@SessionAttributes({"vendor", "states"})
public class VendorController {
	
	@Autowired
	private VendorService vendorService = new VendorService();
	@Autowired
	private EmployeeService employeeService = new EmployeeService();

	//reads all vendors from the db and displays in table form -working
	@GetMapping(value = "/vendors")
	public String viewVendors(Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		List<Vendor> vendors = vendorService.findAll();
		model.addAttribute("vendors", vendors);
		return "vendor/vendors";
	}
	
	//displays a single vendor -working
	@GetMapping(value = "/vendor/view")
	public String viewVendor(@RequestParam("vId") Integer vId, Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		model.addAttribute("vendor", vendorService.findOne(vId));
		return "vendor/viewV";
	}
	
	//create new vendor object and display form fields -working
	@GetMapping(value = "/vendor/new")
	public String newVendor(Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		Vendor vendor = new Vendor();
		
		model.addAttribute("states", States.values());
		model.addAttribute("vendor", vendor);
		return "vendor/newV";
	}
	
	@PostMapping(value = "/vendor/new")
	public String createVendor(@ModelAttribute@Valid Vendor vendor,
							   BindingResult bindingResult,
							   Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		if (bindingResult.hasErrors())
			return "vendor/newV";
		
		vendorService.create(vendor);
		
		model.addAttribute("states", States.values());
		
		return "redirect:/vendor/view?vId=" + vendor.getVendorId();
	}
	
	//get an vendor object and populate form fields -working
	@GetMapping(value = "/vendor/edit")
	public String editVendor(@RequestParam("vId") Integer vId, 
							 Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		model.addAttribute("vendor", vendorService.findOne(vId));
		model.addAttribute("states", States.values());
		
		return "vendor/editV";
	}
	
	@PostMapping(value = "/vendor/edit")
	public String updateVendor(@ModelAttribute("vendor")@Valid Vendor vendor, 
							   BindingResult bindingResult,
							   SessionStatus sessionStatus,
							   Model model) {
		//input validation
		if (bindingResult.hasErrors())
			return "vendor/editV";
		
		vendorService.update(vendor);
		
		sessionStatus.setComplete();
		
		model.addAttribute("states", States.values());
		
		return "redirect:/vendor/view?vId=" + vendor.getVendorId();
	}
	
	
}

