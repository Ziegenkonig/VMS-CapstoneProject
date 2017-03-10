package com.vms.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data //standard getters/setters
@Entity
@Table(name = "timesheets")
public class Timesheet {
	
	@Id @GeneratedValue
	private int timesheet_id;
	//reference
	
	private LocalDate week_starting;
	
	//might change to enum weekly/biweekly
	private int period;
	
	@Enumerated(EnumType.STRING)
	private TimesheetStatus status; 
	
	private String image_url;
	private int no_hours;
	
	//fks
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="TIMESHEET_INVOICE", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Invoice> invoices; 
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="TIMESHEET_PAYSTUB", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "paystub_id"))
    private List<Paystub> paystubs; 
	
	@ManyToOne
	@JoinColumn(name = "project_employee_id")
	private ProjectEmployee projemp;
	

	@OneToMany(mappedBy="timesheet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<TimesheetRow> weeks;
	
	
	//Methods
	
	//Constructor
	public Timesheet(ProjectEmployee proj_emp, LocalDate period_start) {
		weeks = new ArrayList<TimesheetRow>();
		this.projemp = proj_emp;
		this.week_starting = period_start;
		this.period = proj_emp.getEmployee().getPayPeriod();
		TimesheetRow week1 = new TimesheetRow(this, 1);
		weeks.add(week1);
		if(proj_emp.getEmployee().getPayPeriod() == 2) {
			TimesheetRow week2 = new TimesheetRow(this, 2);
			weeks.add(week2);
		}
		status = com.vms.models.TimesheetStatus.NOT_SUBMITTED;
	}
	
	//calculates total number of hours
	public void calcNo_Hours() {
		no_hours = 0;
		for(TimesheetRow tr:weeks) {
			no_hours += tr.calculateTotalHours();
		}
	}
	//toString
	public String toString() {
		return ("Employee: " + projemp.getEmployee() + 
				" Project: " + projemp.getProject() +
				" Dates: " + projemp.getDate_started() + " - " + projemp.getDate_ended());
	}
	
		
}

