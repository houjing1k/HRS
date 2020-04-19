package com.company;
/**
 * A single room service item. Each item has 4 attributes: name, description, price, and status. 
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
	 * use serialVersionUID for interoperability
	 */
	private static final long serialVersionUID = -5042892457783999289L;
	
	private String name;
	private String description;
	private double price;
	private StockStatus status;
	
	/**
	 * Constructs a room service item with values as specified and sets its status to {@code IN_STOCK}
	 * 
	 * @param name  the name of the item
	 * @param description  the description of the item
	 * @param price  the price of the item
	 */
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
		StringBuilder temp = new StringBuilder();
		for (String word : name.trim().split(" ")) {
			if (word.equals(" ")) continue;
			temp.append(word.substring(0,1).toUpperCase() + word.substring(1) + " ");
		}
		this.name = temp.toString().trim();
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
		StringBuilder temp = new StringBuilder();
		for (String word : description.trim().split(" ")) {
			if (word.equals(" ")) continue;
			temp.append(word + " ");
		}
		this.description = temp.toString().trim();
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
	
	/**
	 * 
	 * @param s the status to set
	 */
	void setStatus(StockStatus s) {
		this.status = s;
	}
	
	@Override
	public int compareTo(RoomServiceItem o) {
		// TODO Auto-generated method stub
		
		return this.getName().compareTo(o.getName());
	}
	
}
