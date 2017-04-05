package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.models.PaystubStatus;
import com.vms.models.Timesheet;
import com.vms.models.TimesheetStatus;

public interface PaystubRepository extends JpaRepository<Paystub, Integer>{
	
	//Finds all paystubs for a given employee with the given status
	public List<Paystub> findByEmpIdAndStatusOrderByPeriodStartDesc(Integer id, PaystubStatus status);
	
	//find all paystubs by employee
	@Query
	public List<Paystub> findByEmpIdOrderByPeriodStartDesc(int emp_id);
	
}
