package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data; 

@Data //standard getters/setters
@Entity
@Table(name="invoices")
public class Invoice {

	@Id @GeneratedValue
	private int invoice_id;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
	//columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
	@Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private LocalDate created_date;
	
//fields to store info from other tables
	//from project
	private BigDecimal rate; //timesheet.projemp.project.pay_rate
	
	private double total_hours; //sum(timesheet.no_hours) over timesheet list
	private BigDecimal total_amt; //total_hours * rate
	
	//pay period start and end (14/7 day interval)
	private LocalDate period_start;
	private LocalDate period_end;
	
	// one month from period_end
	private LocalDate payment_due;
	
	//from vendor.contact_name
	private String recruiter;
	
	//from vendor
	private String name;
	private String address;
	private String city;
	private String state;
	private String phone;
	
	//employer info saved in global variables somewhere
	
	//constructor
	public Invoice(List<Timesheet> timesheets) { //the input here is the list of timesheets from query that it should be generated from
		this.timesheets = timesheets;
	}
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "invoices", cascade = CascadeType.ALL)
    private List<Timesheet> timesheets; 
	
	@PrePersist
	protected void onCreate() {
		created_date = LocalDate.now();
	}
}
