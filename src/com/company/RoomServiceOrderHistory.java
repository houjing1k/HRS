package com.company;
import java.io.*;
import java.util.*;

public class RoomServiceOrderHistory implements Serializable, Iterable<RoomServiceOrder>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5706864655161900176L;
	private List<RoomServiceOrder> order_history;
	private int latest_order_id;
	
	RoomServiceOrderHistory() {
		order_history = new ArrayList<RoomServiceOrder>();
		latest_order_id = 0;
	}
	
	
	boolean addOrder(RoomServiceOrder order) {
		boolean flag = order_history.add(order);
		if (flag) {
			latest_order_id += 1;
		}
		return flag;
	}
	
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
