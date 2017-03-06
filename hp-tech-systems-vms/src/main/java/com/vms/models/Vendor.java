package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vendors")
public class Vendor {
	
	//Attributes//
	
	//Primary Key
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vendor_id;
	
	//Regular ol Attributes
	@Column()
	private String name;
	@Column()
	private String primary_email;
	@Column()
	private String contact_name;
	@Column()
	private String address;
	@Column()
	private String city;
	@Column()
	private String state;
	@Column()
	private String phone;
	
	/*
	 * Getters and Setters
	 */
	
	//vendor_id
	public int getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}
	//name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//primary_email
	public String getPrimary_email() {
		return primary_email;
	}
	public void setPrimary_email(String primary_email) {
		this.primary_email = primary_email;
	}
	//contact_email
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	//address
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	//city
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	//state
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	//phone
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	//ToString, for testing
	public String toString()
	{
		return ("Vendor => id::" + this.getVendor_id() +
				", name::" + this.getName() +
				", primary_email::" + this.getPrimary_email() +
				", contact_name::" + this.getContact_name() +
				", address::" + this.getAddress() +
				", city::" + this.getCity() +
				", state::" + this.getState() +
				", phone::" + this.getPhone());	
	}
	
}