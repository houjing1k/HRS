package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class GuestBoundary implements IBoundary
{
	private Scanner sc;

	public GuestBoundary()
	{
		sc = new Scanner(System.in);
	}

	@Override
	public int process()
	{
		printMenu();
		int sel = sc.nextInt();
		return sel;
	}

	private void printMenu()
	{
		System.out.println("~~~~~~~~~~~~~~~~~ Manage Guests ~~~~~~~~~~~~~~~~");
		System.out.println("1 - Add new Guest");
		System.out.println("2 - Update Guest Details");
		System.out.println("3 - Search Guests by Name");
		System.out.println("4 - Print all Guests");
		System.out.println("0 - Back to Main Menu");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
	}

	public GuestEntity addGuest(int ID)
	{
		GuestEntity newGuest = new GuestEntity();
		newGuest.setGuestID(ID);
		sc.nextLine();
		System.out.println("~~~~~~~~~~~~~~~~~~~ Add Guest ~~~~~~~~~~~~~~~~~~");
		System.out.println("Full Name: ");
		newGuest.setName(sc.nextLine());
		System.out.println("Address: ");
		newGuest.setAddress(sc.nextLine());
		System.out.println("Country: ");
		newGuest.setCountry(sc.nextLine());
		System.out.println("Gender (M/F) : ");
		newGuest.setGender(sc.next().charAt(0));
		sc.nextLine();
		System.out.println("Identity Number: ");
		newGuest.setIdentityNo(sc.nextLine());
		System.out.println("Nationality: ");
		newGuest.setNationality(sc.nextLine());
		System.out.println("Credit Card Number: ");
		newGuest.setCreditCardNum(sc.nextLine());
		System.out.println("Contact Number: ");
		newGuest.setContactNo(sc.nextLine());

		printGuest(newGuest);

		return newGuest;
	}

	public void printGuestList(ArrayList<GuestEntity> list)
	{
		for (GuestEntity e : list)
		{
			System.out.println(e);
		}
	}
	public void printGuest(GuestEntity e)
	{
			System.out.println(e);
	}

	public String searchGuest()
	{
		sc = new Scanner(System.in);
		String name;
		System.out.println("Enter Name (Keyword): ");
		name = sc.nextLine();
		return name;
	}

	public int searchGuestID()
	{
		sc = new Scanner(System.in);
		int id;
		System.out.println("Enter Guest ID: ");
		id = sc.nextInt();
		return id;
	}

	public void waitInput()
	{
		sc = new Scanner(System.in);
		System.out.println("\nPress Enter to return.");
		sc.nextLine();
	}

	public int updateGuest()
	{
		sc = new Scanner(System.in);

		System.out.println("Enter Field to Update: ");
		System.out.println("1 - Name");
		System.out.println("2 - Address");
		System.out.println("3 - Country");
		System.out.println("4 - Gender");
		System.out.println("5 - Identity No.");
		System.out.println("6 - Nationality");
		System.out.println("7 - Contact No.");
		System.out.println("8 - Credit Card");

		return sc.nextInt();
	}
	public String updateGuestSub(int sel)
	{
		sc = new Scanner(System.in);
		switch (sel)
		{
			case 1:
				System.out.println("New Name: ");
				return sc.nextLine();
			case 2:
				System.out.println("New Address: ");
				return sc.nextLine();
			case 3:
				System.out.println("New Country: ");
				return sc.nextLine();
			case 4:
				System.out.println("New Gender: ");
				return sc.nextLine();
			case 5:
				System.out.println("New Identity No.: ");
				return sc.nextLine();
			case 6:
				System.out.println("New Nationality: ");
				return sc.nextLine();
			case 7:
				System.out.println("New Contact No.: ");
				return sc.nextLine();
			case 8:
				System.out.println("New Credit Card No.: ");
				return sc.nextLine();
			default:
				System.out.println("Invalid Input");
				return null;
		}
	}
	public Character updateGuest(int sel)
	{
		sc = new Scanner(System.in);
		switch (sel)
		{
			case 4:
				System.out.println("New Gender: ");
				return sc.nextLine().charAt(0);
			default:
				System.out.println("Invalid Input");
				return null;
		}
	}

}
