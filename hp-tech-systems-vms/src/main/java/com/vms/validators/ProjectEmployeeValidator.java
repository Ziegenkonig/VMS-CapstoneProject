package com.vms.validators;


import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vms.models.ProjectEmployee;

@Service
public class ProjectEmployeeValidator implements Validator {

	@Override
    public boolean supports(Class<?> clazz) {
      return ProjectEmployee.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
      ProjectEmployee pe = (ProjectEmployee) target;
      /*
      if(user.getName() == null) {
          errors.rejectValue("name", "your_error_code");
      }
      */
      // do "complex" validation here
      if(pe.getDateStarted() != null && pe.getDateStarted().isBefore(pe.getEmployee().getHireDate())) {
    	  errors.rejectValue("dateStarted", "dateStarted.beforeHireDate");
      }
      
      if(pe.getDateEnded() != null && !pe.getDateEnded().isAfter(pe.getDateStarted())) {
    	  errors.rejectValue("dateEnded", "dateEnded.beforeDateStarted");
      }
      
      if(pe.getPayRate() != null && pe.getPayRate().compareTo(pe.getProject().remainingEmpPayRate()) > 0) {
    	  errors.rejectValue("payRate", "payRate.tooLarge");
      }
    }
}
