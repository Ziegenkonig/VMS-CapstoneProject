package com.vms.controllers;

import java.util.ArrayList;
import java.util.List;

public class ArrayHolder {
	
	public Object[] array;
	public List<String> list;
	
	public ArrayHolder() {
		this.array = new Object[0];
		this.list = new ArrayList<String>();
	}
	
	public void setList(List<String> newList) {
		this.list = newList;
	}
	
	public List<String> getList() {
		return this.list;
	}
	
	public void setArray(Object[] newArray) {
		this.array = newArray;
	}
	
	public Object[] getArray() {
		return this.array;
	}
	
}
