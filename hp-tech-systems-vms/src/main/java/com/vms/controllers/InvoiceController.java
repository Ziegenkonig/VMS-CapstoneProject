package com.vms.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	//Created this so I can just pass the string object over to the post method
	
	//Create a new invoice
	@GetMapping("/invoice/new")
	public String invoiceForm(Model model) {
		
		//Need a string to keep track of selected date since Thymeleaf and LocalDate dont play well together
		//I had to create a new object that specifically holds a string as an attribute to pass it correctly
		//StringHolder stringHolder = new StringHolder();
		//Here we have to transfer all of the LocalDates inside of uniqueDates into Strings because LocalDates are dumb
		//Or maybe I am and I just dont know what im doing
		List<LocalDate> uniqueDates = projTimeService.uniqueDates();
		/*
		List<String> allDates = new ArrayList<String>();
		for (LocalDate date : uniqueDates) 
			allDates.add(date.toString());
		*/
		//Setting all of the model attributes that are used in the corresponding html
		//model.addAttribute("stringHolder", stringHolder); //contains the user selected date in string format
		model.addAttribute("allDates", uniqueDates); //All unique pay periods
		//model.addAttribute("selectedVendor", new Vendor()); //Pass this to @PostMapping
		model.addAttribute("vendorNames", vendorService.findAllNames()); //All Vendors
		
		return "invoice/newI";
	}

	//Everything in here happens after the user presses the submit button, and is executes in-between pages
	@PostMapping("/invoice/new")
	public String invoiceSubmit(@ModelAttribute("vendorName") String vendorName,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
		
		System.out.println(vendorName);
		System.out.println(date.toString());
		System.out.println(date.getClass());
		//Grabbing vendor object associated with the name selected by user
		Vendor v = vendorService.findByName(vendorName);
		//Putting all projects associated with the vendor into a list
		List<Project> projects = projectService.findByVendor(v);
		System.out.println(projects.isEmpty());
		//Cycling through projects list and appending all of the associated ProjectTimesheets into one list
		//(within pay period)
		List<ProjectTimesheet> projectTimesheets = new ArrayList<ProjectTimesheet>();
		for (Project project : projects)
			projectTimesheets.addAll(projTimeService.timesheetsForInvoice(project.getProjectId(), date));
		System.out.println(projectTimesheets.isEmpty());
		//Creating new invoice
		Invoice newInvoice = null;
		if(!projectTimesheets.isEmpty()) {
			newInvoice = new Invoice(projectTimesheets);
			invoiceService.create(newInvoice);
			//Displaying new invoice (not implemented yet)
		}
		return "invoice/viewI";
	}
}
