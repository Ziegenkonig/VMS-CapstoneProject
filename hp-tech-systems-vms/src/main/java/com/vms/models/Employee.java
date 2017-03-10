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

import lombok.Data;

@Data
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
	@OneToMany(fetch=FetchType.EAGER, mappedBy="employee")
	private List<ProjectEmployee> projemps;
	
	@OneToMany(mappedBy="employee")
	private List<Timesheet> timesheets;
	
	public String toString() {
		return (firstname + " " + lastname);
	}
}

