package com.vms.services;

import com.vms.models.Vendor;

//This interface is implemented by VendorServiceImpl
public interface VendorService {
	
	void create(Vendor vendor); //creates a new vendor in table
	void edit(Vendor vendor); //replaces 
	
	Vendor findByName(String name);
}
