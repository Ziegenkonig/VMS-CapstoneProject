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
@Table(name = "projectsEmployees")
public class ProjectEmployee {
	
	//This isn't in the schema, but I had to define this new ID for ProjectEmployee to
	//simplify the relationships
	@Id @GeneratedValue()
	private int project_employee_id;
	
	//Foreign Keys
	//Note the way you have to reference the Entity you are pulling the FK from
	@ManyToOne @JoinColumn(name = "project_id")
	private Project project_id;
    @ManyToOne @JoinColumn(name = "emp_id")
	private Employee employee_id;
	
	//Regular ol Attributes
	@Column()
	private double pay_rate;
	@Column()
	private Date date_started;
	@Column()
	private Date date_ended;
	
}
