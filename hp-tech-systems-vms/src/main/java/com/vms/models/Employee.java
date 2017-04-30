package com.vms.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	//@NotNull()
	@Size(min = 2, max = 32, message = "{username.size}")
	@Pattern(regexp = "[a-zA-Z0-9_-]*", message = "{username.pattern}")
	@Column(length = 32, unique = true, nullable = true)
	private String username;
	
	//Hashed field
	//@NotNull()
	@Size(min = 2, message = "{password.size}")
	//@Pattern(regexp = "[a-zA-Z0-9_-]*", message = "{password.pattern}")
	@Column(nullable = true)
	private String password;
	
	@Size(min = 2, message = "{password.size}")
	//@Pattern(regexp = "[a-zA-Z0-9_-$%@#/]*", message = "{password.pattern}")
	@Column(nullable = true)
	private String confirmPassword;
	
	//@NotNull()
	@Size(min = 6, message = "{email.size}")
	@Pattern(regexp = "[a-zA-Z0-9_-]*+@+[a-zA-Z0-9-]*+\\.+[A-Za-z]*{2,6}", message = "{email.pattern}")
	@Column(length = 128, unique = true, nullable = true)
	private String email;
	
	@Size(min = 6, message = "{email.size}")
	@Pattern(regexp = "[a-zA-Z0-9_-]*+@+[a-zA-Z0-9-]*+\\.+[A-Za-z]*{2,6}", message = "{email.pattern}")
	@Column(length = 128, unique = true, nullable = true)
	private String confirmEmail;
	
	//@NotNull()
	@Size(min = 2, max = 32, message = "{human.size}")
	@Pattern(regexp = "[a-zA-Z]*", message = "{human.pattern}")
	@Column(length = 32, nullable = true)
	private String firstname;
	
	//@NotNull()
	@Size(min = 2, max = 32, message = "{human.size}")
	@Pattern(regexp = "[a-zA-Z]*", message = "{human.pattern}")
	@Column(length = 32, nullable = true)
	private String lastname;
	
	//@NotNull()
	@Size(min = 5, message = "{zipcode.size}")
	@Pattern(regexp = "[0-9]*{5}", message = "{zipcode.pattern}")
	@Column(length = 32, nullable = true)
	private String zipcode;
	
	//Not included in the registration/edit forms, 
	@Column(nullable = false)
	private Permission permissionLevel;
	

	//@NotNull()
	@Size(min = 5, max = 120, message = "{address.size}")
	@Pattern(regexp = "[0-9]*+\\s+[a-zA-Z0-9\\s.]*", message = "{address.pattern}")
	@Column(length = 120, nullable = true)
	private String address;
	
	//@NotNull()
	@Size(min = 2, max = 64, message = "{city.size}")
	@Pattern(regexp = "[a-zA-Z']*", message = "{city.pattern}")
	@Column(length = 64, nullable = true)
	private String city;
	
	//Validation not needed due to being dropdown box
	@Column(length = 32, nullable = true)
	private String state;
	
	//Hiredate won't be included in a form
	@Column(nullable = false)
	private LocalDate hireDate;
	
	//These two attributes will be selected via dropdown box
	@Column(nullable = false)
	private boolean active;
	
	@Column(nullable = false)
	private PayPeriod payPeriod; //weekly, biweekly
	
	//Hashed field --  for account registration link
	@Column(nullable = true)
	private String registrationUrl;
	
	//Hashed field --  for email confirmation link
	@Column(nullable = true)
	private String confirmationUrl;
	
	//The parameter mappedBy is necessary for OneToMany relationships
	//It references the foreign key name inside of the associated entity
	@OneToMany(mappedBy="employee", fetch = FetchType.EAGER)
	private List<ProjectEmployee> projemps;
	
	@OneToMany(mappedBy="employee")
	private List<Timesheet> timesheets;
	
	public String toString() {
		return (firstname + " " + lastname);
	}
	
}

