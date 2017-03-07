package com.vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.vms.models.ProjectEmployee;
import com.vms.repositories.ProjectEmployeeRepository;

@Service
@Primary
public class ProjectEmployeeServiceImpl implements ProjectEmployeeService{

	@Autowired
	ProjectEmployeeRepository projectEmployeeRepo;
	
	@Override
	public void create(ProjectEmployee projectemployee) {
		projectEmployeeRepo.save(projectemployee);
	}
	
}
