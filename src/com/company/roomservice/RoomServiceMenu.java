package com.company.roomservice;
import java.io.Serializable;
import java.util.*;

class RoomServiceMenu implements Serializable, Iterable<RoomServiceItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2792308570466362812L;
	
	private List<RoomServiceItem> menu_items;
	
	RoomServiceMenu(){
		this.menu_items = new ArrayList<RoomServiceItem>();
	}
	
	int size() {
		return menu_items.size();
	}
	
	boolean addItem(String name, String description, double price) {
		RoomServiceItem item = new RoomServiceItem(name, description, price);
		boolean flag = menu_items.add(item);
		menu_items.sort(null);
		return flag;
	}
	
	RoomServiceItem removeItem(int index) {
		return menu_items.remove(index);
	}
	
	void updateItemName(int index, String new_name) {
		menu_items.get(index-1).setName(new_name);
	}
	
	void updateItemDescription(int index, String new_description) {
		menu_items.get(index-1).setDescription(new_description);
	}
	
	void updateItemPrice(int index, double new_price) {
		menu_items.get(index-1).setPrice(new_price);
	}
	
	void updateItemStatus(int index, StockStatus new_stock_status) {
		menu_items.get(index-1).setStatus(new_stock_status);
	}
	
	RoomServiceItem getItem(int index) {
		return menu_items.get(index);
	}
	
	@Override
	public Iterator<RoomServiceItem> iterator() {
		// TODO Auto-generated method stub
		return menu_items.iterator();
	}
}
