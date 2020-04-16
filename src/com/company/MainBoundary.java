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
		String[] menuList=
				{
						"Add/Manage Guests",
						"Add/Manage Reservations",
						"Room Services",
						"Room Check-in / Check-out",
						"Payment Options",
						"Administrative Control"
				};
		printMenuList(menuList,"Exit Application");
		System.out.println();
	}
}
