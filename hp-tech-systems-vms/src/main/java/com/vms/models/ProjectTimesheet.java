package com.vms.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data //standard getters/setters
@Entity
@Table(name = "project_timesheets")
public class ProjectTimesheet {
	
	@Id @GeneratedValue
	private int projectTimesheetId;
	//references
	private int projectId;
	
	private String imageUrl;
	
	private LocalDate weekStarting;
	
	@ManyToOne
	@JoinColumn(name = "timesheet_id")
	private Timesheet timesheet;
	
	@ManyToOne
	@JoinColumn(name = "project_employee_id")
	private ProjectEmployee projemp;
	
	@OneToMany(mappedBy="projTimesheet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<TimesheetRow> weeks;
	
	
	//Methods
	
	//Constructor
	public ProjectTimesheet(ProjectEmployee projEmp, LocalDate weekStarting) {
		weeks = new ArrayList<TimesheetRow>();
		this.projemp = projEmp;
		this.weekStarting = weekStarting;
		this.projectId = projEmp.getProject().getProjectId();
		//this.period = projEmp.getEmployee().getPayPeriod();
		TimesheetRow week1 = new TimesheetRow(this, 1);
		weeks.add(week1);
		if(projEmp.getEmployee().getPayPeriod() == 2) {
			TimesheetRow week2 = new TimesheetRow(this, 2);
			weeks.add(week2);
		}
	}
	
	//calculates total number of hours
	public int calcTotalHoursOfPT() {
		int noHours = 0;
		for(TimesheetRow tr:weeks) {
			noHours += tr.calculateTotalHoursOfTR();
		}
		return noHours;
	}
	
	//toString
	public String toString() {
		return ("Employee: " + projemp.getEmployee() + 
				" Project: " + projemp.getProject() +
				" Starting: " + timesheet.getWeekStarting());
	}
	

}
