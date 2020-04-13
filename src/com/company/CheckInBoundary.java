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
	

	public String getRoomId() {
		// TODO Auto-generated method stub
		String id = "";
		do {
			System.out.println("\nEnter Room ID: ");
			id = sc.nextLine();
		}	while(!checkRoomId(id));
		return id;
	}

	public String printRooms(ArrayList<RoomEntity> roomArray) {
		// TODO Auto-generated method stub
		String pre = "";
		String cur = "";
		ArrayList<RoomEntity> list = RoomController.getInstance().listRooms(RoomStatus.VACANT, roomArray);
		System.out.println("Avalible rooms");
		for(RoomEntity room:list) {
			cur = room.getRoomId().substring(0, 2);
			if(!cur.equals(pre)) {
				System.out.print("\nLevel "+cur+": ");
			}
			pre = cur;
			System.out.print(room.getRoomId());
			System.out.print(" ");
		}
		while(true) {
			String id = getRoomId();
			for(RoomEntity room:list) {
				if(id.equals(room.getRoomId())) {
					return id;
				}
			}
			System.out.println("Invalid room ID");
		}
	}
	
	private boolean checkRoomId(String str)
	{
		boolean check = ((!str.equals(""))
				&& (str != null)
				&& (str.matches("\\d{2}-\\d{2}")));
		if (!check)
		{
			System.out.println("Invalid Room Id Number.");
			System.out.println("Format: xx-xx");
		}
		return check;
	}

	public int getId() {
		// TODO Auto-generated method stub
		System.out.println("Enter Reservation ID: ");
		return sc.nextInt();
	}
}
