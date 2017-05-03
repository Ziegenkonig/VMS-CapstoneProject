package com.vms.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vms.models.Employee;
import com.vms.models.Project;
import com.vms.models.ProjectEmployee;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.ProjectEmployeeService;
import com.vms.services.ProjectService;
import com.vms.services.VendorService;
import com.vms.validators.ProjectEmployeeValidator;

@Controller
@SessionAttributes({"project", "pe", "vendors"})
public class ProjectController {

	@Autowired
	private ProjectService pService = new ProjectService();
	@Autowired
	private VendorService vService = new VendorService();
	@Autowired
	private ProjectEmployeeService peService = new ProjectEmployeeService();
	@Autowired
	private ProjectEmployeeValidator pev;
	@Autowired
	private EmployeeService employeeService = new EmployeeService();
	/*
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
           binder.addValidators(new ProjectEmployeeValidator());
    }
*/
	
	@GetMapping(value = "/projects/{mode}")
	public String viewProjects(@PathVariable String mode, 
							   @RequestParam(required = false) Integer vId,
							   Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		List<Project> projects;
		switch(mode) {
			case "all":
				projects = pService.findAll();
				break;
			//not yet implemented
			case "byVendor":
				Vendor v = vService.findOne(vId);
				projects = pService.findByVendor(v);
				break;
			//not yet implemented
			default:
				projects = null;
		}

		model.addAttribute("projects", projects);
		return "project/projects";
	}
	
	@GetMapping(value = "/project/view")
	public String viewProject(@RequestParam("pId") Integer pId, Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		model.addAttribute("project", pService.findById(pId));
		return "project/viewP";
	}
	
	@GetMapping(value = "/project/new")
	public String newProject(@RequestParam(required = false) Integer vId, 
							 Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		Project p = null;
		if(vId == null) {
			p = new Project();
		} else {
			Vendor v = vService.findOne(vId);
			p = new Project(v);
		}
		List<Vendor> vendors = vService.findAll();
		model.addAttribute("vendors", vendors);
		model.addAttribute("project", p);
		return "project/newP";
	}
	
	@PostMapping("/project/new")
	public String createProject(@RequestParam(value = "vendor", required = false) Vendor v, 
								@ModelAttribute("project")@Valid Project p, BindingResult bindingResult,
								@ModelAttribute("vendors") ArrayList<Vendor> vendors,
								Model model,
								SessionStatus status) {
		//Checks to see if the input has any errors and renders the page over again with the errors included if it does
		if (bindingResult.hasErrors())
			return "project/newP";

		if(p.getVendor() == null) {
			p.setVendor(v);
		}
		pService.create(p);
		status.setComplete();
		
		return "redirect:/projects/all";
	}
	
	@GetMapping(value = "/project/edit")
	public String editProject(@RequestParam("pId") Integer pId, Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		List<Vendor> vendors = vService.findAll();
		model.addAttribute("vendors", vendors);
		model.addAttribute("project", pService.findById(pId));
		return "project/editP";
	}
	
	@PostMapping("/project/edit")
	public String saveProject(@ModelAttribute("project")@Valid Project p, 
							  BindingResult bindingResult,
							  SessionStatus status) {
		
		//Checks to see if the input has any errors and renders the page over again with the errors included if it does
		if (bindingResult.hasErrors())
			return "project/editP";
		
		pService.edit(p);
		status.setComplete();
		return "redirect:/projects/all";
	}
	
	@GetMapping("/project/addEmployee")
	public String addEmployee(@RequestParam(required=false) Integer pId, Model model) {
		
		//Adding currently logged in employee to model
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findByUsername(auth.getName());
		model.addAttribute("employee", employee);
		
		List<Employee> emps;
		Project p;
		if(!model.containsAttribute("pe") && pId == null) {
			return "redirect:/admin";
		}
		if(!model.containsAttribute("pe")) {
			System.out.println("No pe attribute");
			p = pService.findById(pId);
			model.addAttribute("project", p);
			ProjectEmployee newPe = new ProjectEmployee();
			newPe.setProject(p);
			model.addAttribute("pe", newPe);
			emps = peService.findRemainingEmployeesForProject(p);
			model.addAttribute("emps", emps);
		}
		if(!model.containsAttribute("emps")) {
			emps = peService.findRemainingEmployeesForProject(pService.findById(pId));
			model.addAttribute("emps", emps);
		}
		return "project/addEmployeeForm";
	}
	
	@PostMapping("/project/addEmployee")
	public String saveAddedEmployee(@ModelAttribute("pe") @Valid ProjectEmployee pe, 
				BindingResult result, SessionStatus status, RedirectAttributes redirectAttributes) {
		//ProjectEmployeeValidator pev = new ProjectEmployeeValidator();
		//redirectAttributes.addFlashAttribute("pId", pe.getProject().getProjectId());
		pev.validate(pe, result);
		if (result.hasErrors()){
			//return "redirect:/project/addEmployee?pId=" + pe.getProject().getProjectId();
			//status.setComplete();
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.pe", result);
	        redirectAttributes.addFlashAttribute("pe", pe);
	        redirectAttributes.addFlashAttribute("emps", peService.findRemainingEmployeesForProject(pe.getProject()));
			return "redirect:/project/addEmployee";
        }
        peService.create(pe);
		status.setComplete();
		return "redirect:/project/view?pId=" + pe.getProject().getProjectId();
	}
  
}
