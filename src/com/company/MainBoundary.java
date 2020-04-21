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
						"Add / Manage Reservations",
						"Check-in / Check-out Services",
						"Room Services",
						"Room Overview",
						"Schedule Overview",
						"Administrative Control",
				};
		printMenuList(menuList, "Exit Application");
		System.out.println();
	}
}
