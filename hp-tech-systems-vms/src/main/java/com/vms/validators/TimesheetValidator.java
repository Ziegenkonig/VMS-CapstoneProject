package com.vms.validators;


import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vms.models.Timesheet;

@Service
public class TimesheetValidator implements Validator {

	@Override
    public boolean supports(Class<?> clazz) {
      return Timesheet.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	Timesheet t = (Timesheet) target;
      /*
      if(user.getName() == null) {
          errors.rejectValue("name", "your_error_code");
      }
*/
      // do "complex" validation here
      if(t.getWeekStarting() != null && t.getWeekStarting().isBefore(t.getEmployee().getHireDate())) {
    	  errors.rejectValue("weekStarting", "weekStarting.beforeHireDate");
      }
      
      if(t.getProjTimesheets().isEmpty()) {
    	  errors.reject("projTimesheets.isEmpty");
      }
    }
}
