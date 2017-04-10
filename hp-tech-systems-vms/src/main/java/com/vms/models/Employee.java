package com.vms.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.vms.utilities.mail.Mail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
	
	//Attributes//
	
	//Primary Key
	@Id @GeneratedValue()
	private int empId;
	
	//Regular attributes.  Does not yet include length, hashing, or some specialties(i.e. tinyint)
	@NotNull()
	@Size(min = 2, max = 32, message = "{username.size}")
	@Pattern(regexp = "[a-zA-Z0-9_-]*", message = "{username.pattern}")
	@Column(length = 32, unique = true, nullable = false)
	private String username;
	
	@NotNull()
	@Size(min = 3, max = 32, message = "{password.size}")
	@Pattern(regexp = "[a-zA-Z0-9_-]*", message = "{password.pattern}")
	@Column(length = 32, nullable = false)
	private String password;
	
	@NotNull()
	@Size(min = 6, max = 64, message = "{email.size}")
	@Pattern(regexp = "[a-zA-Z0-9_-]*+@+[a-zA-Z0-9-]*+\\.+[A-Za-z]*{2,6}", message = "{email.pattern}")
	@Column(length = 64, nullable = false)
	private String email;
	
	@NotNull()
	@Size(min = 2, max = 32, message = "{human.size}")
	@Pattern(regexp = "[a-zA-Z]*", message = "{human.pattern}")
	@Column(length = 32, nullable = false)
	private String firstname;
	
	@NotNull()
	@Size(min = 2, max = 32, message = "{human.size}")
	@Pattern(regexp = "[a-zA-Z]*", message = "{human.pattern}")
	@Column(length = 32, nullable = false)
	private String lastname;
	
	//Not included in the registration/edit forms, 
	@Column(length = 1, nullable = false)
	private int permissionLevel;
	

	@NotNull()
	@Size(min = 5, max = 120, message = "{address.size}")
	@Pattern(regexp = "[0-9]*+\\s+[a-zA-Z0-9\\s.]*", message = "{address.pattern}")
	@Column(length = 120, nullable = false)
	private String address;
	
	@NotNull()
	@Size(min = 2, max = 64, message = "{city.size}")
	@Pattern(regexp = "[a-zA-Z]*", message = "{city.pattern}")
	@Column(length = 64, nullable = false)
	private String city;
	
	//Validation not needed due to being dropdown box
	@Column(length = 32, nullable = false)
	private String state;
	
	//Hiredate won't be included in a form
	@Column(nullable = false)
	private LocalDate hireDate;
	
	//These two attributes will be selected via dropdown box
	@Column(nullable = false)
	private boolean active;
	@Column(length = 1, nullable = false)
	private int payPeriod; //weekly, biweekly
	
	//The parameter mappedBy is necessary for OneToMany relationships
	//It references the foreign key name inside of the associated entity
	@OneToMany(mappedBy="employee")
	private List<ProjectEmployee> projemps;
	
	@OneToMany(mappedBy="employee")
	private List<Timesheet> timesheets;
	
	public String toString() {
		return (firstname + " " + lastname);
	}
	
	//If we want to notify the employee that their paystub is complete, use this method.. Should be called once paystub has been persisted to database 
	public void notifyPaystubCompletion(){
		//this.email = the current instance of employee that has a paystub ready
		String message = "Your paystub is complete. Go here to check it: http://localhost:8080/paystub";
		String subject = "Paystub is ready";
		Mail.sendEmail(this.email, message, subject);
	}
	
	public void notifyTimesheetCompletion(){
		String message = "You just completed a timesheet. Go here to view it: http://localhost:8080/timesheet";
		String subject = "Timesheet completion";
		Mail.sendEmail(this.email, message, subject);
	}
	public void notifyTimesheetAccepted(){
		String message = "Your timesheet has been accepted. Go here to view it: http://localhost:8080/timesheet";
		String subject = "Timesheet has been accepted";
		Mail.sendEmail(this.email, message, subject);
	}
}

