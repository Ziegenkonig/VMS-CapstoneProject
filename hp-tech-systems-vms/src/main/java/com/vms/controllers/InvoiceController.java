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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vms.models.Invoice;
import com.vms.models.InvoiceStatus;
import com.vms.models.Project;
import com.vms.models.ProjectTimesheet;
import com.vms.models.Vendor;
import com.vms.services.InvoiceService;
import com.vms.services.ProjectService;
import com.vms.services.ProjectTimesheetService;
import com.vms.services.VendorService;
import com.vms.utilities.MailService;

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
	@Autowired
	MailService mailService;
	
	//CREATING NEW INVOICE
	//Needs countermeasures to make sure only authorized people can view
	@GetMapping("/invoice/new")
	public String invoiceForm(Model model) {
		
		//Here we have to transfer all of the LocalDates inside of uniqueDates into Strings because LocalDates are dumb
		//Or maybe I am and I just dont know what im doing
		List<LocalDate> uniqueDates = projTimeService.uniqueDates();

		//Setting all of the model attributes that are used in the corresponding html
		model.addAttribute("allDates", uniqueDates); //All unique pay periods
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

		//Cycling through projects list and appending all of the associated ProjectTimesheets into one list
		//(within pay period)
		List<ProjectTimesheet> projectTimesheets = new ArrayList<ProjectTimesheet>();
		for (Project project : projects)
			projectTimesheets.addAll(projTimeService.timesheetsForInvoice(project.getProjectId(), date));

		//Creating new invoice
		Invoice newInvoice = new Invoice(projectTimesheets);
		invoiceService.create(newInvoice);
		mailService.sendEmail(newInvoice, "invoiceReady");
		//Displaying new invoice
		return "redirect:" + "http://localhost:8080/invoice/view/" + newInvoice.getInvoiceId();
	}
	
	//VIEWING ALL INVOICES
	//Needs countermeasures to make sure only authorized people can view
	@GetMapping(value = "/invoices")
	public String viewInvoices(Model model) {
		
		//Getting all invoices
		List<Invoice> invoices = invoiceService.findAll();
		
		//Adding all invoices to the model
		model.addAttribute("draft", InvoiceStatus.DRAFT);
		model.addAttribute("invoices", invoices);
		model.addAttribute("projectService", projectService);
		//specifying which html file rendering
		return "invoice/invoices";
	}
	
	@GetMapping("invoice/regenerate/{id}")
	public String regenerateInvoice(@PathVariable("id") Integer invoiceId) {
		
		Invoice regenInvoice = invoiceService.findById(invoiceId);
		
		//Grabbing vendor object associated with the name selected by user
		Vendor v = vendorService.findOne(regenInvoice.getVendorId());
		
		//Putting all projects associated with the vendor into a list
		List<Project> projects = projectService.findByVendor(v);

		//Cycling through projects list and appending all of the associated ProjectTimesheets into one list
		//(within pay period)
		List<ProjectTimesheet> projectTimesheets = new ArrayList<ProjectTimesheet>();
		for (Project project : projects)
			projectTimesheets.addAll(projTimeService.timesheetsForInvoice(project.getProjectId(), regenInvoice.getPeriodStart()));

		//Creating new invoice
		regenInvoice = new Invoice(projectTimesheets);
		invoiceService.edit(regenInvoice);
		mailService.sendEmail(regenInvoice, "invoiceReady");
		return "redirect:/invoice/view/" + invoiceId;
	}
	
	//VIEWING ONE INVOICE
	//Needs countermeasures to make sure only authorized people can view
	@GetMapping(value = "/invoice/view/{id}")
	public String viewInvoice(@PathVariable("id") Integer invoiceId, Model model) {
		//Takes invoiceId, which is stored inside of the url, and finds the associated invoice
		Invoice invoice = invoiceService.findById(invoiceId);
		//Finds the associated project from the given invoice
		Project project = projectService.findById(invoice.getProjectId());
		
		model.addAttribute("invoice", invoice);
		model.addAttribute("project", project);
		
		return "invoice/viewI";
	}

}
