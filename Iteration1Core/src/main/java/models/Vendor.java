package com.vms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data //This annotation automatically generates getters/setters for this entity
@Entity //Tells Hibernate that this class is to be treated as an entity
@Table(name = "vendors") //Specifies what the table name is
public class Vendor {

	//Primary Key
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vendor_id;
	
	//Regular ol Attributes
	@Column(length = 32)
	private String name;
	
	@Column(length = 50)
	private String primary_email;
	
	@Column(length = 64)
	private String contact_name;
	
	@Column(length = 50)
	private String address;
	
	@Column(length = 16)
	private String city;
	
	@Column(length = 2)
	private String state; //Should be two capital letters
	
	@Column(length = 10)
	private String phone; //not including non-numerical characters
	
	@PrePersist //called before actual insertion into database
	protected void onCreate() {
		
	}
	
}