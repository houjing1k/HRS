package com.company;

/**
 * This class is a list of {@code RoomServiceOrder} objects. It provides
 * functionality to add {@code RoomServiceOrder} objects. This object also keeps track
 * of the latest order id of {@code ROomServiceOrder} objects in the list.
 * The {@code RoomServiceOrder} objects are sorted numerically by their order id.
 * 
 */

/**
 * @author damien
 *
 */

import java.io.*;
import java.util.*;

public class RoomServiceOrderHistory implements Serializable, Iterable<RoomServiceOrder>{

	
	/**
	 * use serialVersionUID for interoperability
	 */
	private static final long serialVersionUID = 5706864655161900176L;
	private List<RoomServiceOrder> order_history;
	private int latest_order_id;
	
	/**
	 * Creates a new empty list of {@code RoomServiceOrder} objects.
	 */
	RoomServiceOrderHistory() {
		order_history = new ArrayList<RoomServiceOrder>();
		latest_order_id = 0;
	}
	
	/**
	 * Appends the specified {@code RoomServiceOrder} to the end of this list.
	 * @param order  {@code RoomServiceOrder{ to be appended to this list
	 * @return  true (as specified by Collection.add(E))
	 */
	boolean addOrder(RoomServiceOrder order) {
		boolean flag = order_history.add(order);
		if (flag) {
			latest_order_id += 1;
		}
		return flag;
	}
	
	/**
	 * Returns the number of orders in this list.
	 * @return the number of orders in this list
	 */
	int getNoOfOrders() {
		return order_history.size();
	}
	
	/**
	 * @return the order_history
	 */
	List<RoomServiceOrder> getOrderHistory() {
		return order_history;
	}

	/**
	 * @return the latest_order_id
	 */
	int getLatestOrderID() {
		return latest_order_id;
	}

	@Override
	public Iterator<RoomServiceOrder> iterator() {
		// TODO Auto-generated method stub
		return order_history.iterator();
	}

	
}
