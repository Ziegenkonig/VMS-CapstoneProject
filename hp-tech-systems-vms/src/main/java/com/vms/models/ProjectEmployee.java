package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
	@NotNull()
	@ManyToOne @JoinColumn(name = "projectId")
	private Project project;
	
	@NotNull(message = "{employee.null}")
    @ManyToOne @JoinColumn(name = "empId")
	private Employee employee;
	
    @OneToMany(mappedBy = "projemp")
    private List<ProjectTimesheet> projTimesheets;
    
	//Regular ol Attributes
    @NotNull(message = "{payRate.null}")
	@Column(nullable = false)
	private BigDecimal payRate;
	
    @NotNull(message = "{dateStarted.null}")
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateStarted;
	
	@Column//(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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