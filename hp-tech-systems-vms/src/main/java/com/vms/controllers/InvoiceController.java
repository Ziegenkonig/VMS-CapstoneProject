package com.vms.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Invoice;
import com.vms.models.Project;
import com.vms.models.ProjectTimesheet;
import com.vms.models.Vendor;
import com.vms.services.InvoiceService;
import com.vms.services.ProjectService;
import com.vms.services.ProjectTimesheetService;
import com.vms.services.VendorService;

@Controller
public class InvoiceController {
	//Pulling in vendor and invoice and project services
	@Autowired
	VendorService vendorService = new VendorService();
	@Autowired
	InvoiceService invoiceService = new InvoiceService();
	@Autowired
	ProjectService projectService = new ProjectService();
	@Autowired
	ProjectTimesheetService projTimeService = new ProjectTimesheetService();
	
	//Create a new invoice
	@GetMapping()
	public String invoiceForm(Model model) {
		//Have to instantiate the LocalDate
		LocalDate date = null;
		
		model.addAttribute("dateSelected", date); //Pass this to @PostMapping
		model.addAttribute("projectTimesheets", projTimeService.findAll()); //All pay periods (need to shorten eventually)
		model.addAttribute("selectedVendor", new Vendor()); //Pass this to @PostMapping
		model.addAttribute("vendors", vendorService.findAll()); //All Vendors
		
		return "invoice/newI";
	}

	
	@PostMapping()
	public String invoiceSubmit(@ModelAttribute Vendor selectedVendor, @ModelAttribute LocalDate date) {
		//Grabbing vendor object associated with the name selected by user
		selectedVendor = vendorService.findByName(selectedVendor.getName());
		//Putting all projects associated with the vendor into a list
		List<Project> projects = projectService.findByVendor(selectedVendor);
		//Cycling through projects list and appending all of the associated ProjectTimesheets into one list
		//(within pay period)
		List<ProjectTimesheet> projectTimesheets = new ArrayList<ProjectTimesheet>();
		for (Project project : projects)
			projectTimesheets.addAll(projTimeService.timesheetsForInvoice(project, date));
		//Creating new invoice
		invoiceService.create(new Invoice(projectTimesheets));
		//Displaying new invoice (not implemented yet)
		return "admin";
	}
}
