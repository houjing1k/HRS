package com.company;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private double price;
	private int quantity;
	private LocalDateTime time;
	
	//create a new transaction
	public Transaction(String name, String description, double price,int quantity, LocalDateTime time){
		this.name=name;
		this.description=description;
		this.price=price;
		this.quantity=quantity;
		this.time =time;
	}
	
	public Transaction(Transaction trans){
		this.name=trans.getName();
		this.description=trans.getDescription();
		this.price=trans.getPrice();
		this.quantity=trans.getQuantity();
		this.time =trans.getTime();
				
	}
	
	public Transaction() {
		
	}

	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDateTime = time.format(formatter);
        return   String.format("%-10s %-15s %-8s %-5s %-25s", name, description, quantity ,price,formatDateTime);
	}
	
	
	//return name of the transaction
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	//return description of the transaction
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}
	
	//return price of the transaction
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price=price;
	}
	
	//return quantity of the transaction
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	
	//return date of the transaction
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time=time;
	}
}
