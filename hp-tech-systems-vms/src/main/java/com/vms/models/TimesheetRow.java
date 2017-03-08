package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;

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
	private Timesheet timesheet_id;
	
	//Regular ol attributes
	@Column()
	private int week_no;
	
	@Column()
	private int hours_1;
	@Column()
	private int hours_2;
	@Column()
	private int hours_3;
	@Column()
	private int hours_4;
	@Column()
	private int hours_5;
	@Column()
	private int hours_6;
	@Column()
	private int hours_7;
	
}	
