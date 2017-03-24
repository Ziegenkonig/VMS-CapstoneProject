package com.vms.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		StringHolder stringHolder = new StringHolder();
		//Here we have to transfer all of the LocalDates inside of uniqueDates into Strings because LocalDates are dumb
		//Or maybe I am and I just dont know what im doing
		List<LocalDate> uniqueDates = projTimeService.uniqueDates();
		List<String> allDates = new ArrayList<String>();
		for (LocalDate date : uniqueDates) 
			allDates.add(date.toString());
		
		//Setting all of the model attributes that are used in the corresponding html
		model.addAttribute("stringHolder", stringHolder); //contains the user selected date in string format
		model.addAttribute("allDates", allDates); //All unique pay periods
		model.addAttribute("selectedVendor", new Vendor()); //Pass this to @PostMapping
		model.addAttribute("vendors", vendorService.findAllNames()); //All Vendors
		
		return "invoice/newI";
	}

	//Everything in here happens after the user presses the submit button, and is executes in-between pages
	@PostMapping("/invoice/new")
	public String invoiceSubmit(@ModelAttribute("selectedVendor") Vendor selectedVendor,
								@ModelAttribute("stringHolder") StringHolder stringHolder) {
		
		//Grabbing vendor object associated with the name selected by user
		selectedVendor = vendorService.findByName(selectedVendor.getName());
		//Putting all projects associated with the vendor into a list
		List<Project> projects = projectService.findByVendor(selectedVendor);

		//Cycling through projects list and appending all of the associated ProjectTimesheets into one list
		//(within pay period)
		List<ProjectTimesheet> projectTimesheets = new ArrayList<ProjectTimesheet>();
		for (Project project : projects)
			projectTimesheets.addAll(projTimeService.timesheetsForInvoice(project, LocalDate.parse(stringHolder.string)));
		
		//Creating new invoice
		Invoice newInvoice = new Invoice(projectTimesheets);
		invoiceService.create(newInvoice);
		//Displaying new invoice (not implemented yet)
		return "invoice/viewI";
	}
}
