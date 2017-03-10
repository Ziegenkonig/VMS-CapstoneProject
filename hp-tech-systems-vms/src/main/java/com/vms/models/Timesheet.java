package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "timesheets")
public class Timesheet { //new summary timesheet
	
	@Id @GeneratedValue
	private int timesheetId;
	//references
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;
	//private int empId;
	//private int projectId;
	@Column(nullable = false)
	private LocalDate weekStarting;
	
	//might change to enum weekly/biweekly
	//private int period;
	
	@Enumerated(EnumType.STRING)
	private TimesheetStatus status; 
	
	//private String imageUrl;
	//private int noHours;
	
	@OneToMany(mappedBy="timesheet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ProjectTimesheet> projTimesheets;
	//fks
	
	/*
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="TIMESHEET_INVOICE", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Invoice> invoices; 
    */
	
	@OneToMany(mappedBy="timesheet", cascade = CascadeType.ALL)
	//@JoinTable(name="TIMESHEET_PAYSTUB", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "paystub_id"))
    private List<Paystub> paystubs; 
	
	//Methods
	
	//Constructor
	public Timesheet(Employee e, LocalDate periodStart) {
		this.employee = e;
		this.weekStarting = periodStart;
		this.projTimesheets = new ArrayList<ProjectTimesheet>();
		List<ProjectEmployee> projEmps = e.getProjemps();
		for(ProjectEmployee pe:projEmps) {
			projTimesheets.add(new ProjectTimesheet(pe, weekStarting));
		}
		this.status = com.vms.models.TimesheetStatus.NOT_SUBMITTED;
	}
	
	//calculates total number of hours
	public int calcTotalHoursOfT() {
		int noHours = 0;
		for(ProjectTimesheet pt:projTimesheets) {
			noHours += pt.calcTotalHoursOfPT();
		}
		return noHours;
	}
	
	public BigDecimal calcEarned() {
		BigDecimal earned = BigDecimal.ZERO;
		double noHours;
		for(ProjectTimesheet pt:projTimesheets) {
			noHours = pt.calcTotalHoursOfPT();
			earned.add(pt.getProjemp().getPayRate().multiply(new BigDecimal(noHours)));
		}
		return earned;
	}
	
	//toString
	public String toString() {
		return ("Employee: " + employee + 
				"Period Starting: " + weekStarting);
	}
	
}