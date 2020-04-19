package com.company;

import java.util.Scanner;

import com.company.RoomEntity.BedType;

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
						"Change Room To Maintenance",
						"Change Room Bed Type",
						"Change Smoking Room",
						"Change Room Wifi",
						"Search Room by Room Id",
						"Search Room by guest"
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

	public String getRoomId() {
		// TODO Auto-generated method stub
		String id = "";
		do {
			System.out.println("Enter Room ID: ");
			id = sc.nextLine();
		}	while(!checkRoomId(id));
		return id;
	}
	
	private boolean checkRoomId(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("\\d{4}"))
				&& (str.matches("^[0-9]+$")));

		if (!check)
		{
			System.out.println("Invalid Room Id Number.");
			System.out.println("Format: xxxx");
		}
		return check;
	}

	public BedType getBedType() {
		// TODO Auto-generated method stub
		String[] bedType =
			{
					" 1 - Single          ",
					" 2 - Double Single   ",
					" 3 - Queen           ",
					" 4 - King            "
			};
		int sel;
		System.out.println("Choose bed Type:");
		printList(bedType);
		try {
			sel = sc.nextInt();
		}catch(Exception e) {
			return null;
		}
		switch(sel) {
			case 1:
				return BedType.SINGLE;
			case 2:
				return BedType.DOUBLESINGLE;
			case 3:
				return BedType.QUEEN;
			case 4:
				return BedType.KING;
			default:
				return null;
		}
	}

	public boolean getBooleanInput() {
		// TODO Auto-generated method stub
		String[] ans =
			{
					" 1 - Yes          ",
					" 2 - No  ",
			};
		int sel = 0;
		while(true) {
		printList(ans);
		try {
			sel = sc.nextInt();
		}catch(Exception e) {
			System.out.println("Invalid input");
		}
		switch(sel) {
			case 1:
				return true;
			case 2:
				return false;
			default:
				System.out.println("Invalid input");
		}
		}
	}

}
