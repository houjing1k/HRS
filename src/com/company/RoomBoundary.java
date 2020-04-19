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
						"Filter and Display Rooms",
						"Test Select Rooms"
				};
		printMenuList(menuList, "Go back to Administrative Control");
		System.out.println();
	}


	protected void filterRoom(boolean[] filter, boolean mode)
	{
		char check = getDesign()[8];

		String[] roomType =
				{
						" 1 - Single          " + characterSelect(filter[0], check, ' '),
						" 2 - Double          " + characterSelect(filter[1], check, ' '),
						" 3 - Deluxe          " + characterSelect(filter[2], check, ' ')
				};
		String[] bedType =
				{
						" 4 - Single          " + characterSelect(filter[3], check, ' '),
						" 5 - Double Single   " + characterSelect(filter[4], check, ' '),
						" 6 - Queen           " + characterSelect(filter[5], check, ' '),
						" 7 - King            " + characterSelect(filter[6], check, ' ')
				};
		String[] smoking =
				{
						" 8 - Smoking         " + characterSelect(filter[7], check, ' '),
						" 9 - Non-Smoking     " + characterSelect(filter[8], check, ' ')
				};
		String[] wifi =
				{
						"10 - Yes             " + characterSelect(filter[9], check, ' '),
						"11 - No              " + characterSelect(filter[10], check, ' ')
				};
		String[] roomStatus =
				{
						"12 - Vacant          " + characterSelect(filter[11], check, ' '),
						"13 - Occupied        " + characterSelect(filter[12], check, ' '),
						"14 - Reserved        " + characterSelect(filter[13], check, ' '),
						"15 - Maintenance     " + characterSelect(filter[14], check, ' ')
				};
		String[] done =
				{
						" 0 - Done",
				};
		printMainTitle("Filter Rooms");
		System.out.println("[Room Type]");
		printList(roomType);
		System.out.println("[Bed Type]");
		printList(bedType);
		System.out.println("[Smoking / Non-Smoking]");
		printList(smoking);
		System.out.println("[WIFI Enabled]");
		printList(wifi);
		if (!mode)
		{
			System.out.println("[Room Status]");
			printList(roomStatus);
		}
		printList(done);

	}

}
