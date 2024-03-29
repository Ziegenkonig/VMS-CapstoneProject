package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Invoice;
import com.vms.models.Project;
import com.vms.models.Vendor;
import com.vms.repositories.InvoiceRepository;

@Service
public class InvoiceService {
	
	@Autowired
	private InvoiceRepository invRepo;
	
	//return all invoices for an project starting with most recent
	public List<Invoice> findInvoiceByProject(Project p) {
		return invRepo.findByProjectIdOrderByPeriodStartDesc(p.getProjectId());
	}
	
	//return all invoices for an vendor starting with most recent
	public List<Invoice> findInvoiceByVendor(Vendor v) {
		return invRepo.findByVendorIdOrderByPeriodStartDesc(v.getVendorId());
	}
	
	//basic repo methods
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
