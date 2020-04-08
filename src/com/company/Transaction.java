package com.company;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	public Transaction() {
		
	}

	public String toString() {
        return "Transaction [name=" + name + ", description=" + description + ", price=" + price +", quantity="+quantity+ ", Date=" + time +"]";
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
