package com.vms.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

//ProjectEmployee is a joined table that is used to keep track of the relations between
//the Project and Employee entities, as well as recording necessary information for these
//relations.

@Data
@Entity
@Table(name = "projects-employees")
public class ProjectEmployee {
	
	//This isn't in the schema, but I had to define this new ID for ProjectEmployee to
	//simplify the relationships
	@Id @GeneratedValue()
	private int project_employee_id;
	
	//Foreign Keys
	//Note the way you have to reference the Entity you are pulling the FK from
	@ManyToOne @JoinColumn(name = "project_id")
	private Project project_id;
	@ManyToOne @JoinColumn(name = "employee_id")
	private Employee employee_id;
	
	//Regular ol Attributes
	@Column()
	private double pay_rate;
	@Column()
	private Date date_started;
	@Column()
	private Date date_ended;
	
	/*
	 * Getters and Setters
	 */

	//ProjectEmployeeID
	public int getProject_employee_id() {
		return project_employee_id;
	}
	public void setProject_employee_id(int project_employee_id) {
		this.project_employee_id = project_employee_id;
	}
	//ProjectID
	public Project getProject_id() {
		return project_id;
	}
	public void setProject_id(Project project_id) {
		this.project_id = project_id;
	}
	//EmployeeID
	public Employee getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Employee employee_id) {
		this.employee_id = employee_id;
	}
	//PayRate
	public double getPay_rate() {
		return pay_rate;
	}
	public void setPay_rate(double pay_rate) {
		this.pay_rate = pay_rate;
	}
	//DateStarted
	public Date getDate_started() {
		return date_started;
	}
	public void setDate_started(Date date_started) {
		this.date_started = date_started;
	}
	//DateEnded
	public Date getDate_ended() {
		return date_ended;
	}
	public void setDate_ended(Date date_ended) {
		this.date_ended = date_ended;
	}
	
}
