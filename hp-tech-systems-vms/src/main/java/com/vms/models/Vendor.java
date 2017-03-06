package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Vendor {
	
	//Attributes//
	
	//Primary Key
	@Id @GeneratedValue()
	private int vendor_id;
	
	//Regular ol Attributes
	private String name;
	private String primary_email;
	private String contact_name;
	private String address;
	private String city;
	private String state;
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
		return ("Vendor => id::" + vendor_id +
				", name::" + name +
				", primary_email::" + primary_email +
				", contact_name::" + contact_name +
				", address::" + address +
				", city::" + city +
				", state::" + state +
				", phone::" + phone);	
	}
	
}