package com.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vms.models.Vendor;
import com.vms.repositories.VendorRepository;

//This interface is implemented by VendorServiceImpl
@Service
public class VendorService {
	
	//pulling in repository
	@Autowired
	VendorRepository vendorRepo;
	
	//General methods
	
	//creates a new object in table
	public void create(Vendor vendor) {
		vendorRepo.save(vendor);
	}
	
	//update a new object in table
	public void update(Vendor vendor) {
		vendorRepo.save(vendor);
	}
	
	//Search methods
	
	//returns one object with specified id
	public Vendor findOne(Integer id) {
		return vendorRepo.findOne(id);
	}
	
	//returns all objects
	public List<Vendor> findAll() {
		return vendorRepo.findAll();
	}
	
	public Vendor findByName(String name) {
		return vendorRepo.findByName(name);
	}
	
	
}
