package com.company;

import java.util.Scanner;

public class MainBoundary implements IBoundary
{
	private Scanner sc;

	public MainBoundary()
	{
		sc = new Scanner(System.in);
	}

	@Override
	public int process()
	{
		printMenu();
		int sel=sc.nextInt();
		return sel;
	}

	private void printMenu()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~~~~~ Welcome to Hotel Reservation App ~~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("1 - Add/Manage Guests");
		System.out.println("2 - Add/Manage Reservations");
		System.out.println("3 - Room Services");
		printDivider();
		System.out.println("4 - Room Check-in / Check-out");
		printDivider();
		System.out.println("5 - Manage Hotel Rooms");
		System.out.println("6 - Other Admin");
		printDivider();
		System.out.println("0 - Exit Application");
		printDivider();
		System.out.println();
	}

	private void printDivider()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
}
