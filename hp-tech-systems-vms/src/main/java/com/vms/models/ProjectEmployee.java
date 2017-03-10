package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
	private int projectEmployeeId;
	
	//Foreign Keys
	//@JoinColumn is necessary for a ManyToOne relationship
	@ManyToOne @JoinColumn(name = "projectId")
	private Project project;
    @ManyToOne @JoinColumn(name = "empId")
	private Employee employee;
	
    @OneToMany(mappedBy = "projemp")
    private List<Timesheet> timesheets;
    
	//Regular ol Attributes
	private BigDecimal payRate;
	private LocalDate dateStarted;
	private LocalDate dateEnded;
	
	//Methods
	//toString
	public String toString() {
		return ("Employee: " + employee + 
				"Project: " + project);
	}
	//Default constructor
	public ProjectEmployee() {
		
	}
	//Nondefault constructor
	public ProjectEmployee(Employee employee, Project project) {
		this.employee = employee;
		this.project = project;
	}
	
}
