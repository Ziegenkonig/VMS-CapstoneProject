package com.vms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data //This annotation automatically generates getters/setters for this entity
@Entity //Tells Hibernate that this class is to be treated as an entity
@Table(name = "vendors") //Specifies what the table name is
public class Vendor {

	//Primary Key
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vendorId;
	
	//Regular ol Attributes
	@Column(length = 120, nullable = false)
	private String name;
	
	@Column(length = 50, nullable = false)
	private String primaryEmail;
	
	@Column(length = 64, nullable = false)
	private String contactName;
	
	@Column(length = 50, nullable = false)
	private String address;
	
	@Column(length = 16, nullable = false)
	private String city;
	
	@Column(length = 2, nullable = false)
	private String state; //Should be two capital letters
	
	@Column(length = 20, nullable = false)
	private String phone; //not including non-numerical characters
	
	public String toString() {
		return name;
	}
	
}