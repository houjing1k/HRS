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
		printMainTitle("Administrative Control");
		String[] menuList =
				{
						"Manage Rooms",
						"Print Occupancy Reports",
						"Modify Hotel Charges",
						"Print Bill Invoice"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}

}