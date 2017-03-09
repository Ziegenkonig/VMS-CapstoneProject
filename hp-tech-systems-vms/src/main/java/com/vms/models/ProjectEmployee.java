package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "projectsEmployees")
public class ProjectEmployee {
	
	//This isn't in the schema, but I had to define this new ID for ProjectEmployee to
	//simplify the relationships
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int project_employee_id;
	
	//Foreign Keys
	//@JoinColumn is necessary for a ManyToOne relationship
	@ManyToOne @JoinColumn(name = "project_id")
	private Project project;
    @ManyToOne @JoinColumn(name = "emp_id")
	private Employee employee;
	
    @OneToMany(mappedBy = "projemp")
    private List<Timesheet> timesheets;
    
	//Regular ol Attributes
	private BigDecimal pay_rate;
	private Date date_started;
	private Date date_ended;
	
	public String toString() {
		return ("Employee: " + employee + 
				"Project: " + project);
	}
	
}
