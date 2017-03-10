package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vms.models.ProjectEmployee;
import com.vms.models.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer>{
	List<Timesheet> findByProjemp(ProjectEmployee projemp);
}
