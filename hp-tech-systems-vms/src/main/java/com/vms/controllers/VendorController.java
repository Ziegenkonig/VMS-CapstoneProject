package com.vms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vms.models.States;
import com.vms.models.Vendor;
import com.vms.services.VendorService;

@Controller
@SessionAttributes({"vendor", "states"})
public class VendorController {
	
	@Autowired
	private VendorService vendorService = new VendorService();

	//reads all vendors from the db and displays in table form -working
	@GetMapping(value = "/vendors")
	public String viewVendors(Model model) {
		List<Vendor> vendors = vendorService.findAll();
		model.addAttribute("vendors", vendors);
		return "vendor/vendors";
	}
	
	//displays a single vendor -working
	@GetMapping(value = "/vendor/view/{name}")
	public String viewVendor(@PathVariable String name, Model model) {
		model.addAttribute("vendor", vendorService.findByName(name));
		return "vendor/viewV";
	}
	
	//create new vendor object and display form fields -working
	@GetMapping(value = "/vendor/new")
	public String newVendor(Model model) {
		
		Vendor vendor = new Vendor();
		
		model.addAttribute("states", States.values());
		model.addAttribute("vendor", vendor);
		return "vendor/newV";
	}
	
	@PostMapping(value = "/vendor/new")
	public String createVendor(@ModelAttribute@Valid Vendor vendor,
							   BindingResult bindingResult,
							   Model model) {
		
		if (bindingResult.hasErrors())
			return "vendor/newV";
		
		vendorService.create(vendor);
		
		model.addAttribute("states", States.values());
		
		return "vendor/viewV";
	}
	
	//get an vendor object and populate form fields -working
	@GetMapping(value = "/vendor/edit/{id}")
	public String editVendor(@PathVariable Integer id, 
							 Model model) {
		
		model.addAttribute("vendor", vendorService.findOne(id));
		model.addAttribute("states", States.values());
		
		return "vendor/editV";
	}
	
	@PostMapping(value = "/vendor/edit/{id}")
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
		
		return "vendor/viewV";
	}
	
	
}

