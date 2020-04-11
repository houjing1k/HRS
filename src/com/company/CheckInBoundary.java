package com.company;

import java.util.ArrayList;
import java.util.Scanner;

import com.company.RoomEntity.RoomStatus;

public class CheckInBoundary extends Boundary {

	private String[] menu;
	private String title;
	private Scanner sc;
	public CheckInBoundary() {
		this.title = "Check In/Out Menu";
		this.menu = menuMain;
		sc = new Scanner(System.in);
	}
	
	@Override
	protected void printMenu() {
		// TODO Auto-generated method stub
		printMainTitle(title);
		printMenuList(menu, "Go back to Main Menu");
		System.out.println();
	}
	
	public void setMenu(String[] menu,String title) {
		this.menu = menu;
		this.title = title;
	}
	
	private String[] menuMain = {
            "Walk In Check In",
            "Reservation Check In",
            "Check out"
    };
	

	public int getId(boolean b) {
		// TODO Auto-generated method stub
		if(b)
			System.out.println("Enter Room ID: ");
		else
			System.out.println("Enter Reservation ID: ");
		int id = sc.nextInt();
		return id;
	}

	public int printRooms(ArrayList<RoomEntity> roomArray) {
		// TODO Auto-generated method stub
		char pre = 0;
		char cur = 0;
		ArrayList<RoomEntity> list = RoomController.getInstance().listRooms(RoomStatus.VACANT, roomArray);
		System.out.println("Avalible rooms");
		for(RoomEntity room:list) {
			cur = String.valueOf(room.getRoomId()).charAt(0);
			if(cur!=pre) {
				System.out.print("\nLevel "+cur+": ");
			}
			pre = cur;
			System.out.print(room.getRoomId());
			System.out.print(" ");
		}
		while(true) {
			System.out.println("\nEnter Room ID:");
			int id = sc.nextInt();
			for(RoomEntity room:list) {
				if(id==room.getRoomId()) {
					return id;
				}
			}
			System.out.println("Invalid room ID");
		}
	}
}
