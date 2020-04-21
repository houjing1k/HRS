package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class GuestBoundary extends Boundary
{
	private Scanner sc;
	private String[] list = {"Name", "Address", "Country", "Gender", "Identity No.", "Nationality", "Contact No.","Card Details"};

	public GuestBoundary()
	{
		sc = new Scanner(System.in);
	}

	protected void printMenu()
	{
		printMainTitle("Manage Guests");
		String[] menuList =
				{
						"Add new Guest",
						"Remove Guest",
						"Update Guest Details",
						"Search Guests by Name",
						"Search Guests by Guest ID",
						"Print all Guests",
						//"Test"
				};
		printMenuList(menuList, "Go back to Administrative Control");
		System.out.println();
	}

	public void addGuest_head()
	{
		printSubTitle("Add New Guest");
	}

	public void removeGuest_head()
	{
		printSubTitle("Remove Guest");
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

	public int updateGuest()
	{
		sc = new Scanner(System.in);
		String input = null;
		while (true)
		{
			try
			{
				printSubTitle("Enter Field to Update");
				printMenuList(list, "Back to menu");

				input = sc.next();
				if (!input.matches("^[0-8]+$"))
					throw new Exception();
				else
					return Integer.parseInt(input);
			} catch (Exception e)
			{
				System.out.println("Invalid choice.");
			}

		}
	}

	public String addUpdateGuestSub(int sel, String prefix)
	{
		sc = new Scanner(System.in);
		String input = null;
		boolean check = true;
		while (check)
		{
			try
			{
				if (prefix != null)
				{
					System.out.println(prefix + " " + list[sel - 1] + ":");
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
						input = input.toUpperCase();
						if (!checkGender(input)) throw new Exception();
						break;
					case 5:
						input = input.toUpperCase();
						if (!checkIdentityNo(input)) throw new Exception();
						break;
					case 6:
						if (!checkNationality(input)) throw new Exception();
						break;
					case 7:
						if (!checkContactNo(input)) throw new Exception();
						break;
				}

			} catch (Exception e)
			{
				check = true;
			}
		}
		return input;
	}

	public String[] updateCardDetails()
	{
		String[] cardDetails = new String[3];
		boolean loop = true;

		printSubTitle("Add/Modify Card Details");

		while (loop)
		{
			sc = new Scanner(System.in);
			System.out.println("Card Name: ");
			cardDetails[0] = sc.nextLine();
			System.out.println("Card No.: ");
			cardDetails[1] = sc.nextLine();
			System.out.println("Expiry Date: ");
			cardDetails[2] = sc.nextLine();
			if (checkCardDetails(cardDetails))
			{
				loop = false;
			}
		}
		return cardDetails;
	}

	public boolean confirmation(GuestEntity guest)
	{
		sc = new Scanner(System.in);
		String choice;
		while (true)
		{
			try
			{
				System.out.println();

				printGuest(guest);

				System.out.println("Please confirm the above:");
				System.out.println("(Y - Accept, N - Decline):");

				choice = sc.next();

				if (choice.equals("Y") || choice.equals("y"))
				{
					System.out.println("Changes accepted.");
					return true;
				}
				else if (choice.equals("N") || choice.equals("n"))
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
			System.out.println("Invalid input. Only numeric accepted");
		}
		return check;
	}

	private boolean checkCardDetails(String[] str)
	{
		boolean check1, check2, check3;
		check1 = ((!str[0].equals(""))
				&& (str[0] != null)
				&& (str[0].matches("^[ A-Za-z]+$")));
		if (!check1)
		{
			System.out.println("Invalid Name. Only alphabetical accepted");
		}

		check2 = ((!str[1].equals(""))
				&& (str[1] != null)
				&& (str[1].matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")));
		if (!check2)
		{
			System.out.println("Invalid Card Number.");
			System.out.println("Format: xxxx-xxxx-xxxx-xxxx");
		}

		check3 = ((!str[2].equals(""))
				&& (str[2] != null)
				&& (str[2].matches("^(0[1-9]|1[0-2])\\/?([0-9]{2})$")));
		if (!check3)
		{
			System.out.println("Invalid Expiry Date.");
			System.out.println("Format: MM/YY");
		}
		return check1 && check2 && check3;
	}
}
