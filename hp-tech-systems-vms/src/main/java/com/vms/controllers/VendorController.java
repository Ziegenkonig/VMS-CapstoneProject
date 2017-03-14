package com.vms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Vendor;
import com.vms.services.VendorService;

@Controller
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
		Vendor v = new Vendor();
		model.addAttribute("vendor", v);
		return "vendor/newV";
	}
	
	@PostMapping(value = "/vendor/new")
	public String createVendor(@ModelAttribute Vendor v) {
		vendorService.create(v);
		//some validation crap needs to go here bc it should show error on same page or continue on
		return "vendor/viewV";
	}
	
	//get an vendor object and populate form fields -working
	@GetMapping(value = "/vendor/edit/{name}")
	public String editVendor(
			@PathVariable String name, 
			Model model) {
		model.addAttribute("vendor", vendorService.findByName(name));
		return "vendor/editV";
	}
	
	@PostMapping(value = "/vendor/edit")
	public String updateVendor(@ModelAttribute Vendor v, Model model) {
		vendorService.update(v);
		//some validation crap needs to go here bc it should show error on same page or continue on
		return "vendor/viewV";
	}
	
	
}

