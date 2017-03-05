package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class TimesheetRow {

	//Attributes//

	//Primary Key
	@Id @GeneratedValue()
	private int timesheet_row_id;

	//Foreign Key timesheet_id - relates to the timesheet object each row belongs to
	@ManyToOne @JoinColumn(name = "timesheet_id")
	private Timesheet timesheet_id;

	//Regular ol attributes
	private int week_no;

	private int hours_1;
	private int hours_2;
	private int hours_3;
	private int hours_4;
	private int hours_5;
	private int hours_6;
	private int hours_7;


	/*
	 * Getters and Setters
	 */

	//timesheet_row_id
	public int getTimesheet_row_id() {
		return timesheet_row_id;
	}
	public void setTimesheet_row_id(int timesheet_row_id) {
		this.timesheet_row_id = timesheet_row_id;
	}
	//timesheet_id
	public Timesheet getTimesheet_id() {
		return timesheet_id;
	}
	public void setTimesheet_id(Timesheet timesheet_id) {
		this.timesheet_id = timesheet_id;
	}
	//week_no
	public int getWeek_no() {
		return week_no;
	}
	public void setWeek_no(int week_no) {
		this.week_no = week_no;
	}
	//hours_1
	public int getHours_1() {
		return hours_1;
	}
	public void setHours_1(int hours_1) {
		this.hours_1 = hours_1;
	}
	//hours_2
	public int getHours_2() {
		return hours_2;
	}
	public void setHours_2(int hours_2) {
		this.hours_2 = hours_2;
	}
	//hours_3
	public int getHours_3() {
		return hours_3;
	}
	public void setHours_3(int hours_3) {
		this.hours_3 = hours_3;
	}
	//hours_4
	public int getHours_4() {
		return hours_4;
	}
	public void setHours_4(int hours_4) {
		this.hours_4 = hours_4;
	}
	//hours_5
	public int getHours_5() {
		return hours_5;
	}
	public void setHours_5(int hours_5) {
		this.hours_5 = hours_5;
	}
	//hours_6
	public int getHours_6() {
		return hours_6;
	}
	public void setHours_6(int hours_6) {
		this.hours_6 = hours_6;
	}
	//hours_7
	public int getHours_7() {
		return hours_7;
	}
	public void setHours_7(int hours_7) {
		this.hours_7 = hours_7;
	}

	//ToString, for testing
		public String toString()
		{
			return ("TimesheetRow => id::" + timesheet_row_id +
					", timesheet_id::" + timesheet_id +
					", week_no::" + week_no +
					", hours_1::" + hours_1 +
					", hours_2::" + hours_2 +
					", hours_3::" + hours_3 +
					", hours_4::" + hours_4 +
					", hours_5::" + hours_5 +
					", hours_6::" + hours_6 +
					", hours_7::" + hours_7);
		}
}	
