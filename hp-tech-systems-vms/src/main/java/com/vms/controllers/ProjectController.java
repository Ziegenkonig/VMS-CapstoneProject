package com.vms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vms.models.Project;
import com.vms.services.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService = new ProjectService();

	@GetMapping(value = "/projects")
	public String viewProjects(Model model) {
		List<Project> projects = projectService.findAll();
		model.addAttribute("projects", projects);
		return "project/projects";
	}
	
	@GetMapping(value = "/project/view/{name}")
	public String viewProject(@PathVariable String name, Model model) {
		model.addAttribute("project", projectService.findByName(name));
		return "project/viewP";
	}
	
	@GetMapping(value = "/project/new")
	public String newProject(Model model) {
		Project v = new Project();
		model.addAttribute("project", v);
		return "project/newP";
	}
	
	@GetMapping(value = "/project/edit/{name}")
	public String editProject(
			@PathVariable String name, 
			Model model) {
		model.addAttribute("project", projectService.findByName(name));
		return "project/editP";
	}
  
}
