package com.vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.vms.models.Vendor;
import com.vms.repositories.VendorRepository;

@Service
@Primary
public class VendorServiceImpl implements VendorService{
	@Autowired
	public VendorRepository vendorRepo;
	
	@Override
	public void create(Vendor vendor) {
		vendorRepo.save(vendor);
		vendorRepo.toString();
	}
	
	@Override
	public void edit(Vendor vendor) {
		
	}
	
	@Override
    public Vendor findByName(String name) {
        return vendorRepo.findByName(name);
}
}
