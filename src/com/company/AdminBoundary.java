package com.company;

import java.util.Scanner;

public class AdminBoundary extends Boundary
{
	private Scanner sc;

	public AdminBoundary()
	{
		sc = new Scanner(System.in);
	}

	protected void printMenu()
	{
		printMainTitle("Manage Guests");
		String[] menuList =
				{
						"Add / Create Rooms",
						"Print Room Report",
						"Search Guests by Name",
						"Modify Hotel Charges",
						"Print Bill Invoice"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}

}
