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
	
	//Calling .save on an object that does not have a predefined ID
	//simply creates a new entry in the table
	//Calling .save on an object that has a predefined ID updates the info
	//for the given object instead of creating a new one
	@Override
	public void create(Vendor vendor) {
		vendorRepo.save(vendor);
	}
	
	@Override
    public Vendor findByName(String name) {
        return vendorRepo.findByName(name);
	}
}
