package com.vms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data //This annotation automatically generated getters/setters for this entity
@Entity
@Table(name = "timesheetRows")
public class TimesheetRow {
	
	//Attributes//
	
	//Primary Key
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int timesheetRowId; 
	
	//Foreign Key timesheet_id - relates to the timesheet object each row belongs to
	@ManyToOne @JoinColumn(name = "timesheet_id")
	private Timesheet timesheet;
	
	//Regular ol attributes
	@Column(length = 2) 
	private int weekNo; // weekly = 1, biweekly = 1 or 2
	
	@Column(length = 2)
	private int hours1;
	
	@Column(length = 2)
	private int hours2;
	
	@Column(length = 2)
	private int hours3;
	
	@Column(length = 2)
	private int hours4;
	
	@Column(length = 2)
	private int hours5;
	
	@Column(length = 2)
	private int hours6;
	
	@Column(length = 2)
	private int hours7;
	
	//methods
	public TimesheetRow() {
		this.hours1 = 0;
		this.hours2 = 0;
		this.hours3 = 0;
		this.hours4 = 0;
		this.hours5 = 0;
		this.hours6 = 0;
		this.hours7 = 0;
	}
	
	public TimesheetRow(Timesheet t, int week_no) {
		this();
		this.timesheet = t;
		this.weekNo = week_no;
	}
	
	public int calculateTotalHours() {
		return hours1 + hours2 + hours3 + hours4 + hours5 + hours6 + hours7;
	}

}	
