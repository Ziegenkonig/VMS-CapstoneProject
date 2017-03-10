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
@Table(name="paystubs")
public class Paystub {
	
	@Id @GeneratedValue
	private int paystub_id;
	
	//deposit number
	private int check_no;
	
	@Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate created_date;
	
// calculated or imported fields
	//pay period start and end (14/7 day interval)
	private LocalDate period_start;
	private LocalDate period_end;
	
	//advice date (period end + 10?)
	private LocalDate pay_date;
	
	//net pay same as amount of check 
	private BigDecimal net_pay; //total - deductions
	private BigDecimal ytd_net_pay; //ytd_gross - ytd_deductions
	
	//federal taxable wages
	private BigDecimal total; //sum(timesheet.no_hours * timesheet.projemp.pay_rate) over timesheet list
	private BigDecimal ytd_gross; //previous paystub ytd_gross + this.total
	
	//from employee
	private String first_name;
	private String last_name;
	private String address;
	private String city;
	private String state;
	
	//employer info saved in global variables somewhere
	
	// may not be implemented default as 0
	private BigDecimal ytd_deductions;
	private BigDecimal deductions;
	private BigDecimal federal_tax;
	private BigDecimal state_tax;
	private BigDecimal ss_tax;
	private BigDecimal medicare_tax;
	private BigDecimal med_insurance;
	private BigDecimal _401k;
	private BigDecimal ytd_federal_tax;
	private BigDecimal ytd_state_tax;
	private BigDecimal ytd_ss_tax;
	private BigDecimal ytd_medicare_tax;
	private BigDecimal ytd_med_insurance;
	private BigDecimal ytd_401k;
	
	//constructor
	public Paystub(List<Timesheet> timesheets, Paystub previous) { //the input here is the list of timesheets from query that it should be generated from, and the previous paystub also from query
		this.timesheets = timesheets;
		//info should be same from all timesheets
		Timesheet ts = timesheets.get(0);
		this.period_start = ts.getWeek_starting();
		Employee emp = ts.getProjemp().getEmployee();
		if(emp.getPay_period() == 2) {
			this.period_end = this.period_start.plusDays(14);
		} else { this.period_end = this.period_start.plusDays(7); }
		this.pay_date = this.period_end.plusDays(10);
		this.first_name = emp.getFirst_name();
		this.last_name = emp.getLast_name();
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
		this.federal_tax = BigDecimal.ZERO;
		this.state_tax = BigDecimal.ZERO;
		this.ss_tax = BigDecimal.ZERO;
		this.medicare_tax = BigDecimal.ZERO;
		this.med_insurance = BigDecimal.ZERO;
		this._401k = BigDecimal.ZERO;
		//totals
		//this.deductions = this.federal_tax.add(this.state_tax).add(this.ss_tax).add(this.medicare_tax).add(this.med_insurance).add(this._401k);
		this.deductions = BigDecimal.ZERO;
		this.net_pay = this.total.subtract(this.deductions);
		//ytd
		this.ytd_gross = previous.getYtd_gross().add(this.total);
		this.ytd_deductions = previous.getYtd_deductions().add(this.deductions);
		this.ytd_net_pay = previous.getYtd_net_pay().add(this.net_pay);
		this.ytd_federal_tax = previous.getFederal_tax().subtract(this.federal_tax);
		this.ytd_state_tax = previous.getState_tax().subtract(this.state_tax);
		this.ytd_ss_tax = previous.getSs_tax().subtract(this.ss_tax);
		this.ytd_medicare_tax = previous.getMedicare_tax().subtract(this.medicare_tax);
		this.ytd_med_insurance = previous.getMed_insurance().subtract(this.med_insurance);
		this.ytd_401k = previous.get_401k().subtract(this._401k);
	}
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "paystubs", cascade = CascadeType.ALL)
    private List<Timesheet> timesheets;
	
	@PrePersist
	protected void onCreate() {
		created_date = LocalDate.now();
	}
	
	/*// testing Lombok - should not have any errors
	private void printSomething() {
		Invoice myinvoice = new Invoice();
		myinv.getAddress();
	}
	*/
	
}
