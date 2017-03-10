package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vms.models.Paystub;

public interface PaystubRepository extends JpaRepository<Paystub, Integer>{
	
	//find all paystubs by employee
	@Query
	public List<Paystub> findByEmpIdOrderByPeriodStartDesc(int emp_id);
	
}
