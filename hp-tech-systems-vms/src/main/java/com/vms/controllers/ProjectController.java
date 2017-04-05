package com.vms.controllers;

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
import com.vms.models.Project;
import com.vms.models.ProjectEmployee;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.ProjectEmployeeService;
import com.vms.services.ProjectService;
import com.vms.services.VendorService;

@Controller
@SessionAttributes({"project", "pe"})
public class ProjectController {

	@Autowired
	private ProjectService pService = new ProjectService();
	
	@Autowired
	private VendorService vService = new VendorService();
	
	@Autowired
	private EmployeeService eService = new EmployeeService();
	
	@Autowired
	private ProjectEmployeeService peService = new ProjectEmployeeService();
	
	@GetMapping(value = "/projects/{mode}")
	public String viewProjects(@PathVariable String mode, 
							   @RequestParam(required = false) Integer vId,
							   Model model) {
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
								@ModelAttribute("project") Project p, SessionStatus status) {
		if(p.getVendor() == null) {
			p.setVendor(v);
		}
		pService.create(p);
		status.setComplete();
		return "redirect:/projects/all";
	}
	
	@GetMapping(value = "/project/edit/{name}")
	public String editProject(@PathVariable String name, Model model) {
		List<Vendor> vendors = vService.findAll();
		model.addAttribute("vendors", vendors);
		model.addAttribute("project", pService.findByName(name));
		return "project/editP";
	}
	
	@PostMapping("/project/edit")
	public String saveProject(@ModelAttribute("project") Project p, SessionStatus status) {
		pService.edit(p);
		status.setComplete();
		return "redirect:/projects/all";
	}
	
	@GetMapping("/project/addEmployee")
	public String addEmployee(@RequestParam Integer pId, Model model) {
		Project p = pService.findById(pId);
		List<Employee> emps = eService.findAll();
		model.addAttribute("emps", emps);
		ProjectEmployee pe = new ProjectEmployee();
		pe.setProject(p);
		model.addAttribute("pe", pe);
		return "project/addEmployeeForm";
	}
	
	@PostMapping("/project/addEmployee")
	public String saveAddedEmployee(@ModelAttribute("pe") ProjectEmployee pe, SessionStatus status) {
		peService.create(pe);
		status.setComplete();
		return "redirect:/project/view/" + pe.getProject().getName();
	}
  
}
