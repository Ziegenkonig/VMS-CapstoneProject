package com.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vms.models.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer>{

}
