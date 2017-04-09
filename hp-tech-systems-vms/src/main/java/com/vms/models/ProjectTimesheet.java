package com.vms.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //standard getters/setters
@NoArgsConstructor
@Entity
@Table(name = "project_timesheets")
public class ProjectTimesheet {
	
	@Id @GeneratedValue
	private int projectTimesheetId;
	//references
	@Column(nullable = false)
	private int projectId;
	
	@Column(length = 164)
	private String imageUrl;
	@Column(nullable = false)
	private LocalDate weekStarting;
	
	@ManyToOne
	@JoinColumn(name = "timesheet_id")
	private Timesheet timesheet;
	
	@ManyToOne
	@JoinColumn(name = "project_employee_id")
	private ProjectEmployee projemp;
	
	@OneToMany(mappedBy="projTimesheet", cascade = CascadeType.ALL)
	private List<TimesheetRow> weeks;
	
	@ManyToMany//(cascade = CascadeType.ALL)
	@JoinTable(name="PROJECTTIMESHEET_INVOICE", joinColumns = @JoinColumn(name = "projectTimesheetId"), inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Invoice> invoices; 
	
	
	//Methods
	
	//Constructor
	public ProjectTimesheet(ProjectEmployee projEmp, Timesheet t) {
		weeks = new ArrayList<TimesheetRow>();
		this.projemp = projEmp;
		this.timesheet = t;
		this.weekStarting = t.getWeekStarting();
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
