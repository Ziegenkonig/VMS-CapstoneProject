package com.vms.services;

import com.vms.models.Vendor;

public interface VendorService {
	void create(Vendor vendor);
	
	Vendor findByName(String name);
}
