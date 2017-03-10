package com.vms.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
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
	private int emp_id;
	
	//Regular attributes.  Does not yet include length, hashing, or some specialties(i.e. tinyint)
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private int permission_level;
	private String address;
	private String city;
	private String state;
	private Date hire_date;
	private boolean active;
	
	//The parameter mappedBy is necessary for OneToMany relationships
	//It references the foreign key name inside of the associated entity
	@OneToMany(mappedBy="employee")
	private List<ProjectEmployee> projemps;
}