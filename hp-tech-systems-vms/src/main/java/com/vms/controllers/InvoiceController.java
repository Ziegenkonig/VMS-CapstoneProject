package com.vms.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vms.models.Invoice;
import com.vms.models.ProjectTimesheet;

@Controller
public class InvoiceController {


	@GetMapping("/invoice/new")
	public String invoiceForm(Model model) {
		model.addAttribute("invoice", new Invoice( new ArrayList<ProjectTimesheet>() ));
		return "invoice/newI";
	}


	@PostMapping("/invoice/new")
	public String invoiceSubmit(@ModelAttribute Invoice invoice) {
		
		return "admin";
	}
}
