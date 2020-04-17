package com.company;

import com.company.Boundary;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class RoomServiceBoundary extends Boundary {

	private Scanner sc;
	private String currency;
	
	/**
	 * Initializes new Scanner object and set currency
	 */
	public RoomServiceBoundary() {
		sc = new Scanner(System.in);
		currency = "$";
	}
	
	/**
	 * Gets user input for name, description, and price to create a {@code RoomServiceItem} object.
	 * @return an array of objects to be down-casted to String, String, double
	 */
	Object[] userInputForAddItem() {
		
		Object[] inputs = new Object[3];
		
		System.out.println("Enter item name: ");
		inputs[0] = getStringFromUser();
		while ( ((String) inputs[0]).length() >= 20 ) {
			System.out.println("Name must be less than 20 characters.");
			System.out.println("Enter item name: ");
			inputs[0] = getStringFromUser();
		}
		System.out.println();
		
		System.out.println("Enter item description: ");
		inputs[1] = getStringFromUser();
		while ( ((String) inputs[1]).length() >= 80 ) {
			System.out.println("Description must be less than 80 characters.");
			System.out.println("Enter item description: ");
			inputs[1] = getStringFromUser();
		}
		System.out.println();
		
		System.out.println("Enter item price: ");
		inputs[2] = getDoubleFromUser();
		System.out.println();
		
		return inputs;
	}
	
	/**
	 * Gets user input for an integer to represent the index of a list
	 * @param size  size of the list
	 * @return  an array of 1 object to be down-casted, the integer index
	 */
	Object[] userInputForRemoveItem(int size) {
		
		Object[] inputs = new Object[1];
		
		System.out.println("Enter index of item to remove: ");
		inputs[0] = getIntFromUser(1, size);
		System.out.println();
		
		return inputs;
	}
	
	/**
	 * Gets user input for an integer to represent the index of a list, and
	 * gets user input for name, description, price, or status depending on {@code attribute} parameter.
	 * 
	 * @param menuSize  the size of the list
	 * @param attribute  0: name, 1: description, 2: price, 3: status
	 * @return  an array of objects to be down-casted
	 */
	Object[] userInputForUpdateItem(int menuSize, int attribute) {
		
		Object[] inputs = new Object[2];
		
		System.out.println("Enter the index of item to update: ");
		inputs[0] = getIntFromUser(1, menuSize);
		System.out.println();
		switch (attribute) {
		case 0:
			System.out.println("Enter the new name: ");
			inputs[1] = getStringFromUser();
			while ( ((String) inputs[1]).length() >= 20 ) {
				System.out.println("Name must be less than 20 characters.");
				System.out.println("Enter item name: ");
				inputs[1] = getStringFromUser();
			}
			break;
		case 1:
			System.out.println("Enter the new description: ");
			inputs[1] = getStringFromUser();
			while ( ((String) inputs[1]).length() >= 80 ) {
				System.out.println("Description must be less than 80 characters.");
				System.out.println("Enter item description: ");
				inputs[1] = getStringFromUser();
			}
			break;
		case 2:
			System.out.println("Enter the new price: ");
			inputs[1] = getDoubleFromUser();
			break;
		case 3:
			System.out.println("Select the new status: ");
			System.out.println("1. In Stock");
			System.out.println("2. Out Of Stock");
			inputs[1] = (getIntFromUser(1,2)==1) ? StockStatus.IN_STOCK : StockStatus.OUT_OF_STOCK;
			break;
		default:
			// invalid option for attribute
		}
		
		System.out.println();
		return inputs;
	}	
	
	/**
	 * Outputs a success or failure message depending on {@code flag} and {@code choice} parameters.
	 * @param flag  true: success,  false: failure
	 * @param choice  1: add item, 2: remove item, 3: update item name, 4: update item description, 
	 * 5: update item price, 6: update item status
	 */
	void printSuccess(boolean flag, int choice) {
		switch (choice) {
		case 1:
			if (flag == true) System.out.println("Item successfully added.");
			else System.out.println("Item was not added.");
			break;
		case 2:
			if (flag == true) System.out.println("Item successfully removed.");
			else System.out.println("Item was not removed.");
			break;
		case 3:
			if (flag == true) System.out.println("Item name successfully updated.");
			else System.out.println("Item name was not updated.");
			break;
		case 4:
			if (flag == true) System.out.println("Item description successfully updated.");
			else System.out.println("Item description was not updated.");
			break;
		case 5:
			if (flag == true) System.out.println("Item price successfully updated.");
			else System.out.println("Item price was not updated.");
			break;
		case 6:
			if (flag == true) System.out.println("Status successfully updated.");
			else System.out.println("Status was not updated.");
			break;
			
		}
		System.out.println();
	}
	
	
	/**
	 * Outputs the contents of a specified {@code RoomServiceMenu}.
	 * @param menu  the menu to be printed
	 */
	void printFoodMenu(RoomServiceMenu menu) {
		
		if (menu.size() == 0) System.out.println("No items in menu.");
		else {
			int i=1;
			printMainTitle("Today's Menu");
			for (RoomServiceItem item : menu) {
				System.out.printf("%d: \t%s \n\t%s%-15.2f %s\n", i, item.getName(), currency, item.getPrice(), item.getStatus());
				
				String[] wordlist = item.getDescription().split(" ");
				StringBuilder builder = new StringBuilder();
				
				for (String word : wordlist)
				{
					int temp = builder.length();
					builder.append(word + " ");
					if (builder.length() >  40 ) // arbitrary value of 40
					{
						builder.setLength(temp);
						System.out.printf("\t" + builder.toString() + "\n");
						builder = new StringBuilder(word + " ");
					}
				}
				int temp = builder.length();
				builder.setLength(temp - 1);
				System.out.printf("\t" + builder.toString() + "\n");
				i++;
			}
		}
		printDivider();
	}	

	/**
	 * Outputs the contents of a specified {@code RoomServiceOrder}.
	 * @param order  the order to be printed
	 */
	void printOrder(RoomServiceOrder order) {
		if (order == null || order.size() == 0) System.out.println("No items in order.");
		else {
			int i=1;
			for (RoomServiceItem item : order) {
				System.out.printf("%d: \t%s \n\t%s%.2f\n", i, item.getName(), currency, item.getPrice());
				i++;
			}
			System.out.print("Remarks: ");
			System.out.println(order.getRemarks());
			System.out.print("Total: " + currency);
			System.out.printf("%.2f\n",order.getBill());
		}
	}
	

	/**
	 * Outputs the contents of a list of {@code RoomServiceOrder} objects.
	 * @param list  the list of {@code RoomServiceOrder} objects to be printed
	 */
	void printListOfOrders(ArrayList<RoomServiceOrder> list) {
		if (list == null || list.size() == 0) System.out.println("No orders found.");
		else {
			System.out.printf("%-9s %-13s %-9s %s\n",
					"OrderId", "Room Number", "Amount","Time of order");
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			for (RoomServiceOrder order : list) {
				
				String formatedDateTime = order.getOrder_date_time().format(formatter);
				System.out.printf("%-9d %-13d %s%-8.2f %s\n", 
						order.getOrder_id(), order.getRoom_number(), currency, order.getBill(), formatedDateTime);
			}
		}
		System.out.println();
	}
	
	/**
	 * Gets user input of integer of which menu option chosen
	 * @param menu  string array of menu options to choose from
	 * @return  the index of menu option
	 */
	public int userInputFromMenu(String[] menu) {
		
		printMenu(menu);
		int choice = getIntFromUser(0, menu.length - 2);
		System.out.println();
		
		return choice;
	}
	
	/**
	 * Gets string user input in form of a sentence instead of single word.
	 * @return  String sentence
	 */
	public String getStringFromUser() {
		
		StringBuilder builder = new StringBuilder();;
		builder.append(sc.next());
		builder.append(sc.nextLine());
		
		return builder.toString().trim();
	}
	
	/**
	 * Gets user input of double
	 * @return  double
	 */
	public double getDoubleFromUser() {
		
		double doub;
		while (!sc.hasNextDouble()) {
			System.out.print("Please enter a number.");
			sc.next();
		}
		doub = sc.nextDouble();
		
		return doub;
	}
	
	/**
	 * Gets user input of integer
	 * @return  integer
	 */
	public int getIntFromUser() {
		
		int num;
		while (!sc.hasNextInt()) {
			System.out.println("Please enter an integer.");
			sc.next();
		}
		num = sc.nextInt();
		
		return num;
	}
	
	/**
	 * Gets user input of integer of at least {@code min} and at most {@code max}.
	 * @param min  minimum value
	 * @param max  maximum value
	 * @return  integer
	 */
	public int getIntFromUser(int min, int max) {
		
		int num = getIntFromUser();
		
		if (num < min || num > max) {
			System.out.println("Please choice a valid option.");
			num = getIntFromUser(min, max);
		}
		
		return num;
	}
	
	/**
	 * Outputs menu options to user
	 * @param menu  String array of menu options
	 */
	public void printMenu(String[] menu) {
		
		printMainTitle(menu[0]);
		String[] temp = new String[menu.length-2];
		System.arraycopy(menu, 1, temp, 0, menu.length-2 );
		printMenuList(temp, menu[menu.length-1]);
	}

	@Override
	public int process() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void printMenu() {
		// TODO Auto-generated method stub
		
	}
}
