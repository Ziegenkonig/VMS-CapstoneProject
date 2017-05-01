package com.vms.controllers;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.vms.models.Permission;
import com.vms.models.Timesheet;
import com.vms.services.EmployeeService;
import com.vms.services.PaystubService;
import com.vms.services.TimesheetService;
import com.vms.utilities.MailService;

@Controller
@SessionAttributes("paystub")
public class PaystubController {

	@Autowired
	EmployeeService employeeService = new EmployeeService();
	@Autowired
	TimesheetService tSService = new TimesheetService();
	@Autowired
	PaystubService pSService = new PaystubService();
	@Autowired
	EmployeeService	empService = new EmployeeService();
	@Autowired
	MailService mailService;
	
	//reads all vendors from the db and displays in table form -working
	@GetMapping(value = "/paystubs/{mode}")
	public String viewPaystubs(@PathVariable String mode, 
							   //@RequestParam Integer callerId,
							   @RequestParam(required = false) Integer empId,
							   @RequestParam(required = false) PaystubStatus status,
							   Model model) {
		//Checking to make sure the user isn't being naughty
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee e = employeeService.findByUsername(auth.getName());
		if (e.getEmpId() != empId && e.getPermissionLevel() == Permission.ROLE_USER.toString())
			return "redirect:/paystubs/byEmployee?empId=" + e.getEmpId();
		
		List<Paystub> paystubs;
		switch(mode) {
			case "all":
				paystubs = pSService.findAll();
				break;
			//not yet implemented
			case "byEmployee":
				//paystubs = pSService.findPaystubByEmployee(e);
				paystubs = pSService.findIssued(empId);
				break;
			//not yet implemented
			case "byStatus":
				paystubs = pSService.findByStatus(status);
				break;
			default:
			//	e = null;
				paystubs = null;
		}
		e = empService.findOne(empId);
		
		model.addAttribute("employee", e);
		model.addAttribute("permissions", Permission.values());
		model.addAttribute("paystubs", paystubs);
		return "paystub/paystubs";
	}
	
	//generate new paystub based on a timesheet
	@GetMapping("/paystub/new/{tsId}")
	public String newPaystub(@PathVariable Integer tsId, Model model) {
		Timesheet t = tSService.findById(tsId);
		Paystub prev = pSService.findPreviousPaystubForYtd(t.getEmployee(), t);
		Paystub ps = null;
		if(prev == null) {
			ps = new Paystub(t);
		} else {
			ps = new Paystub(t, prev);
		}
		pSService.create(ps);
		model.addAttribute("paystub", ps);
		return "redirect:/paystub/" + ps.getPaystubId();
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
		mailService.sendEmail(ps, "paystub");
		return "redirect:/paystubs/all?empId=6";
	}
	
	//recreating a paystub from an existing paystub
	@GetMapping("/paystub/regenerate")
	public String reGeneratePaystub(@RequestParam Integer pId) {
		Paystub ps = pSService.findById(pId);
		Paystub copy;
		if(ps.getPrevPaystubId() != null) {
			copy = new Paystub(ps.getTimesheet(), pSService.findById(ps.getPrevPaystubId()));
		} else {
			copy = new Paystub(ps.getTimesheet());
		}
		pSService.voidPaystub(ps);
		pSService.create(copy);
		return "redirect:/paystubs/all?empId=6";
	}
	
	//return associated timesheet and void paystub
	@GetMapping("/paystub/returnTimesheet")
	public String returnAssociatedTimesheet(@RequestParam Integer psId) {
		Paystub ps = pSService.findById(psId);
		Timesheet ts = ps.getTimesheet();
		tSService.returnTimesheet(ts);
		pSService.voidPaystub(ps);
		mailService.sendEmail(ts, "timesheetCorrection");
		return "redirect:/paystubs/all?empId=6";
	}
}
