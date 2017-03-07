package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

//ProjectEmployee is a joined table that is used to keep track of the relations between
//the Project and Employee entities, as well as recording necessary information for these
//relations.

@Data
@Entity
@Table(name = "projects")
public class Project {
	
	//This isn't in the schema, but I had to define this new ID for ProjectEmployee to
	//simplify the relationships
	@Id @GeneratedValue()
	private int project_id;
	
	//Foreign Keys
	//Note the way you have to reference the Entity you are pulling the FK from
	@ManyToOne @JoinColumn(name = "vendor_id")
	private Vendor vendor_id;
	
	//Regular attributes.  Does not yet include length some specialties(i.e. BigDecimal)
	private String name;
	private double billing_rate;
	private String client_name;
	private String client_location;
	
	/*
	 * Getters and Setters
	 */

	public int getProject_id() {
		return project_id;
	}
	
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	
	public Vendor getVendor_id() {
		return vendor_id;
	}
	
	public void setVendor_id(Vendor vendor_id) {
		this.vendor_id = vendor_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getBilling_rate() {
		return billing_rate;
	}
	
	public void setBilling_rate(double billing_rate) {
		this.billing_rate = billing_rate;
	}
	
	public String getClient_name() {
		return client_name;
	}
	
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	
	public String getClient_location() {
		return client_location;
	}
	
	public void setClient_location(String client_location) {
		this.client_location = client_location;
	}
	
	//ToString, for testing
		public String toString()
		{
			return ("Project => id::" + getProject_id() +
					", name::" + getName() +
					",vendor_id::" + getVendor_id() +
					", billing rate::" + getBilling_rate() +
					", client_name::" + getClient_name() +
					". client_location::" + getClient_location());
		}
}