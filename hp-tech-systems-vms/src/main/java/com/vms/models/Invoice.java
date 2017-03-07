package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data; 

/* can I get the following with some get methods?
a single vendor object (timesheet.proj-emp.project.vendor)
the names of the employees working on the project (timesheet.proj-emp.employee)
the pay rate for the project (timesheet.proj-emp.project)
*/

@Data //standard getters/setters
@Entity
public class Invoice {

	@Id @GeneratedValue
	private int invoice_id;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Timestamp created_date;
	
//fields to store info from other tables
	//from project
	private BigDecimal rate; //timesheet.projemp.project.pay_rate
	
	private double total_hours; //sum(timesheet.no_hours) over timesheet list
	private BigDecimal total_amt; //total_hours * rate
	
	//pay period start and end (14/7 day interval)
	private Date period_start;
	private Date period_end;
	
	// one month from period_end
	private Date payment_due;
	
	//from vendor.contact_name
	private String recruiter;
	
	//from vendor
	private String name;
	private String address;
	private String city;
	private String state;
	private String phone;
	
	//employer info saved in global variables somewhere
	
	@ManyToMany(mappedBy = "invoices")//(cascade = CascadeType.ALL)
    private List<Timesheet> timesheets; 
	
	@PrePersist
	protected void onCreate() {
		created_date = new Timestamp(Calendar.getInstance().getTime().getTime());
	}
}
