package com.vms.controllers;

import java.time.LocalDate;

//This class exists because I was unable to find a way to pass only a string from a controller into a form
//on an html page; I believe this is because only objects (not datatypes) can be edited from a form in the html.
//This stemmed from me not being able to access a LocalDate value in the html, so I decided it would be easier to
//use a string and parse it back to LocalDate form whenever we were done manipulating it

public class StringHolder {
	public LocalDate localdate;
	public String string;
	public String[] stringArray;
	public StringHolder(){this.string = ""; this.stringArray = new String[2]; this.localdate = LocalDate.now();} 
	public String getString() {return this.string;}
	public void setString(String string) {this.string = string;}
	public LocalDate getLocalDate() {return this.localdate;}
	public void setLocalDate(LocalDate localdate) {this.localdate = localdate;}
}
