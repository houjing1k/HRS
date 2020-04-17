package com.company;

import java.util.Scanner;

public class RoomBoundary extends Boundary
{
	private Scanner sc;

	public RoomBoundary()
	{
		sc = new Scanner(System.in);
	}

	protected void printMenu()
	{
		printMainTitle("Manage Rooms");
		String[] menuList =
				{
						"Add Rooms",
						"Remove Rooms",
				};
		printMenuList(menuList, "Go back to Administrative Control");
		System.out.println();
	}

	protected void addRoom()
	{

	}

}
