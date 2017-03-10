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

import lombok.Data; 

@Data //standard getters/setters
@Entity
@Table(name="invoices")
public class Invoice {

	@Id @GeneratedValue
	private int invoice_id;
	// reference
	private int project_id;
	private int vendor_id;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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
	public Invoice(List<Timesheet> timesheets) {
		//Setting timesheets
		this.timesheets = timesheets;
		Timesheet ts = timesheets.get(0); //grabbing the first timesheet in the list
		
		//Setting period_start/period_end based on info from timesheet
		this.period_start = ts.getWeek_starting();
		ProjectEmployee proj_emp = ts.getProjemp();
		if (proj_emp.getEmployee().getPay_period() == 2)
			this.period_end = this.period_start.plusDays(14);
		else
			this.period_end = this.period_start.plusDays(7);
		
		//Setting payment_due
		this.payment_due = this.payment_due.plusMonths(1);
		
		//Grabbing the vendor out of the timesheet object and using it to set all kinds of info
		Project proj = proj_emp.getProject();
		this.project_id = proj.getProject_id();
		Vendor vendor = proj.getVendor();
		this.vendor_id = vendor.getVendor_id();
		this.name = vendor.getName();
		this.address = vendor.getAddress();
		this.city = vendor.getCity();
		this.state = vendor.getState();
		this.phone = vendor.getPhone();
		this.recruiter = vendor.getContact_name();
		
		//Setting rate
		this.rate = proj_emp.getProject().getBilling_rate();
		
		//Setting total_hours
		this.total_hours = 0;
		for(Timesheet i : timesheets)
			this.total_hours += i.getNo_hours();
		
		//Setting total_amount
		this.total_amt = this.rate.multiply(BigDecimal.valueOf(total_hours));
	}
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "invoices", cascade = CascadeType.ALL)
    private List<Timesheet> timesheets; 
	
	@PrePersist
	protected void onCreate() {
		created_date = LocalDate.now();
	}
}
