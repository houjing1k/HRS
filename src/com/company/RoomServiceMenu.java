package com.company;

/**
 * This class is a list of {@code RoomServiceItem} objects. It provides
 * functionality to add and remove {@code RoomServiceItem} objects, and 
 * make changes to individual {@code RoomServiceItem} object attributes.
 * The {@code RoomServiceItem} objects are sorted alphabetically by their names.
 * 
 */

/**
 * @author damien
 *
 */

import java.io.Serializable;
import java.util.*;

class RoomServiceMenu implements Serializable, Iterable<RoomServiceItem>{

	/**
	 * use serialVersionUID for interoperability
	 */
	private static final long serialVersionUID = -2792308570466362812L;
	
	private List<RoomServiceItem> menu_items;
	
	/**
	 * Creates a new empty list of {@code RoomServiceItem} objects.
	 */
	RoomServiceMenu(){
		this.menu_items = new ArrayList<RoomServiceItem>();
	}
	
	/**
	 * Returns the number of {@code RoomServiceItem} objects in this menu.
	 * @return number of {@code RoomServiceItem} objects in this menu
	 */
	int size() {
		return menu_items.size();
	}
	
	/**
	 * Creates a new {@code RoomServiceItem} object, appends it to the current list, and 
	 * sorts the list alphabetically by name.
	 * 
	 * @param name  name of {@code RoomServiceItem} to add
	 * @param description  description of {@code RoomServiceItem} to add
	 * @param price  price of {@code RoomServiceItem} to add
	 * @return  true (as specified by Collection.add(E))
	 */
	boolean addItem(String name, String description, double price) {
		if (price < 0) return false;
		RoomServiceItem item = new RoomServiceItem(name, description, price);
		boolean flag = menu_items.add(item);
		menu_items.sort(null);
		return flag;
	}
	
	/**
	 * Removes the {@code RoomServiceItem} at the specified position in the list of items.
	 * 
	 * @param index  the index of {@code RoomServiceItem} to be removed
	 * @return the {@code RoomServiceItem} previously at the specified position
	 */
	RoomServiceItem removeItem(int index) {
		return menu_items.remove(index);
	}
	
	/**
	 * Changes the name of the {@code RoomServiceItem} at the specified position in the list of items.
	 * @param index  the index of {@code RoomServiceItem} to be updated
	 * @param new_name  the new name to set
	 * @return  true (operation is always successful)
	 */
	boolean updateItemName(int index, String new_name) {
		menu_items.get(index).setName(new_name);
		return true;
	}
	
	/**
	 * Changes the description of the {@code RoomServiceItem} at the specified position in the list of items.
	 * @param index  the index of {@code RoomServiceItem} to be updated
	 * @param new_description  the new description to set
	 * @return  true (operation is always successful)
	 */
	boolean updateItemDescription(int index, String new_description) {
		menu_items.get(index).setDescription(new_description);
		return true;
	}
	
	/**
	 * Changes the price of the {@code RoomServiceItem} at the specified position in the list of items.
	 * @param index  the index of {@code RoomServiceItem} to be updated
	 * @param new_price  the new price to set
	 * @return  true (operation is always successful)
	 */
	boolean updateItemPrice(int index, double new_price) {
		if (new_price < 0) return false;
		menu_items.get(index).setPrice(new_price);
		return true;
	}
	
	/**
	 * Changes the status of the {@code RoomServiceItem} at the specified position in the list of items.
	 * @param index  the index of {@code RoomServiceItem} to be updated
	 * @param new_stock_status  the new status to set
	 * @return  true (operation is always successful)
	 */
	boolean updateItemStatus(int index, StockStatus new_stock_status) {
		menu_items.get(index).setStatus(new_stock_status);
		return true;
	}
	
	/**
	 * Returns the {@code RoomServiceItem} at the specified position in this menu.
	 * @param index  index of the {@code RoomServiceItem} to return
	 * @return  the {@code RoomServiceItem} at the specified position in this menu
	 */
	RoomServiceItem getItem(int index) {
		return menu_items.get(index);
	}
	
	@Override
	public Iterator<RoomServiceItem> iterator() {
		// TODO Auto-generated method stub
		return menu_items.iterator();
	}
}
