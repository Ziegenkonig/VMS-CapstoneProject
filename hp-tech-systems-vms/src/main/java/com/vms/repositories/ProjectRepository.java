package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vms.models.Project;
import com.vms.models.Vendor;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

	@Query
	public List<Project> findByVendor(Vendor v);
}
