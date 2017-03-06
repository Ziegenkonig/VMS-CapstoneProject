package com.vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vms.services.VendorServiceImpl;

@Controller
public class VendorController {
	@Autowired
	private VendorServiceImpl vendorService = new VendorServiceImpl();
	
}
