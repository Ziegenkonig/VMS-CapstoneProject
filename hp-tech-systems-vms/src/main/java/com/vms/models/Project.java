package com.vms.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	//Regular attributes.  Does not yet include length some specialties(i.e. BigDecimal)
	private String name;
	private double billing_rate;
	private String client_name;
	private String client_location;
	
	
	//Foreign Keys
	//Note the way you have to reference the Entity you are pulling the FK from
	@ManyToOne @JoinColumn(name = "vendor_id")
	private Vendor vendor_id;
	
	@OneToMany(mappedBy = "project")
	private List<ProjectEmployee> projemps;
	
	public String toString() {
		return name;
	}
}

