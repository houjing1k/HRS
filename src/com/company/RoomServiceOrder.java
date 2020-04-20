package com.company;
/**
 * A single room service order that contains a list of {@code RoomServiceItem} objects.
 * This class provides functionality to add and remove {@code RoomServiceItem}s.
 * 
 */

/**
 * @author damien
 *
 */

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

enum OrderStatus { UNCONFIRMED, CONFIRMED, PREPARING, DELIVERED };

class RoomServiceOrder implements Serializable, Comparable<RoomServiceOrder>, Iterable<RoomServiceItem> {
	
	/**
	 * use serialVersionUID for interoperability
	 */
	private static final long serialVersionUID = 5513143310548014542L;

	
	private List<RoomServiceItem> order_items = new ArrayList<>();
	private int order_id;
	private LocalDateTime order_date_time;
	private String remarks;
	private BigDecimal bill;
	
	private String room_number;
	private OrderStatus status;
	private boolean paid;
	
	/**
	 * Creates an unconfirmed room service order with no initial {@code RoomServiceItem} objects.
	 * 
	 * @param order_id  the order id of this order
	 * @param order_date_time  the date and time this order was created
	 * @param room_number  the room number that this order belongs to
	 */
	RoomServiceOrder(int order_id, LocalDateTime order_date_time, String room_number) {
		this.order_id = order_id;
		this.order_date_time = order_date_time;
		this.room_number = room_number;
		this.status = OrderStatus.UNCONFIRMED;
		this.remarks = "";
		this.bill = BigDecimal.ZERO;
		this.paid = false;
	}
	
	/**
	 * Appends the specified quantity of the {@code RoomServiceItem} item to the order.
	 * 
	 * @param item  the room service item to be added
	 * @param quantity  the quantity of specified item to be added
	 * @return  true (as specified by Collection.add(E))
	 */
	boolean addItem(RoomServiceItem item, int quantity) {
		
		for (int i=0; i < quantity; i++) {
			if (item.getStatus() == StockStatus.OUT_OF_STOCK) return false;
			order_items.add(item);
			bill = bill.add( BigDecimal.valueOf(item.getPrice()) );
		}
		order_items.sort(null);
		return true;
	}
	
	/**
	 * Removes the {@code RoomServiceItem} with the specified name in the list of items.
	 * 
	 * @param name  the name of {@code RoomServiceItem} to be removed
	 * @return {@code true} if this list contains the item with the specified name
	 *
	 */
	boolean removeItem(String name) {
		
		// pre-process string input
		StringBuilder temp = new StringBuilder();
		name.trim();
		for (String word : name.split(" ")) {
			if (word.equals(" ")) continue;
			temp.append(word.substring(0,1).toUpperCase() + word.substring(1) + " ");
		}
		name = temp.toString().trim();
		
		int i = 0;
		for (RoomServiceItem item : order_items) {
			if (item.getName().equals(name)) {
				order_items.remove(i);
				bill = bill.subtract( BigDecimal.valueOf(item.getPrice()) );
				return true;
			}
			i += 1;
		}
		return false;
	}
	
	/**
	 * @return the list of {@code RoomServiceItem} objects
	 */
	List<RoomServiceItem> getOrderList(){
		return order_items;
	}
	
	/**
	 * @return the number of items in this order
	 */
	int size() {
		return order_items.size();
	}

	/**
	 * @return the order_id
	 */
	int getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id the order_id to set
	 */
	void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return the order_date_time
	 */
	LocalDateTime getOrder_date_time() {
		return order_date_time;
	}

	/**
	 * @param order_date_time the order_date_time to set
	 */
	void setOrder_date_time(LocalDateTime order_date_time) {
		this.order_date_time = order_date_time;
	}

	/**
	 * @return the remarks
	 */
	String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the bill
	 */
	BigDecimal getBill() {
		return bill;
	}

	/**
	 * @param bill the bill to set
	 */
	void setBill(BigDecimal bill) {
		this.bill = bill;
	}

	/**
	 * @return the room_number
	 */
	String getRoom_number() {
		return room_number;
	}

	/**
	 * @param room_number the room_number to set
	 */
	void setRoom_number(String room_number) {
		this.room_number = room_number;
	}

	/**
	 * @return the status
	 */
	OrderStatus getStatus() {
		return status;
	}
	
	/**
	 * @param the status to set
	 */
	void setStatus(OrderStatus s) {
		this.status = s;
	}

	/**
	 * @return whether the order has been paid
	 */
	boolean isPaid() {
		return paid;
	}

	/**
	 * @param paid set whether order has been paid
	 */
	void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Override
	public int compareTo(RoomServiceOrder o) {
		// TODO Auto-generated method stub
		
		return this.getOrder_id() - o.getOrder_id();
	}

	@Override
	public Iterator<RoomServiceItem> iterator() {
		// TODO Auto-generated method stub
		return order_items.iterator();
	}

}

