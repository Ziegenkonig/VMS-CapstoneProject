package com.vms.controllers;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.models.PaystubStatus;
import com.vms.models.Timesheet;
import com.vms.services.EmployeeService;
import com.vms.services.PaystubService;
import com.vms.services.TimesheetService;

@Controller
@SessionAttributes("paystub")
public class PaystubController {

	@Autowired
	TimesheetService tSService = new TimesheetService();
	
	@Autowired
	PaystubService pSService = new PaystubService();
	
	@Autowired
	EmployeeService	empService = new EmployeeService();
	
	//reads all vendors from the db and displays in table form -working
	@GetMapping(value = "/paystubs/{mode}")
	public String viewPaystubs(@PathVariable String mode, @RequestParam(required = false) Integer empId, Model model) {
		List<Paystub> paystubs;
		switch(mode) {
			case "all":
				paystubs = pSService.findAll();
				break;
			case "byEmployee":
				Employee e = empService.findOne(empId);
				paystubs = pSService.findPaystubByEmployee(e);
				break;
			default:
				paystubs = null;
		}
		
		model.addAttribute("paystubs", paystubs);
		return "paystub/paystubs";
	}
	
	//generate new paystub based on a timesheet
	@GetMapping("/paystub/new/{tsId}")
	public String newPaystub(@PathVariable Integer tsId, Model model) {
		Timesheet t = tSService.findById(tsId);
		Paystub prev = pSService.findPreviousPaystubForYtd(t.getEmployee(), t);
		if(prev == null) {
			Paystub ps = new Paystub(t);
			pSService.create(ps);
			model.addAttribute("paystub", ps);
		} else {
			Paystub ps = new Paystub(t, prev);
			pSService.create(ps);
			model.addAttribute("paystub", ps);
		}
		return "paystub/viewPs";
	}
	
	//view an existing paystub
	@GetMapping("/paystub/{psId}")
	public String viewExistingPaystub(@PathVariable Integer psId, Model model) {
		Paystub ps = pSService.findById(psId);
		model.addAttribute("paystub", ps);
		return "paystub/viewPs";
	}
	
	//adding a check number and updating status
	@GetMapping("/paystub/addCheck")
	public String addCheckToPaystub(@RequestParam Integer pId, Model model) {
		Paystub ps = pSService.findById(pId);
		model.addAttribute("paystub", ps);
		return "paystub/checkNoForm";
	}
	
	@PostMapping("/paystub/addCheck")
	public String saveCheckToPaystub(@ModelAttribute("paystub") Paystub ps, SessionStatus status) {
		ps.setStatus(PaystubStatus.ISSUED);
		ps.setCheckDate(ZonedDateTime.now());
		pSService.update(ps);
		status.setComplete();
		return "redirect:/paystubs/all";
	}

}
