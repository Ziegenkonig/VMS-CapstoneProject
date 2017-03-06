package models;

public abstract class Invoice {
	int invoiceID;
	int timesheetID; //Foreign Key
	//Date structures in mySQL
	String startPeriod;
	String endPeriod;
	String paymentDue;
	String invoiceStatus;//possible statuses: draft, pending, paid, archived (if > 1 year from start date)

}
