package com.company.roomservice;

import com.company.IBoundary;
import java.util.Scanner;

class RoomServiceBoundary implements IBoundary {

	private Scanner sc;
	
	public RoomServiceBoundary() {
		sc = new Scanner(System.in);
	}
	
	Object[] userInputForAddItem() {
		
		Object[] inputs = new Object[3];
		
		System.out.println("Enter item name: ");
		inputs[0] = getStringFromUser();
		System.out.println();
		
		System.out.println("Enter item description: ");
		inputs[1] = getStringFromUser();
		System.out.println();
		
		System.out.println("Enter item price: ");
		inputs[2] = getDoubleFromUser();
		System.out.println();
		
		return inputs;
	}
	
	Object[] userInputForRemoveItem(int menuSize) {
		
		Object[] inputs = new Object[1];
		
		System.out.println("Enter index of item to remove: ");
		inputs[0] = getIntFromUser(1, menuSize);
		System.out.println();
		
		return inputs;
	}
	
	Object[] userInputForUpdateName(int menuSize) {
		
		Object[] inputs = new Object[2];
		
		System.out.println("Enter the index of item to update: ");
		inputs[0] = getIntFromUser(1, menuSize);
		System.out.println();
		
		System.out.println("Enter the new name: ");
		inputs[1] = getStringFromUser();
		System.out.println();
		
		return inputs;
	}
	
	void printSuccess(int choice) {
		switch (choice) {
		case 1:
			System.out.println("Item successfully added.");
			break;
		case 2:
			System.out.println("Item successfully removed.");
			break;
		case 3:
			System.out.println("Item name successfully updated.");
			break;
		case 4:
			System.out.println("Item description successfully updated.");
			break;
		case 5:
			System.out.println("Item price successfully updated.");
			break;
		case 6:
			System.out.println("Item price successfully updated.");
			break;
			
		}
		System.out.println();
	}
	
	void printFoodMenu(RoomServiceMenu menu) {
		
		if (menu.size() == 0) System.out.println("No items in menu.");
		else {
			int i=1;
			System.out.println("Today's menu:");
			for (RoomServiceItem item : menu) {
				System.out.printf("%d: \t%s \n\t$%.2f \t\t%s\n", i, item.getName(), item.getPrice(), item.getStatus());
				System.out.printf("\t%s\n",item.getDescription());
				i++;
			}
		}
		System.out.println();
	}
	
	public int userInputFromMenu(String[] menu) {
		
		printMenu(menu);
		int choice = getIntFromUser(0, menu.length - 2);
		System.out.println();
		
		return choice;
	}
	
	public String getStringFromUser() {
		
		String str;
		str = sc.next();
		str += sc.nextLine();
		
		return str;
	}
	
	public double getDoubleFromUser() {
		
		double doub;
		while (!sc.hasNextDouble()) {
			System.out.print("Please enter a number.");
			sc.next();
		}
		doub = sc.nextDouble();
		
		return doub;
	}
	
	public int getIntFromUser() {
		
		int num;
		while (!sc.hasNextInt()) {
			System.out.print("Please enter an integer.");
			sc.next();
		}
		num = sc.nextInt();
		
		return num;
	}
	
	public int getIntFromUser(int min, int max) {
		
		int num = getIntFromUser();
		
		if (num < min || num > max) {
			System.out.println("Please choice a valid option.");
			num = getIntFromUser(min, max);
		}
		
		return num;
	}
	public void printMenu(String[] menu) {
		
		int index = 0;
		for (String item : menu) {
			if (index==0) {
				System.out.println(item);
			}
			else if (index == menu.length-1) {
				System.out.println("0. \t" + item);
				System.out.println();
			}
			else {
				System.out.println(index + ". \t" + item);
			}
			index++;
		}
	}
	

	@Override
	public int process() {
		// TODO Auto-generated method stub
		
		return 0;
	}
}
