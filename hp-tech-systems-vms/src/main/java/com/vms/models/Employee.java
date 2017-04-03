package com.vms.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@Column(length = 32, unique = true, nullable = false)
	private String username;
	@Column(length = 32, nullable = false)
	private String password;
	@Column(length = 64, nullable = false)
	private String email;
	@Column(length = 32, nullable = false)
	private String firstname;
	@Column(length = 32, nullable = false)
	private String lastname;
	@Column(length = 1, nullable = false)
	private int permissionLevel;
	@Column(length = 120, nullable = false)
	private String address;
	@Column(length = 64, nullable = false)
	private String city;
	@Column(length = 32, nullable = false)
	private String state;
	@Column(nullable = false)
	private LocalDate hireDate;
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
}

