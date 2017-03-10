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
	private int invoiceId;
	// reference
	private int projectId;
	private int vendorId;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "invoices", cascade = CascadeType.ALL)
    private List<ProjectTimesheet> projTimesheets; 
	
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdDate;
	
//fields to store info from other tables
	//from project
    @Column(nullable = false)
	private BigDecimal rate; //timesheet.projemp.project.pay_rate
	
    @Column(nullable = false)
	private double totalHours; //sum(timesheet.no_hours) over timesheet list
    @Column(nullable = false)
	private BigDecimal totalAmt; //total_hours * rate
	
	//pay period start and end (14/7 day interval)
	@Column(nullable = false)
	private LocalDate periodStart;
	@Column(nullable = false)
	private LocalDate periodEnd;
	
	// one month from period_end
	@Column(nullable = false)
	private LocalDate paymentDue;
	
	//from vendor.contact_name
	@Column(length = 64)
	private String recruiter;
	
	//from vendor
	@Column(length = 64, nullable = false)
	private String name;
	@Column(length = 120, nullable = false)
	private String address;
	@Column(length = 64, nullable = false)
	private String city;
	@Column(length = 64, nullable = false)
	private String state;
	@Column(length = 64, nullable = false)
	private String phone;
	
	//employer info saved in global variables somewhere
	
	//constructor
	public Invoice(List<ProjectTimesheet> projTimesheets) { 
		//Setting timesheets
		this.projTimesheets = projTimesheets;
		ProjectTimesheet pt = projTimesheets.get(0); //grabbing the first timesheet in the list
		
		//Setting period_start/period_end based on info from timesheet
		this.periodStart = pt.getWeekStarting();
		ProjectEmployee proj_emp = pt.getProjemp();
		if (proj_emp.getEmployee().getPayPeriod() == 2)
			this.periodEnd = this.periodStart.plusDays(14);
		else
			this.periodEnd = this.periodStart.plusDays(7);
		
		//Setting payment_due
		this.paymentDue = this.paymentDue.plusMonths(1);
		
		//Grabbing the project/vendor out of the timesheet object and using it to set all kinds of info
		Project proj = proj_emp.getProject();
		this.projectId = proj.getProjectId();
		Vendor vendor = proj.getVendor();
		this.vendorId = vendor.getVendorId();
		this.name = vendor.getName();
		this.address = vendor.getAddress();
		this.city = vendor.getCity();
		this.state = vendor.getState();
		this.phone = vendor.getPhone();
		this.recruiter = vendor.getContactName();
		
		//Setting rate
		this.rate = proj_emp.getProject().getBillingRate();
		
		//Setting total_hours
		this.totalHours = 0;
		for(ProjectTimesheet i : projTimesheets)
			this.totalHours += i.calcTotalHoursOfPT();
		
		//Setting total_amount
		this.totalAmt = this.rate.multiply(BigDecimal.valueOf(totalHours));
	}
	
	//Called before .save
	@PrePersist
	protected void onCreate() {
		createdDate = LocalDate.now();
	}
	
	//toString
	public String toString() {
		return ("Vendor: " + name + 
				" Dates: " + periodStart + " - " + periodEnd);
	}
	
}
