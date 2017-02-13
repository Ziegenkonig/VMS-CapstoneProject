package payment;

public abstract class Paystub {
	int paystubID; //Primary key
	int timesheetID; //Foreign key
	int checkNo;
}
