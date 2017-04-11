package com.vms.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	private int projectId;

	//Regular attributes.  Does not yet include length some specialties(i.e. BigDecimal)
	@NotNull(message = "{dollar.size}")
	@Digits(integer=6, fraction=2)
	@DecimalMin("0.00") 
	@Column(nullable = false)
	private BigDecimal billingRate;
	
	@NotNull()
	@Size(min = 2, max = 64, message = "{irregular.size}")
	@Pattern(regexp = "[a-zA-Z\\s-_.]*", message = "{irregular.pattern}")
	@Column(length = 64, nullable = false)
	private String name;
	
	@NotNull()
	@Size(min = 2, max = 64, message = "{irregular.size}")
	@Pattern(regexp = "[a-zA-Z\\s-_.]*", message = "{irregular.pattern}")
	@Column(length = 64, nullable = false)
	private String clientName;
	
	@NotNull()
	@Size(min = 2, max = 64, message = "{address.size}")
	@Pattern(regexp = "[0-9]*+\\s+[a-zA-Z0-9\\s.]*", message = "{address.pattern}")
	@Column(length = 64, nullable = false)
	private String clientLocation;
	
	
	//Foreign Keys
	//Note the way you have to reference the Entity you are pulling the FK from
	@ManyToOne @JoinColumn(name = "vendor_id")
	private Vendor vendor;
	
	@OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ProjectEmployee> projemps;
	
	
	//Methods
	//toString
	public String toString() {
		return name;
	}
	
	//Constructor
	public Project() {}
	
	public Project(Vendor vendor) {
		//this.projemps = new ArrayList<ProjectEmployee>();
		this.vendor = vendor;
	}
	
}

