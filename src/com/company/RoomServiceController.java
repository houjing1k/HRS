package com.company;
/**
 * 
 */


/**
 * @author damien
 *
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.company.Controller;
import com.company.RoomEntity.RoomStatus;

public class RoomServiceController extends Controller {
	
	private final String[] main_menu = {
			"Room Service",
			"View Menu",
			"Edit Menu",
			"Create New Order",
			"View/Edit Orders",
			"Search For Order",
			"Back to Main Menu"
		};

	private final String[] edit_menu_menu = {
			"Edit Menu",
			"Add item",
			"Remove item",
			"Update item's name",
			"Update item's description",
			"Update item's price",
			"Update item's status",
			"Back to Room Service Menu"
	};
	
	private final String[] create_order_menu = {
			"Creating New Order",
			"Add item to order",
			"Remove item from order",
			"Add remarks to order",
			"View current order",
			"Place order",
			"Back to Room Service Menu"
	};
	
	private final String[] view_edit_orders_menu = {
			"View/Edit Orders",
			"View all preparing orders",
			"View all confirmed orders",
			"Change order status",
			"Back to Room Service Menu"
	};
	
	private final String[] search_order_menu = {
			"Search For Order",
			"Search order by ID",
			"Search order by Room Number",
			"Back to Room Service Menu"
	};
	
	private final String filedir_menu = "./data/room_service_menu.ser";
	private final String filedir_order_history = "./data/order_history.ser";
	
	private RoomServiceMenu menu;
	private RoomServiceOrderHistory order_history;
	private int next_order_id;
	private RoomServiceBoundary boundary;
	
	/**
	 * Loads up the {@code RoomServiceMenu} and {@code RoomServiceOrderHistory} from file, or creates 
	 * a new {@code RoomServiceMenu} or {@code RoomServiceOrderHistory} if file is not found.
	 * Initializes {@code RoomServiceBoundary} to handle input output to console.
	 */
	public RoomServiceController() {
		boundary = new RoomServiceBoundary();
		
		this.menu = fromFile(filedir_menu);
		if (this.menu == null) this.menu = new RoomServiceMenu();
		
		this.order_history = fromFile(filedir_order_history);
		if (this.order_history == null) this.order_history = new RoomServiceOrderHistory();
		this.next_order_id = order_history.getLatestOrderID() + 1;
	}
	
	/**
	 * To save {@code RoomServiceMenu} and {@code RoomServiceOrderHistory} to file.
	 */
	public void close() {
		toFile(menu, filedir_menu);
		toFile(order_history, filedir_order_history);
	}
	
	/**
	 * Main method to be called to use this class.
	 */
	public void processMain() {
		int choice;
		
		do {
			choice = boundary.userInputFromMenu(main_menu);
		
			switch (choice) {
			
			case 1: // display menu
				boundary.printFoodMenu(menu);
				break;
			case 2: // edit menu
				editMenu();
				break;
			case 3:
				createOrderMenu();
				break;
			case 4:
				viewEditOrdersMenu();
				break;
			case 5:
				searchOrderMenu();
				break;
			case 0:
				break;
			}
		} while (choice != 0);
		close();
	}
	
	/**
	 * Sub method called by {@code processMain()} to make changes to menu.
	 */
	public void editMenu() {
		int choice;
		
		do {
			choice = boundary.userInputFromMenu(edit_menu_menu);
			
			switch (choice) {
			
			case 1: // add item
			{
				Object[] inputs = boundary.userInputForAddItem();
				String name = (String) inputs[0];
				String description = (String) inputs[1];
				double price = (double) inputs[2];
				
				boolean flag = menu.addItem(name, description, price);
				boundary.printSuccess(flag, choice);
				
				break;
			}
			
			case 2: // remove item
			{
				boundary.printFoodMenu(menu);
				Object[] inputs = boundary.userInputForRemoveItem(menu.size());
				int index_to_remove = ((int) inputs[0]) - 1;
				
				boolean flag = true;
				if (menu.removeItem(index_to_remove) == null) flag = false;
				
				boundary.printSuccess(flag, choice);
				break;
			}
			
			case 3: // update name
			{
				boundary.printFoodMenu(menu);
				Object[] inputs = boundary.userInputForUpdateItem(menu.size(), 0);
				int index_to_update = ((int) inputs[0]) - 1;
				String new_name = (String) inputs[1];
				
				boolean flag = menu.updateItemName(index_to_update, new_name);
				
				boundary.printSuccess(flag, choice);
				break;
			}
			
			case 4: // update description
			{
				boundary.printFoodMenu(menu);
				Object[] inputs = boundary.userInputForUpdateItem(menu.size(), 1);
				int index_to_update = ((int) inputs[0]) - 1;
				String new_description = (String) inputs[1];
				
				boolean flag = menu.updateItemDescription(index_to_update, new_description);
				
				boundary.printSuccess(flag, choice);
				break;
			}
				
			case 5: // update price
			{
				boundary.printFoodMenu(menu);
				Object[] inputs = boundary.userInputForUpdateItem(menu.size(), 2);
				int index_to_update = ((int) inputs[0]) - 1;
				double new_price = (double) inputs[1];
				
				boolean flag = menu.updateItemPrice(index_to_update, new_price);
				
				boundary.printSuccess(flag, choice);
				break;
			}
			
			case 6: // update status
			{
				boundary.printFoodMenu(menu);
				Object[] inputs = boundary.userInputForUpdateItem(menu.size(), 3);
				int index_to_update = ((int) inputs[0]) - 1;
				StockStatus new_stock_status = (StockStatus) inputs[1];
				
				boolean flag = menu.updateItemStatus(index_to_update, new_stock_status);
				
				boundary.printSuccess(flag, choice);
				break;		
			}
			case 0:
				break;
			default:
				break;
				
			}
		} while (choice != 0);
		
	}
	
	/**
	 * Sub method called by {@code processMain()} to create a new order. 
	 */
	public void createOrderMenu() {
		
		 // need to account for invalid room numbers
		String room_number;
		
		do {
			System.out.println("Please enter the room number (eg. 0208): ");
			room_number = boundary.getStringFromUser();
			
			String general_pattern = "^[0123456789]{4}$";
			String valid_room_pattern = "^[0][234567][0][12345678]$";
			
			
			if (!Pattern.matches(general_pattern, room_number)) {
				System.out.println("Please enter in a valid format.");
				continue;
			}
			else if (!Pattern.matches(valid_room_pattern, room_number)) {
				System.out.println("No such room exists.");
				continue;
			}
			else
				break;

		} while (true);
		
		System.out.println();
		
		ArrayList<RoomEntity> occupied_rooms = RoomController.getInstance().listRooms(RoomStatus.OCCUPIED);
		
		boolean found = false;
		for (RoomEntity room : occupied_rooms) {
			if (room.getRoomId().equals(room_number)) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			System.out.println("The room currently is not checked in.");
			return;
		}
		
		RoomServiceOrder temp_order = new RoomServiceOrder(this.next_order_id, LocalDateTime.now(), room_number);
		
		int choice;
		
		do {
			choice = boundary.userInputFromMenu(create_order_menu);
			
			switch (choice) {
			case 1:  // add item to order
				// needs improvement!! 
			{
				boundary.printFoodMenu(menu);
				
				int index, quantity;
				do {
					System.out.println("Enter item number (0 to finish adding): ");
					
					index = boundary.getIntFromUser(0, menu.size());
					
					if (index == 0) {
						System.out.println();
						break;
					}
					
					System.out.println("Enter quantity of " + menu.getItem(index-1).getName() + ": ");
					
					quantity = boundary.getIntFromUser(1, 50); // arbitrary max limit of 50
					
					boolean flag = temp_order.addItem(menu.getItem(index-1), quantity);
					
					boundary.printSuccess(flag, choice);
					
				} while (index != 0);
				break;
			}
			case 2: // remove item from order
			{
				boundary.printOrder(temp_order);

				System.out.println("Enter item to be removed: ");
				
				int index_to_remove = boundary.getIntFromUser(1, temp_order.size()) - 1;
				
				boolean flag = true;
				if (temp_order.removeItem(index_to_remove) == null) flag = false;
				
				boundary.printSuccess(flag, choice);
				break;
			}
			case 3: // add remarks
			{
				System.out.println("Enter remarks: ");
				String remarks = boundary.getStringFromUser();
				
				temp_order.setRemarks(remarks);
				
				System.out.println("Order remarks successfully added.");
				System.out.println();
				
				break;
			}
			case 4: // view list of current order
			{
				boundary.printMainTitle("Current Order");
				boundary.printOrder(temp_order);
				System.out.println();
				break;
			}
			case 5:
			{
				temp_order.setStatus(OrderStatus.CONFIRMED);
				order_history.addOrder(temp_order);
				new PaymentController().addRoomServiceToPaymentBill(room_number, temp_order);
				
				temp_order = null;
				next_order_id += 1;
				
				System.out.println("Order has been confirmed.");
				System.out.println();
				choice=0;
				break;
			}
			case 0:
				break;
			default:
			{
				System.out.println("Please choose one of the options.");
				break;
			}
			}
		} while (choice != 0);
	}
	
	/**
	 * Sub method called by {@code processMain()} to view or edit existing orders.
	 */
	public void viewEditOrdersMenu() {
		
		int choice;
		
		do  {
			choice = boundary.userInputFromMenu(view_edit_orders_menu);
			
			switch (choice) {
			case 1: // view all preparing orders
			{
				if (next_order_id == 1) {
					System.out.println("There are no orders.");
					break;
				}
				ArrayList<RoomServiceOrder> list = new ArrayList<RoomServiceOrder>();
				for (RoomServiceOrder order : order_history) {
					if (order.getStatus() == OrderStatus.PREPARING)
						list.add(order);
				}
				boundary.printMainTitle("Preparing Orders Found");
				boundary.printListOfOrders(list);
				break;
			}
			case 2: // view all confirmed orders
			{
				if (next_order_id == 1) {
					System.out.println("There are no orders.");
					break;
				}
				ArrayList<RoomServiceOrder> list = new ArrayList<RoomServiceOrder>();
				for (RoomServiceOrder order : order_history) {
					if (order.getStatus() == OrderStatus.CONFIRMED)
						list.add(order);
				}
				boundary.printMainTitle("Completed Orders Found");
				boundary.printListOfOrders(list);
				break;
			}
			case 3: // change order status
			{
				int order_id;
				OrderStatus status;
				
				if (next_order_id == 1) {
					System.out.println("There are no orders.");
					break;
				}
				System.out.println("Enter order id of status to be changed: ");
				order_id = boundary.getIntFromUser(1, next_order_id-1);
				
				ArrayList<RoomServiceOrder> list = searchForOrderWithID(order_id);
				
				boundary.printListOfOrders(list);
				
				if (list.size()==0)
					break;
				
				System.out.printf("Curent status is %s\n", list.get(0).getStatus());
				System.out.println("Select the new status: ");
				System.out.println("1. Preparing");
				System.out.println("2. Delivered");
				System.out.println("0. Do not change status and go back.");
				int tempChoice = boundary.getIntFromUser(0,2);
				if (tempChoice == 0) break;
				
				status = (tempChoice==1) ? OrderStatus.PREPARING : OrderStatus.DELIVERED;
				
				list.get(0).setStatus(status);
				boundary.printSuccess(true, 6);
				
				break;
			}
			case 0:
				break;
			} 
		} while (choice!=0);
	}
	
	/**
	 * Sub method called by {@code processMain()} to search for an existing order.
	 */
	public void searchOrderMenu() {
		
		int choice;
		
		do {
			choice = boundary.userInputFromMenu(search_order_menu);
			
			switch (choice) {
			case 1: // search order by id
			{
				if (next_order_id == 1) {
					System.out.println("There are no orders.");
					break;
				}
				
				System.out.println("Enter order id to be searched: ");
				int order_id = boundary.getIntFromUser(1, next_order_id-1);
				
				ArrayList<RoomServiceOrder> list = searchForOrderWithID(order_id);
				
				boundary.printMainTitle("Order of id " + order_id);
				boundary.printListOfOrders(list);
				break;
			}
			case 2: // search order by room number
			{
				if (next_order_id == 1) {
					System.out.println("There are no orders.");
					break;
				}
				
				System.out.println("Enter room number to be searched: ");
				String room_number;
				
				do {
					System.out.println("Please enter the room number (eg. 0208): ");
					room_number = boundary.getStringFromUser();
					
					String general_pattern = "^[0123456789]{4}$";
					String valid_room_pattern = "^[0][234567][0][12345678]$";
					
					
					if (!Pattern.matches(general_pattern, room_number)) {
						System.out.println("Please enter in a valid format.");
						continue;
					}
					else if (!Pattern.matches(valid_room_pattern, room_number)) {
						System.out.println("No such room exists.");
						continue;
					}
					else
						break;

				} while (true);
				
				ArrayList<RoomServiceOrder> list = searchForOrderWithRoomNum(room_number);
				
				boundary.printMainTitle("Orders for Room " + room_number);
				boundary.printListOfOrders(list);
				break;
			}
			case 0:
				break;
			}
		} while (choice != 0);
	}

	// search for order with id
	/**
	 * Returns a list of {@code RoomServiceOrder} objects with the specified order id.
	 * @param order_id  order id of order to be found
	 * @return  a list of orders with the specified order id
	 */
	public ArrayList<RoomServiceOrder> searchForOrderWithID(int order_id){
		
		ArrayList<RoomServiceOrder> list = new ArrayList<RoomServiceOrder>();
		for (RoomServiceOrder order : order_history) {
			if (order.getOrder_id() == order_id)
				list.add(order);
		}
		
		return list;
	}
	
	// search for order with room number
	/**
	 * Returns a list of {@code RoomServiceOrder} objects with the specified room number.
	 * @param room_num  room number of order to be found
	 * @return  a list of orders with the specified room number
	 */
	public ArrayList<RoomServiceOrder> searchForOrderWithRoomNum(String room_num){
			
		ArrayList<RoomServiceOrder> list = new ArrayList<RoomServiceOrder>();
		for (RoomServiceOrder order : order_history) {
			if (order.getRoom_number().equals(room_num))
				list.add(order);
		}
			
		return list;
	}
	
	/**
	 * Returns a list of {@code RoomServiceOrder} objects with the specified room number that has yet to be paid.
	 * @param room_number  room number of order to be found
	 * @return  a list of orders with the specified room number that has yet to be paid
	 */
	public ArrayList<RoomServiceOrder> getCurrentOrdersOfRoom(String room_number){
		
		ArrayList<RoomServiceOrder> list = new ArrayList<RoomServiceOrder>();
		
		for (RoomServiceOrder order : order_history) {
			if (order.getRoom_number().equals(room_number) && !order.isPaid() ) {
				list.add(order);
			}
		}
		return list;
	}

	/**
	 * Sets a list of {@code RoomServiceOrder} objects with the specified room number to paid=true.
	 * @param room_number  the specified room_number
	 */
	public void setPaidCurrentOrdersOfRoom(String room_number) {
		
		ArrayList<RoomServiceOrder> list = getCurrentOrdersOfRoom(room_number);
		
		for (RoomServiceOrder order : list) {
			order.setPaid(true);
			order.setStatus(OrderStatus.DELIVERED); 
			// set status to delivered here if staff did not manually change status to delivered.
		}
		close();
	}
}
