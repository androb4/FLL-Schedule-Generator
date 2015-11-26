package com.androb4.fll.schedulegenerator;

public class Table {
	
	private int tableNumber;
	private String tableName;
	
	public Table(int tableNumber, String name) {
		this.tableNumber = tableNumber;
		this.tableName = name;
	}
	
	public int getNumber() {
		return this.tableNumber;
	}
	
	public String getName() {
		return this.tableName;
	}
}
