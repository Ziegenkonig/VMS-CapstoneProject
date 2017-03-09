package com.vms.models;

import java.sql.Date;
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
@Table(name="timesheets")
public class Timesheet {
	
	@Id @GeneratedValue
	private int timesheet_id;
	
	private Date week_starting;
	
	//might change to enum weekly/biweekly
	private int period;
	
	@Enumerated(EnumType.STRING)
	private TimesheetStatus status; 
	
	private String image_url;
	private int no_hours;
	
	public Timesheet(ProjectEmployee proj_emp) {
		TimesheetRow week1 = new TimesheetRow();
		week1.setTimesheet(this);
		weeks.add(week1);
		if(proj_emp.getEmployee().getPay_period() == 2) {
			TimesheetRow week2 = new TimesheetRow();
			week2.setTimesheet(this);
			weeks.add(week2);
		}
	}
	
	//fks
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="TIMESHEET_INVOICE", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Invoice> invoices; 
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="TIMESHEET_PAYSTUB", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "paystub_id"))
    private List<Invoice> paystubs; 
	
	@ManyToOne
	@JoinColumn(name = "project_employee_id")
	private ProjectEmployee projemp;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="timesheet", cascade = CascadeType.ALL)
	private List<TimesheetRow> weeks;
		
}