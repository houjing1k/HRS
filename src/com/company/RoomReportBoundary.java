package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.company.RoomEntity.RoomStatus;

public class RoomReportBoundary  extends Boundary{
	private Scanner sc;
	
	public RoomReportBoundary()
	{
		sc = new Scanner(System.in);
	}
	
	protected void printMenu()
	{
		printMainTitle("Print Occupancy Reports");
		String[] menuList =
				{
						"Print Room Type Occupancy Report",
						"Print Room Status Occupancy Report"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
	
	public void printReport(HashMap<String, ArrayList<RoomEntity>> reportData, boolean b) {
		for(String key: reportData.keySet()){
			System.out.println(key);
			if(b) {
				System.out.print("Numbers: ");
				System.out.print(RoomController.getInstance().listRooms(RoomStatus.VACANT, reportData.get(key)).size());
				System.out.print(" out of "+reportData.get(key).size());
			}

			System.out.print("\nrooms:");
			if(reportData.get(key).isEmpty()) {
				System.out.print("NIL");
			}
			else {
			     for(RoomEntity val : reportData.get(key) ){
			    	 System.out.print(val.getRoomId()+",");
			     }
			}
			System.out.println("\n");
		}
	}

	
}
