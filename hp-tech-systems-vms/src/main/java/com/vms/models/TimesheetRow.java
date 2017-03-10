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
	private int timesheet_row_id; 
	
	//Foreign Key timesheet_id - relates to the timesheet object each row belongs to
	@ManyToOne @JoinColumn(name = "timesheet_id")
	private Timesheet timesheet;
	
	//Regular ol attributes
	@Column(length = 2) 
	private int week_no; // weekly = 1, biweekly = 1 or 2
	
	@Column(length = 2)
	private int hours_1;
	
	@Column(length = 2)
	private int hours_2;
	
	@Column(length = 2)
	private int hours_3;
	
	@Column(length = 2)
	private int hours_4;
	
	@Column(length = 2)
	private int hours_5;
	
	@Column(length = 2)
	private int hours_6;
	
	@Column(length = 2)
	private int hours_7;
	
	//methods
	public TimesheetRow() {
		this.hours_1 = 0;
		this.hours_2 = 0;
		this.hours_3 = 0;
		this.hours_4 = 0;
		this.hours_5 = 0;
		this.hours_6 = 0;
		this.hours_7 = 0;
	}
	
	public TimesheetRow(Timesheet t, int week_no) {
		this();
		this.timesheet = t;
		this.week_no = week_no;
	}
	
	public int calculateTotalHours() {
		return hours_1 + hours_2 + hours_3 + hours_4 + hours_5 + hours_6 + hours_7;
	}
	
}	
