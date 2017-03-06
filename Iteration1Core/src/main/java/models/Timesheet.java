package models;

public abstract class Timesheet {
	int timesheetID;
	String timesheetImageURL;
	String timesheetStatus; //possible statuses: not submitted, pending, verified, archived archived (if > 1 year from start date)


}
