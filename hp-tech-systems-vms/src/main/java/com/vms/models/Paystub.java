package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	//deposit number
	private int checkNo;
	
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdDate;
	
// calculated or imported fields
	//pay period start and end (14/7 day interval)
	private LocalDate periodStart;
	private LocalDate periodEnd;
	
	//advice date (period end + 10?)
	private LocalDate payDate;
	
	//net pay same as amount of check 
	private BigDecimal netPay; //total - deductions
	private BigDecimal ytdNetPay; //ytd_gross - ytd_deductions
	
	//federal taxable wages
	private BigDecimal total; //sum(timesheet.no_hours * timesheet.projemp.pay_rate) over timesheet list
	private BigDecimal ytdGross; //previous paystub ytd_gross + this.total
	
	//from employee
	private String firstname;
	private String lastname;
	private String address;
	private String city;
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
	public Paystub(List<Timesheet> timesheets, Paystub previous) { //the input here is the list of timesheets from query that it should be generated from, and the previous paystub also from query
		this.timesheets = timesheets;
		//info should be same from all timesheets
		Timesheet ts = timesheets.get(0);
		this.periodStart = ts.getWeek_starting();
		Employee emp = ts.getProjemp().getEmployee();
		this.empId = emp.getEmp_id();
		if(emp.getPay_period() == 2) {
			this.periodEnd = this.periodStart.plusDays(14);
		} else { this.periodEnd = this.periodStart.plusDays(7); }
		this.payDate = this.periodEnd.plusDays(10);
		this.firstname = emp.getFirst_name();
		this.lastname = emp.getLast_name();
		this.address = emp.getAddress();
		this.city = emp.getCity();
		this.state = emp.getState();
		
		//math
		BigDecimal total = BigDecimal.valueOf(0);
		for(Timesheet t: timesheets) {
			t.calcNo_Hours();
			double hours = t.getNo_hours();
			total.add(new BigDecimal(hours).multiply(t.getProjemp().getPay_rate())); 
		}
		this.total = total;
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
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "paystubs", cascade = CascadeType.ALL)
    private List<Timesheet> timesheets;
	
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
