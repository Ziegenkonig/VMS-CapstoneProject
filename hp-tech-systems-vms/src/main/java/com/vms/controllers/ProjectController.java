package com.vms.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.vms.models.Project;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.ProjectEmployeeService;
import com.vms.services.ProjectService;
import com.vms.services.VendorService;

@Controller
@SessionAttributes({"project", "pe", "vendors"})
public class ProjectController {

	@Autowired
	private ProjectService pService = new ProjectService();
	@Autowired
	private VendorService vService = new VendorService();
	
	@Autowired
	private EmployeeService eService = new EmployeeService();
	
	@Autowired
	private ProjectEmployeeService peService = new ProjectEmployeeService();
	
	
	@GetMapping(value = "/projects")
	public String viewProjects(Model model) {
		List<Project> projects = pService.findAll();
		model.addAttribute("projects", projects);
		return "project/projects";
	}
	
	@GetMapping(value = "/project/view/{name}")
	public String viewProject(@PathVariable String name, Model model) {
		model.addAttribute("project", pService.findByName(name));
		return "project/viewP";
	}
	
	@GetMapping(value = "/project/new")
	public String newProject(@RequestParam(required = false) Integer vId, 
							 Model model) {
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
	
	@GetMapping(value = "/project/edit/{name}")
	public String editProject(
			@PathVariable String name, 
			Model model) {
		model.addAttribute("project", pService.findByName(name));
		return "project/editP";
	}
  
}
