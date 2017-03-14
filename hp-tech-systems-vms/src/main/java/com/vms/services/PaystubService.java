package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.models.Paystub;
import com.vms.repositories.PaystubRepository;

@Service
public class PaystubService {
	
	@Autowired
	private PaystubRepository paystubRepo;
	
	//return all paystubs for an employee starting with most recent
	public List<Paystub> findPaystubByEmployee(Employee e) {
		return paystubRepo.findByEmpIdOrderByPeriodStartDesc(e.getEmpId());
	}
	
	//find previous paystub
	public Paystub findPreviousPaystubForYtd(Employee e) {
		return paystubRepo.findByEmpIdOrderByPeriodStartDesc(e.getEmpId()).get(0);
	}
	
	//basic repo methods
	public List<Paystub> findAll() {
		return paystubRepo.findAll();
	}
	
	public Paystub findById(Integer id) {
		return paystubRepo.findOne(id);
	}

	public Paystub create(Paystub paystub) {
		return paystubRepo.save(paystub);
	}
	
	public Paystub edit(Paystub paystub) {
		return paystubRepo.save(paystub);
	}
}
