package com.vms.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vms.models.ProjectEmployee;

@Component
public class ProjectEmployeeValidator implements Validator {

	@Override
    public boolean supports(Class<?> clazz) {
      return ProjectEmployee.class.equals(clazz);
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
      if(pe.getDateStarted().isBefore(pe.getEmployee().getHireDate())) {
    	  errors.rejectValue("dateStarted", "beforeHireDate");
      }
      
      if(pe.getDateEnded() != null && !pe.getDateEnded().isAfter(pe.getDateStarted())) {
    	  errors.rejectValue("dateEnded", "beforeDateStarted");
      }
      
      if(pe.getPayRate().compareTo(pe.getProject().remainingEmpPayRate()) > 0) {
    	  errors.rejectValue("payRate", "tooLarge");
      }
    }
}
