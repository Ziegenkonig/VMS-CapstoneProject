package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vms.models.Paystub;
import com.vms.repositories.PaystubRepository;

public class PaystubService {
	
	@Autowired
	private PaystubRepository paystubRepo;
	
	//repo methods
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
