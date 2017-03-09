package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vms.models.Invoice;
import com.vms.repositories.InvoiceRepository;

public class InvoiceService {
	
	@Autowired
	private InvoiceRepository invRepo;
	
	public List<Invoice> findAll() {
		return invRepo.findAll();
	}
	
	public Invoice findById(Integer id) {
		return invRepo.findOne(id);
	}

	public Invoice create(Invoice invoice) {
		return invRepo.save(invoice);
	}
	
	public Invoice edit(Invoice invoice) {
		return invRepo.save(invoice);
	}
	
}
