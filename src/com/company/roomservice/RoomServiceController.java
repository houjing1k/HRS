package com.company.roomservice;
/**
 * 
 */


/**
 * @author damien
 *
 */

import java.util.*;
import java.time.LocalDateTime;
import com.company.Controller;

public class RoomServiceController extends Controller {
	
	private final String[] main_menu = {
			"Room Service",
			"View Menu",
			"Edit Menu",
			"Create New Order",
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
	
	private final String filedir_menu = "room_service_menu.ser";
	private final String filedir_order_history = "room_service_order_history.ser";
	
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
				viewMenu();
				break;
			case 2: // edit menu
				editMenu();
				break;
			case 3:
				//createOrder();
				break;
			case 0:
				break;
			}
		} while (choice != 0);
		close();
		 
	}
	
	public void viewMenu() {
		// feat?: for multiple users, perhaps need to check if menu was updated by other users? check if file was updated, if yes, read menu from file
		boundary.printFoodMenu(menu);	
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
				
				menu.addItem(name, description, price);
				boundary.printSuccess(choice);
				
				break;
			}
			
			case 2: // remove item
			{
				
				Object[] inputs = boundary.userInputForRemoveItem(menu.size());
				int index_to_remove = ((int) inputs[0]) - 1;
				
				menu.removeItem(index_to_remove);
				boundary.printSuccess(choice);
				
				break;
			}
			
			case 3: // update name
			{
				
				Object[] inputs = boundary.userInputForUpdateName(menu.size());
				int index_to_update = ((int) inputs[0]) - 1;
				String new_name = (String) inputs[1];
				
				menu.updateItemName(index_to_update, new_name);
				boundary.printSuccess(choice);
				
				break;
			}
			
			case 4: // update description
			{
				
				//Object[] inputs = boundary.userInputForUpdateDescription(menu.size());
				
				break;
			}
				
			case 5: // update price
			{
				
				break;
			}
			
			case 6: // update status
			{
				
				break;		
			}
			case 0:
				break;
			default:
				break;
				
			}
		} while (choice != 0);
		
	}
	
	public void viewOrder(RoomServiceOrder order) {
		if (order.size() == 0) System.out.println("No items in order.");
		else {
			int i=1;
			System.out.println("Current order:");
			for (RoomServiceItem item : order) {
				System.out.printf("%d: \t%s \n\t$%.2f\n", i, item.getName(), item.getPrice());
				i++;
			}
		}		
	}
	
	public void reviewOrder(RoomServiceOrder order) {
		
		if (order.size() == 0) System.out.println("No items in menu.");
		else {
			System.out.println("\tYour current order:");
			
			double total_price=0;
			
			Set<RoomServiceItem> unique_items = new HashSet<RoomServiceItem>(order.getOrderList());
			
			for (RoomServiceItem item : unique_items) {
				int quantity = Collections.frequency(order.getOrderList(), item);
				double price = item.getPrice() * quantity;
				System.out.println("\t" + item.getName() + "\n\t\t" + quantity + "\t$" + price);
				total_price += price;
			}
			System.out.println("Total price:\t" + total_price);
		}
	}
	
	/*
	public void createOrder() {
		
		
		Scanner sc = new Scanner(System.in);
		int choice, room_num;
		
		System.out.print("Please enter the room number: ");
		
		while (!sc.hasNextInt()) {
			System.out.print("Please enter an integer. ");
			sc.next();
		}
		room_num = sc.nextInt();
		
		RoomServiceOrder temp_order = new RoomServiceOrder(this.next_order_id, LocalDateTime.now(), room_num);
		System.out.println();
		
		do {
			sc = new Scanner(System.in); // to flush any inputs;
			
			System.out.println("1) \tAdd items to order.\n"
					+ "2) \tRemove items from order.\n"
					+ "3) \tView my selected items.\n"
					+ "4) \tPlace order.\n"
					+ "-1) \tGo back.\n");
			
			System.out.print("Please enter your choice: ");
			
			while (!sc.hasNextInt()) {
				System.out.print("Please enter an integer. ");
				sc.next();
			}
			
			choice = sc.nextInt();
			System.out.println();
			
			switch (choice) {
			case 1:
				// needs improvement!! 
			{
				viewMenu();
				System.out.println();
				
				int index, quantity;
				do {
					System.out.print("Enter item number (-1 to finish adding): ");
					
					while (!sc.hasNextInt()) {
						System.out.print("Please enter an integer. ");
						sc.next();
					}
					index = sc.nextInt();
					
					if (index == 0 || index > menu.size()) {
						System.out.println("Item not found.");
						continue;
					}
					if (index == -1) {
						break;
					}
					
					System.out.print("Enter quantity of " + menu.getItem(index-1).getName() + ": ");
					
					while (!sc.hasNextInt()) {
						System.out.print("Please enter an integer. ");
						sc.next();
					}
					quantity = sc.nextInt();
					
					temp_order.addItem(menu.getItem(index-1), quantity);
					
					System.out.println("\t" + quantity + " " + menu.getItem(index-1).getName() + " has been added.");
					
				} while (index != -1);
				break;
			}
			case 2: // remove item from order
			{
				viewOrder(temp_order);
				System.out.println();
				
				int index_to_remove = 0;
				System.out.print("Enter item to be removed: ");
				
				while (!sc.hasNextInt()) {
					System.out.print("Please enter an integer. ");
					sc.next();
				}
				index_to_remove = sc.nextInt();
				
				// check if index is within range
				if (index_to_remove < 1 || index_to_remove > temp_order.size()) {
					System.out.println("Item not found.");
					break;
				}
				
				try {
					temp_order.removeItem(index_to_remove-1);
				}
				catch (IndexOutOfBoundsException e) {
					System.out.println("Item not found.");
				}
				break;
			}
			case 3: // view list of current orders
			{
				reviewOrder(temp_order);
				break;
			}
			case 4:
			{
				
				break;
			}
			case -1:
				break;
			default:
			{
				System.out.println("Please choose one of the options.");
				break;
			}
			}
			System.out.println();
		} while (choice != -1);
	}
	*/
}
