package com.vms.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	//Create a new invoice
	@GetMapping("/invoice/new")
	public String invoiceForm(Model model) {
		//Filtering out duplicate periods so dropdown box isn't cluttered, and putting them in desc order
		List<ProjectTimesheet> projectTimesheets = projTimeService.findAll();
		List<LocalDate> allDates = new ArrayList<LocalDate>();
		for (ProjectTimesheet projTime : projectTimesheets) {
			if ( !allDates.contains(projTime.getWeekStarting()) )
				allDates.add(projTime.getWeekStarting());
		}
		Collections.reverse(allDates);
		
		System.out.println(allDates.get(0) + " " + allDates.get(1));
		
		model.addAttribute("selectedProjectTimesheet", new ProjectTimesheet()); //Pass this to @PostMapping
		model.addAttribute("allDates", allDates); //All pay periods
		model.addAttribute("selectedVendor", new Vendor()); //Pass this to @PostMapping
		model.addAttribute("vendors", vendorService.findAllSorted()); //All Vendors
		
		return "invoice/newI";
	}

	
	@PostMapping("/invoice/new")
	public String invoiceSubmit(@ModelAttribute Vendor selectedVendor, ProjectTimesheet selectedProjectTimesheet) {
		//Grabbing vendor object associated with the name selected by user
		selectedVendor = vendorService.findByName(selectedVendor.getName());
		System.out.println("runs");
		//Putting all projects associated with the vendor into a list
		List<Project> projects = projectService.findByVendor(selectedVendor);
		//Cycling through projects list and appending all of the associated ProjectTimesheets into one list
		//(within pay period)
		List<ProjectTimesheet> projectTimesheets = new ArrayList<ProjectTimesheet>();
		for (Project project : projects)
			projectTimesheets.addAll(projTimeService.timesheetsForInvoice(project, selectedProjectTimesheet.getWeekStarting()));
		//Creating new invoice
		invoiceService.create(new Invoice(projectTimesheets));
		//Displaying new invoice (not implemented yet)
		return "invoice/viewI";
	}
}
