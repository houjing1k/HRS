package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class GuestBoundary implements IBoundary
{
	private Scanner sc;
	private String[] list = {"Name", "Address", "Country", "Gender", "Identity No.", "Nationality", "Contact No.", "Credit Card No."};

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

	public void addGuestMenu()
	{
		System.out.println("---Add New Guest--- ");
	}

	public void printGuestList(ArrayList<GuestEntity> list)
	{
		if ((list == null) || (list.size() == 0))
		{
			System.out.println("No records found.");
		}
		else
		{
			for (GuestEntity e : list)
			{
				System.out.println(e);
			}
		}
	}

	public void printGuest(GuestEntity e)
	{
		if (e == null)
		{
			System.out.println("No records found.");
		}
		else
		{
			System.out.println(e);
		}
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
		System.out.println("\nPress \"Enter\" to return.");
		sc.nextLine();
	}

	public int updateGuest()
	{
		sc = new Scanner(System.in);
		int sel;
		while (true)
		{
			try
			{
				System.out.println("Enter Field to Update: ");
				System.out.println("1 - Name");
				System.out.println("2 - Address");
				System.out.println("3 - Country");
				System.out.println("4 - Gender");
				System.out.println("5 - Identity No.");
				System.out.println("6 - Nationality");
				System.out.println("7 - Contact No.");
				System.out.println("8 - Credit Card");
				System.out.println("0 - Exit");
				sel = sc.nextInt();
				if (sel < 0 || sel > 8)
				{
					throw new Exception();
				}
				else
				{
					return sel;
				}
			} catch (Exception e)
			{
				System.out.println("Invalid choice.");
			}

		}
	}

	public String addUpdateGuestSub(int sel, boolean update)
	{
		sc = new Scanner(System.in);
		String input = null;
		boolean check = true;
		while (check)
		{
			try
			{
				if (update)
				{
					System.out.println("New " + list[sel - 1] + ":");
				}
				else
				{
					System.out.println(list[sel - 1] + ":");
				}
				input = sc.nextLine();
				check = false;
				switch (sel)
				{
					case 1:
						if (!checkName(input)) throw new Exception();
						break;
					case 2:
						if (!checkAddress(input)) throw new Exception();
						break;
					case 3:
						if (!checkCountry(input)) throw new Exception();
						break;
					case 4:
						if (!checkGender(input)) throw new Exception();
						break;
					case 5:
						if (!checkIdentityNo(input)) throw new Exception();
						break;
					case 6:
						if (!checkNationality(input)) throw new Exception();
						break;
					case 7:
						if (!checkContactNo(input)) throw new Exception();
						break;
					case 8:
						if (!checkCreditCardNo(input)) throw new Exception();
						break;
				}

			} catch (Exception e)
			{
				check = true;
			}
		}
		return input;
	}

	public boolean confirmation(GuestEntity guest)
	{
		sc = new Scanner(System.in);
		char choice;
		while (true)
		{
			try
			{
				System.out.println("Please confirm the following");
				System.out.println("(Y - Accept, N - Decline):");
				System.out.println();
				printGuest(guest);
				choice = sc.next().charAt(0);

				if (choice == 'Y')
				{
					System.out.println("Changes accepted.");
					return true;
				}
				else if (choice == 'N')
				{
					System.out.println("No changes made.");
					return false;
				}
				else
				{
					throw new Exception();
				}
			} catch (Exception e)
			{
				System.out.println("Invalid Input.");
			}
		}
	}

	private boolean checkName(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("^[ A-Za-z]+$")));
		if (!check)
		{
			System.out.println("Invalid input. Only alphabetical accepted");
		}
		return check;
	}

	private boolean checkAddress(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null));
		if (!check)
		{
			System.out.println("Invalid input.");
		}
		return check;
	}

	private boolean checkCountry(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("^[A-Za-z]+$")));
		if (!check)
		{
			System.out.println("Invalid input. Only alphabetical single-word accepted");
		}
		return check;
	}

	private boolean checkGender(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.equals("M") || str.equals("F")));
		if (!check)
		{
			System.out.println("Invalid input. Only input (M / F).");
		}
		return check;
	}

	private boolean checkIdentityNo(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("^[a-zA-Z0-9]+$")));
		if (!check)
		{
			System.out.println("Invalid input. Only alphanumeric accepted");
		}
		return check;
	}

	private boolean checkNationality(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("^[A-Za-z]+$")));
		if (!check)
		{
			System.out.println("Invalid input. Only alphabetical single-word accepted");
		}
		return check;
	}

	private boolean checkContactNo(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("^[0-9]+$")));
		if (!check)
		{
			System.out.println("Invalid input. Only alphanumeric accepted");
		}
		return check;
	}

	private boolean checkCreditCardNo(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")));
		if (!check)
		{
			System.out.println("Invalid Credit Card Number.");
			System.out.println("Format: xxxx-xxxx-xxxx-xxxx");
		}
		return check;
	}

}
