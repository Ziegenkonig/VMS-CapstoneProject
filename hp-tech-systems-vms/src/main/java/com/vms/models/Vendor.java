package com.vms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data //This annotation automatically generated getters/setters for this entity
@Entity //Tells Hibernate that this class is to be treated as an entity
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