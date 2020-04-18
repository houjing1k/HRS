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
						"Display All Rooms",
						"Display Single Rooms",
						"Display Occupied Rooms",
						"Display 1 Room",
						"Display Smoking Rooms",
						"Filter and Display Rooms"
				};
		printMenuList(menuList, "Go back to Administrative Control");
		System.out.println();
	}

	protected void addRoom()
	{

	}

	protected void filterRoom(boolean[] filter)
	{
		String[] roomStatus =
				{
						" 1 - Vacant       " + characterSelect(filter[0], '■', ' '),
						" 2 - Occupied     " + characterSelect(filter[1], '■', ' '),
						" 3 - Reserved     " + characterSelect(filter[2], '■', ' '),
						" 4 - Maintenance  " + characterSelect(filter[3], '■', ' ')
				};
		String[] bedType =
				{
						" 5 - Single       " + characterSelect(filter[4], '■', ' '),
						" 6 - Double       " + characterSelect(filter[5], '■', ' '),
						" 7 - Deluxe       " + characterSelect(filter[6], '■', ' ')
				};
		String[] smoking =
				{
						" 8 - Smoking      " + characterSelect(filter[7], '■', ' '),
						" 9 - Non-Smoking  " + characterSelect(filter[8], '■', ' ')
				};
		String[] wifi =
				{
						"10 - Yes          " + characterSelect(filter[9], '■', ' '),
						"11 - No           " + characterSelect(filter[10], '■', ' ')
				};
		String[] done =
				{
						" 0 - Done",
				};
		printMainTitle("Filter Rooms");
		System.out.println("[Room Status]");
		printList(roomStatus);
		System.out.println("[Room Type]");
		printList(bedType);
		System.out.println("[Smoking / Non-Smoking]");
		printList(smoking);
		System.out.println("[WIFI Enabled]");
		printList(wifi);
		printList(done);

	}

}
