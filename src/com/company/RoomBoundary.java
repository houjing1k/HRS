package com.company;

import java.util.ArrayList;
import java.util.Scanner;

import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

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
						"Search Room by Room ID",
						"Search Room by Guest",
						"Show number of guest"
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
		sel = getInput(1,4);
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
		int sel;
		printList(ans);
		sel = getInput(1,2);
		if(sel==1) {
			return true;
		}else {
			return false;
		}
		
	}

	public RoomType getRoomType() {
		// TODO Auto-generated method stub
		String[] bedType =
			{
					" 1 - Single          ",
					" 2 - Double    	",
					" 3 - Deluxe           ",
			};
		int sel;
		System.out.println("Choose bed Type:");
		printList(bedType);
		sel = getInput(1,3);
		
		switch(sel) {
			case 1:
				return RoomType.SINGLE;
			case 2:
				return RoomType.DOUBLE;
			case 3:
				return RoomType.DELUXE;
			default:
				return null;
				
		}
	}

	public void printNumGuest(ArrayList<RoomEntity> roomList) {
		// TODO Auto-generated method stub
		String level= "";
		String num = "";
		String pre = "02";
		int totalGuest = 0;
		int totalAdult = 0;
		int totalChild = 0;
		printMainTitle("Number of guests");
		for(RoomEntity room:roomList) {
			level = room.getRoomId().substring(0, 2);
			if(!level.equals(pre)) {
				System.out.println("\nLevel "+pre+": ");
				System.out.println("Total Number of Guest: "+totalGuest);
				System.out.println("Total Number of Adult: "+totalAdult);
				System.out.println("Total Number of Child: "+totalChild);
				pre = level;
				totalGuest = 0;
				totalAdult = 0;
				totalChild = 0;
			}
			totalGuest += room.getNumGuest();
			totalAdult += room.getNumAdult();
			totalChild += room.getNumChild();
		}
		System.out.println("\nLevel "+level+": ");
		System.out.println("Total Number of Guest: "+totalGuest);
		System.out.println("Total Number of Adult: "+totalAdult);
		System.out.println("Total Number of Child: "+totalChild);
	}

}
