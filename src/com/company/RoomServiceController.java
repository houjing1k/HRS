package com.company;
/**
 * 
 */


/**
 * @author damien
 *
 */

import java.time.LocalDateTime;
import com.company.Controller;

public class RoomServiceController extends Controller {
	
	private final String[] main_menu = {
			"Room Service",
			"View Menu",
			"Edit Menu",
			"Create New Order",
			"View/Search Order History",
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
	
	private final String[] order_history_menu = {
			"View/Search Order History",
			"View all orders",
			"Search order by ID",
			"Search order by Room Number",
			"Back to Room Service Menu"
	};
	
	private final String filedir_menu = "./tmp/room_service_menu.ser";
	private final String filedir_order_history = "./tmp/order_history.ser";
	
	private RoomServiceMenu menu;
	private RoomServiceOrderHistory order_history;
	private int next_order_id;
	private RoomServiceBoundary boundary;
	
	public RoomServiceController() {
		boundary = new RoomServiceBoundary();
		
		this.menu = fromFile(filedir_menu);
		if (this.menu == null) this.menu = new RoomServiceMenu();
		
		this.order_history = fromFile(filedir_order_history);
		if (this.order_history == null) this.order_history = new RoomServiceOrderHistory();
		this.next_order_id = order_history.getLatestOrderID() + 1;
	}
	
	public void close() {
		toFile(menu, filedir_menu);
		toFile(order_history, filedir_order_history);
	}
	
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
				orderHistoryMenu();
				break;
			case 0:
				break;
			}
		} while (choice != 0);
		 
	}
	
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
				Object[] inputs = boundary.userInputForRemoveItem(menu.size());
				int index_to_remove = ((int) inputs[0]) - 1;
				
				boolean flag = true;
				if (menu.removeItem(index_to_remove) == null) flag = false;
				
				boundary.printSuccess(flag, choice);
				break;
			}
			
			case 3: // update name
			{
				Object[] inputs = boundary.userInputForUpdateItem(menu.size(), 0);
				int index_to_update = ((int) inputs[0]) - 1;
				String new_name = (String) inputs[1];
				
				boolean flag = menu.updateItemName(index_to_update, new_name);
				
				boundary.printSuccess(flag, choice);
				break;
			}
			
			case 4: // update description
			{
				Object[] inputs = boundary.userInputForUpdateItem(menu.size(), 1);
				int index_to_update = ((int) inputs[0]) - 1;
				String new_description = (String) inputs[1];
				
				boolean flag = menu.updateItemDescription(index_to_update, new_description);
				
				boundary.printSuccess(flag, choice);
				break;
			}
				
			case 5: // update price
			{
				Object[] inputs = boundary.userInputForUpdateItem(menu.size(), 2);
				int index_to_update = ((int) inputs[0]) - 1;
				double new_price = (double) inputs[1];
				
				boolean flag = menu.updateItemPrice(index_to_update, new_price);
				
				boundary.printSuccess(flag, choice);
				break;
			}
			
			case 6: // update status
			{
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
	
	public void createOrderMenu() {
		
		System.out.println("Please enter the room number: ");
		
		int room_num = boundary.getIntFromUser(); // need to account for invalid room numbers
		
		System.out.println();
		
		RoomServiceOrder temp_order = new RoomServiceOrder(this.next_order_id, LocalDateTime.now(), room_num);
		
		int choice;
		
		do {
			choice = boundary.userInputFromMenu(create_order_menu);
			
			switch (choice) {
			case 1:
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
			case 4: // view list of current orders
			{
				boundary.printOrder(temp_order);
				break;
			}
			case 5:
			{
				temp_order.setStatus(OrderStatus.CONFIRMED);
				order_history.addOrder(temp_order);
				
				temp_order = null;
				System.out.println("Order has been confirmed.");
				System.out.println();
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
	
	public void orderHistoryMenu() {
		
		int choice;
		
		do {
			choice = boundary.userInputFromMenu(order_history_menu);
			
			switch (choice) {
			case 1:
			
			case 0:
				break;
			}
		} while (choice != 0);
	}
}
