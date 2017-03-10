package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data //standard getters/setters
@Entity
@Table(name = "paystubs")
public class Paystub {
	
	@Id @GeneratedValue
	private int paystubId;
	//reference
	private int empId;
	//private int projectId;
	
	//deposit number
	private int checkNo;
	
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdDate;
	
// calculated or imported fields
	//pay period start and end (14/7 day interval)
    @Column(nullable = false)
	private LocalDate periodStart;
    @Column(nullable = false)
	private LocalDate periodEnd;
	
	//advice date (period end + 10?)
	@Column(nullable = false)
	private LocalDate payDate;
	
	//net pay same as amount of check 
	@Column(nullable = false)
	private BigDecimal netPay; //total - deductions
	@Column(nullable = false)
	private BigDecimal ytdNetPay; //ytd_gross - ytd_deductions
	
	//federal taxable wages
	@Column(nullable = false)
	private BigDecimal total; //sum(timesheet.no_hours * timesheet.projemp.pay_rate) over timesheet list
	@Column(nullable = false)
	private BigDecimal ytdGross; //previous paystub ytd_gross + this.total
	
	//from employee
	@Column(length = 32, nullable = false)
	private String firstname;
	@Column(length = 32, nullable = false)
	private String lastname;
	@Column(length = 120, nullable = false)
	private String address;
	@Column(length = 64, nullable = false)
	private String city;
	@Column(length = 32, nullable = false)
	private String state;
	
	//employer info saved in global variables somewhere
	
	// may not be implemented default as 0
	private BigDecimal ytdDeductions;
	private BigDecimal deductions;
	private BigDecimal federalTax;
	private BigDecimal stateTax;
	private BigDecimal ssTax;
	private BigDecimal medicareTax;
	private BigDecimal medInsurance;
	private BigDecimal a401k;
	private BigDecimal ytdFederalTax;
	private BigDecimal ytdStateTax;
	private BigDecimal ytdSsTax;
	private BigDecimal ytdMedicaretax;
	private BigDecimal ytdMedInsurance;
	private BigDecimal ytd401k;
	
	//constructor
	public Paystub(Timesheet ts, Paystub previous) { //the input here is the list of timesheets from query that it should be generated from, and the previous paystub also from query
		this.timesheet = ts;
		//info should be same from all timesheets
		//Timesheet ts = timesheets.get(0);
		this.periodStart = ts.getWeekStarting();
		Employee emp = ts.getEmployee();
		this.empId = emp.getEmpId();
		if(emp.getPayPeriod() == 2) {
			this.periodEnd = this.periodStart.plusDays(14);
		} else { this.periodEnd = this.periodStart.plusDays(7); }
		this.payDate = this.periodEnd.plusDays(10);
		this.firstname = emp.getFirstname();
		this.lastname = emp.getLastname();
		this.address = emp.getAddress();
		this.city = emp.getCity();
		this.state = emp.getState();
		//this.projectId = ts.getProjemp().getProject().getProjectId();
		
		//math
		this.total = ts.calcEarned();
		//deduction math - 0 for now
		this.federalTax = BigDecimal.ZERO;
		this.stateTax = BigDecimal.ZERO;
		this.ssTax = BigDecimal.ZERO;
		this.medicareTax = BigDecimal.ZERO;
		this.medInsurance = BigDecimal.ZERO;
		this.a401k = BigDecimal.ZERO;
		//totals
		//this.deductions = this.federal_tax.add(this.state_tax).add(this.ss_tax).add(this.medicare_tax).add(this.med_insurance).add(this._401k);
		this.deductions = BigDecimal.ZERO;
		this.netPay = this.total.subtract(this.deductions);
		//ytd
		this.ytdGross = previous.getYtdGross().add(this.total);
		this.ytdDeductions = previous.getYtdDeductions().add(this.deductions);
		this.ytdNetPay = previous.getYtdNetPay().add(this.netPay);
		this.ytdFederalTax = previous.getFederalTax().subtract(this.federalTax);
		this.ytdStateTax = previous.getStateTax().subtract(this.stateTax);
		this.ytdSsTax = previous.getSsTax().subtract(this.ssTax);
		this.ytdMedicaretax = previous.getMedicareTax().subtract(this.medicareTax);
		this.ytdMedInsurance = previous.getMedInsurance().subtract(this.medInsurance);
		this.ytd401k = previous.getA401k().subtract(this.a401k);
	}
	
	@ManyToOne//(cascade = CascadeType.ALL)
	@JoinColumn(name = "timesheet_id")
    private Timesheet timesheet;
	
	@PrePersist
	protected void onCreate() {
		createdDate = LocalDate.now();
	}
	
	/*// testing Lombok - should not have any errors
	private void printSomething() {
		Invoice myinvoice = new Invoice();
		myinv.getAddress();
	}
	*/
	
	public String toString() {
		return ("Employee: " + firstname + " " + lastname +
				"Dates: " + periodStart + " - " + periodEnd);
	}
	
}
