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
						"Display Smoking Rooms"
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

}
