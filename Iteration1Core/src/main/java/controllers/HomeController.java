package com.vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vms.models.Vendor;
import com.vms.services.VendorServiceImpl;

@Controller
public class HomeController {
	//Just throwing this in for testing
	@Autowired
	private VendorServiceImpl vendorService = new VendorServiceImpl();

	
	@RequestMapping("/")
    public String index() {
		
		//Just throwing this in for testing
		Vendor test = new Vendor();
		test.setAddress("address");
		test.setCity("city");
		test.setContact_name("contact_name");
		test.setName("name");
		test.setPhone("phone");
		test.setPrimary_email("email");
		test.setState("tn");
		vendorService.create(test);
		
        return "index";
    }
}
