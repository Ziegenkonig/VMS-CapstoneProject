package com.vms.forms;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.vms.models.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewTimesheetForm {
	
	private Employee e;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

}
