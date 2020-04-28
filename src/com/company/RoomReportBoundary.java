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
		printMenuList(menuList, "Go Back");
		System.out.println();
	}
	
	public void printReport(HashMap<String, ArrayList<RoomEntity>> reportData, boolean b) {
		String level = "";
		String num = "";
		String pre = "";
		for(String key: reportData.keySet()){
			System.out.println(key);
			if(b) {
				System.out.print("Numbers : ");
				System.out.print(RoomController.getInstance().listRooms(RoomStatus.VACANT, reportData.get(key)).size());
				System.out.print(" out of "+reportData.get(key).size());
			}

			System.out.print("\nRooms : ");
			if(reportData.get(key).isEmpty()) {
				System.out.print("NIL");
			}
			else {
				
			     for(RoomEntity val : reportData.get(key) ){
			    	    level = val.getRoomId().substring(0, 2);
						num = val.getRoomId().substring(2, 4);
						if(!level.equals(pre)) {
							System.out.print("\nLevel "+level+" : ");
						}
						pre = level;
						System.out.print(level+"-"+num);
						System.out.print(" ");
			     }
			}
			System.out.println("\n");
		}
	}

	
}
