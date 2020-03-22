package com.company.roomservice;
/**
 * 
 */

/**
 * @author damien
 *
 */

import java.io.*;

enum StockStatus { IN_STOCK, OUT_OF_STOCK };

class RoomServiceItem implements Serializable, Comparable<RoomServiceItem> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5042892457783999289L;
	
	private String name;
	private String description;
	private double price;
	private StockStatus status;
	
	RoomServiceItem(String name, String description, double price) {
		setName(name);
		setDescription(description);
		setPrice(price);
		this.status = StockStatus.IN_STOCK;
	}
	
	
	/**
	 * @return the name
	 */
	String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	void setName(String name) {
		// remove excess whitespace and capitalize words
		String temp = "";
		name.trim();
		for (String str : name.split(" ")) {
			if (str.length()==0) continue;
			temp += str.substring(0,1).toUpperCase().concat((str.substring(1))).concat(" ");
		}
		temp.trim();
		this.name = temp;
	}
	
	/**
	 * @return the description
	 */
	String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	void setDescription(String description) {
		// remove excess whitespace
		String temp = "";
		description.trim();
		for (String str : description.split(" ")) {
			if (str.length()==1 || str.length()==0) continue;
			temp += str.concat(" ");
		}
		temp.trim();
		this.description = temp;
	}
	
	/**
	 * @return the price
	 */
	double getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return the status
	 */
	StockStatus getStatus() {
		return status;
	}
	
	void setStatus(StockStatus s) {
		this.status = s;
	}
	
	@Override
	public int compareTo(RoomServiceItem o) {
		// TODO Auto-generated method stub
		
		return this.getName().compareTo(o.getName());
	}
	
}
