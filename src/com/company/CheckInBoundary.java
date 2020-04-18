package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
		String id = "";
		do {
			System.out.println("Enter Room ID: ");
			id = sc.nextLine();
		}	while(!checkRoomId(id));
		return id;
	}
	
	public LocalDate getEndDate(LocalDate startDate) {
		// TODO Auto-generated method stub
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	LocalDate endDate = null;
    	while(true) {
    		try {
    			System.out.println("\nEnter end date: (dd/MM/yyyy)");
    			endDate = LocalDate.parse(sc.next(),formatter);
    			if(endDate.compareTo(startDate)>=0) {
    				return endDate;
    			}else {
    				System.out.println("Invalid Date");
    			}
    		} 	catch (DateTimeParseException exc) {
    			System.out.println("Please enter the date in this format (dd/MM/yyyy)");
    		}
    	}
	}

	public String printRooms(ArrayList<RoomEntity> roomArray) {
		// TODO Auto-generated method stub
		String pre = "";
		String curLevel = "";
		String num = "";
		System.out.println("Avalible rooms");
		for(RoomEntity room:roomArray) {
			curLevel = room.getRoomId().substring(0, 2);
			num = room.getRoomId().substring(2, 4);
			if(!curLevel.equals(pre)) {
				System.out.print("\nLevel "+curLevel+": ");
			}
			pre = curLevel;
			System.out.print(curLevel+"-"+num);
			System.out.print(" ");
		}
		System.out.println(" ");
		sc.nextLine();

		while(true) {
			String id = getRoomId();
			for(RoomEntity room:roomArray) {
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
				&& (str.matches("\\d{4}"))
				&& (str.matches("^[0-9]+$")));

		if (!check)
		{
			System.out.println("Invalid Room Id Number.");
			System.out.println("Format: xxxx");
		}
		return check;
	}

	public int getId() {
		// TODO Auto-generated method stub
		int id;
		while(true) {
			try {
				System.out.println("Enter Reservation ID: ");
				id = sc.nextInt(); 
				return id;
			}catch(Exception e) {
				sc.nextLine();
				System.out.println("Invalid reservation id");
			}
		}
	}
}
