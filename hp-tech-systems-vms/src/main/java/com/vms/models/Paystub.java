package com.vms.models;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/* can I get the following with some get methods?
a single employee object (timesheet.proj-emp.employee)
the names of the projects they are working on (timesheet.proj-emp.project)
the pay rate for each project (timesheet.proj-emp)
*/

@Data //standard getters/setters
@Entity
@Table(name="paystubs")
public class Paystub {
	
	@Id @GeneratedValue
	private int paystub_id;
	
	//deposit number
	private int check_no;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date created_date;
	
// calculated or imported fields
	//pay period start and end (14/7 day interval)
	private Date period_start;
	private Date period_end;
	
	//advice date (period end + 10?)
	private Date pay_date;
	
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
	
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "paystubs")
    private List<Timesheet> timesheets;
	
	@PrePersist
	protected void onCreate() {
		created_date = new Date(Calendar.getInstance().getTime().getTime());
	}
	
	/*// testing Lombok - should not have any errors
	private void printSomething() {
		Invoice myinvoice = new Invoice();
		myinv.getAddress();
	}
	*/
	
}
