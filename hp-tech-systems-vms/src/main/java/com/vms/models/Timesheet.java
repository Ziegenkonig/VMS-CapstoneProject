package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //standard getters/setters
@NoArgsConstructor
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
	
	@Type(type = "org.hibernate.type.LocalDateTimeType")
    @Column
    private LocalDateTime dueDate;
	
	//might change to enum weekly/biweekly
	//private int period;
	
	@Enumerated(EnumType.STRING)
	private TimesheetStatus status; 
	
	//private String imageUrl;
	//private int noHours;
	
	@OneToMany(mappedBy="timesheet", cascade = CascadeType.ALL)
	private List<ProjectTimesheet> projTimesheets;
	//fks
	
	/*
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="TIMESHEET_INVOICE", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Invoice> invoices; 
    */
	
	@OneToMany(mappedBy="timesheet", cascade = CascadeType.ALL)
	@OrderBy("createdDate DESC")
	//@JoinTable(name="TIMESHEET_PAYSTUB", joinColumns = @JoinColumn(name = "timesheet_id"), inverseJoinColumns = @JoinColumn(name = "paystub_id"))
    private List<Paystub> paystubs; 
	
	//Methods
	
	//Constructor
	public Timesheet(Employee e, LocalDate periodStart) {
		this.employee = e;
		this.weekStarting = periodStart;
		if(e.getPayPeriod() == 2) {
			this.dueDate = weekStarting.plusWeeks(1).with(WeekFields.of(Locale.US).dayOfWeek(), 6).atTime(10, 0);//.atZone(ZoneId.of("America/Chicago"));
		} else {
			this.dueDate = weekStarting.with(WeekFields.of(Locale.US).dayOfWeek(), 6).atTime(10, 0);//.atZone(ZoneId.of("America/Chicago"));
		}
		this.projTimesheets = new ArrayList<ProjectTimesheet>();
		List<ProjectEmployee> projEmps = e.getProjemps();
		if(projEmps.isEmpty()) {
			System.out.println("Empty projemps");
		}
		for(ProjectEmployee pe:projEmps) {
			if(pe.getDateEnded() == null || pe.getDateEnded().isAfter(weekStarting)) {
				projTimesheets.add(new ProjectTimesheet(pe, this));
			}
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
			BigDecimal numHours = new BigDecimal(noHours);			
			BigDecimal prt = pt.getProjemp().getPayRate();
			earned = earned.add(prt.multiply(numHours));
		}
		return earned;
	}
	
	//toString
	public String toString() {
		return ("Employee: " + employee + 
				"Period Starting: " + weekStarting);
	}
	
}