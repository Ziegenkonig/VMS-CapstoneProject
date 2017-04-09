package com.vms.utilities;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.vms.models.Employee;
import com.vms.services.EmployeeService;

@Component
public class EmployeeFormatter implements Formatter<Employee> {

	@Autowired
	private EmployeeService eService;

	@Override
	public Employee parse(String text, Locale locale) throws ParseException {
		return eService.findOne(Integer.valueOf(text));
	}

	@Override
	public String print(Employee e, Locale locale) {
		return String.valueOf(e.getEmpId());
	}
	
}