package com.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vms.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

}
