package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Project;
import com.vms.models.Vendor;
import com.vms.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projRepo;
	
	//
	public List<Project> findByVendor(Vendor v) {
		return projRepo.findByVendor(v);
	}
	
	//basic repo methods
	public List<Project> findAll() {
		return projRepo.findAll();
	}
	
	public Project findById(Integer id) {
		return projRepo.findOne(id);
	}

	public Project create(Project p) {
		return projRepo.save(p);
	}
	
	public Project edit(Project p) {
		return projRepo.save(p);
	}

}
