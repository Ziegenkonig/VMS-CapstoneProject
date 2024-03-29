package com.vms.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vms.models.Employee;
import com.vms.models.Timesheet;
import com.vms.models.TimesheetStatus;
import com.vms.services.ProjectEmployeeService;
import com.vms.services.TimesheetService;

@Component
public class ScheduledTasks {

	@Autowired
	ProjectEmployeeService peService;
	@Autowired
	TimesheetService tService;
	@Autowired
	MailService mService;

	private LocalDate nextPeriod = LocalDate.of(2017, 1, 1).with(WeekFields.of(Locale.US).dayOfWeek(), 1);

	//for testing
	//@Scheduled(fixedRate = 5000) //10 seconds
	public void printDate() {
		System.out.println("Current date is "
				+ LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy[ HH:mm a]")));
	}

	//
	//@Scheduled(cron = "10 * * * * *")
	//@Scheduled(fixedRate = 20000)
	public void generateTimesheets() {
		//get all the employees working on projects
		if(nextPeriod.getYear() != 2018) {
			List<Employee> emps = peService.findEmployeesForTimesheets();
			//System.out.println("Employees found: " + emps.size());

			//get the start of the current period
			//LocalDate periodStart = LocalDate.now().with(WeekFields.of(Locale.US).dayOfWeek(), 1);
			Timesheet t;
			List<Timesheet> timesheets = new ArrayList<Timesheet>();
			//create timesheets for all employees in emps
			for(Employee e : emps) {
				if(!e.getHireDate().isAfter(nextPeriod)) {
					t = new Timesheet(e,nextPeriod);
					if(!t.getProjTimesheets().isEmpty()) {
						timesheets.add(t);
					}
				}
			}
			//System.out.println(timesheets.size());
			tService.createAll(timesheets);
			//System.out.println("Timesheets created");
			//set next period for testing loop
			nextPeriod = nextPeriod.plusWeeks(1).with(WeekFields.of(Locale.US).dayOfWeek(), 1);
		}
	}

	@Scheduled(cron = "0 0 10 * * SUN")
	public void generateWeeklyTimesheets() {
		List<Employee> emps = peService.findEmployeesForTimesheets();

		//get the start of the current period
		LocalDate periodStart = LocalDate.now().with(WeekFields.of(Locale.US).dayOfWeek(), 1);
		Timesheet t;
		List<Timesheet> timesheets = new ArrayList<Timesheet>();
		for(Employee e : emps) {
			if(!e.getHireDate().isAfter(periodStart)) {
				t = new Timesheet(e,periodStart);
				if(!t.getProjTimesheets().isEmpty()) {
					timesheets.add(t);
				}
			}
		}
		for(Timesheet ts : timesheets) {
			tService.create(ts);
			mService.sendEmail(ts, "timesheetAvailable");
		}
		tService.createAll(timesheets);		
	}
	
	@Scheduled(cron = "0 0 12 * * THU")
	public void generateTimesheetReminders() {
		//should get a thursday's date based on cron
		LocalDateTime now = LocalDateTime.now();//.with(WeekFields.of(Locale.US).dayOfWeek(), 1);
		List<Timesheet> timesheets = tService.findByStatus(TimesheetStatus.NOT_SUBMITTED);
		for(Timesheet t : timesheets) {
			//thursday at 12 pm + 2 days = saturday at 12 pm so that should be after the friday due date
			if(now.plusDays(2).isAfter(t.getDueDate())) {
				mService.sendEmail(t, "timesheetAlmostDue");
			}
		}
		
	}

}
