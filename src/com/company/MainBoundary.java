package com.company;

import java.util.Scanner;

public class MainBoundary extends Boundary
{
	private Scanner sc;

	public MainBoundary()
	{
		sc = new Scanner(System.in);
	}

	protected void printMenu()
	{
		printMainTitle("Welcome to Hotel Reservation App");
		String[] menuList =
				{
						"Add/Manage Reservations",
						"Room Check-in / Check-out",
						"Room Services",
						"Add/Manage Guests",
						"Administrative Control",
						"Old Main Controller"
				};
		printMenuList(menuList, "Exit Application");
		System.out.println();
	}
}
