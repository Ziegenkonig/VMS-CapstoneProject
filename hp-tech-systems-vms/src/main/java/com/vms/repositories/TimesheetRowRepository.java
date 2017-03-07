package com.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vms.models.TimesheetRow;

@Repository
public interface TimesheetRowRepository extends JpaRepository<TimesheetRow, Long> {
	
}
