package com.vms.utilities;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.vms.models.Vendor;
import com.vms.services.VendorService;

@Component
public class VendorFormatter implements Formatter<Vendor> {

	@Autowired
	private VendorService vService;

	@Override
	public Vendor parse(String text, Locale locale) throws ParseException {
		return vService.findOne(Integer.valueOf(text));
	}

	@Override
	public String print(Vendor v, Locale locale) {
		return String.valueOf(v.getVendorId());
	}
	
}