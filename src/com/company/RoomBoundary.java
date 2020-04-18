package com.company;

import java.util.Scanner;

public class RoomBoundary extends Boundary
{
	private Scanner sc;
	
	private String [] titleList = {
			"return",
            "Select Room Type:",
            "Select Bed Type:",
            "Smoking:",
            "WIFI:"
    };
	
	private String [] roomTypeList = {
            "Single room",
            "Double room",
            "Deluxe room"
    };
	
	private String [] bedTypeList = {
            "Single bed",
            "Double bed",
            "Deluxe room"
    };
	
	private String [] ans = {
           "Yes",
           "No"
    };
	
	
	public RoomBoundary() {
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
	
	public int[] selectRoomMenu() {
		int[] selection = new int[4];
		int i = 1;
		int numSel = 0;
		String title;
		printMainTitle("Select Room");
		while(i<5) {
			title = titleList[i];
			System.out.println(title);
			switch(title)
			{
				case "Select Room Type:":
					printMenuList(roomTypeList, "exit");
					numSel = roomTypeList.length;
					break;
				case "Select Bed Type:":
					printMenuList(bedTypeList, "back");
					numSel = bedTypeList.length;
					break;
				case "Smoking:":
					printMenuList(ans, "back");
					numSel = ans.length;
					break;
				case "WIFI:":
					printMenuList(ans, "back");
					numSel = ans.length;
					break;
				case"return":
				default:
					return null;
					
			}
			try {
				selection[i-1] = sc.nextInt();
				if(selection[i-1]==0) {
					i--;
				}else if(selection[i-1]<=numSel) {
					i++;
				}else {
					System.out.println("Invalid Input");
				}
			}catch(Exception e) {
				sc.nextLine();
				System.out.println("Invalid Input");
			}
	

		}
		return selection;
		
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
