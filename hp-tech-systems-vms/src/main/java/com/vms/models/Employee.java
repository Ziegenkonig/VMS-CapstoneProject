package com.vms.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getPermission_level() {
		return permission_level;
	}

	public void setPermission_level(int permission_level) {
		this.permission_level = permission_level;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	//ToString, for testing
	public String toString()
	{
		return ("Employee => id::" + getEmp_id() +
				", username::" + getUsername() +
				", password::" + getPassword() +
				", first_name::" + getFirst_name() +
				", last_name::" + getLast_name() +
				", permission_level::" + getPermission_level() +
				", address" + getAddress() +
				", city::" + getCity() +
				", state::" + getState() +
				", hire_date::" + getHire_date() +
				", active::" + isActive());	
	}
	
}
