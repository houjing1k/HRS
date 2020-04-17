package com.company;

import java.util.ArrayList;
import java.util.HashMap;

import com.company.RoomEntity.RoomStatus;
import com.company.RoomEntity.RoomType;

public class RoomReports {
	
	private RoomReportBoundary roomReportBoundary;
	private static RoomReports instance = null;

	private RoomReports() {
		roomReportBoundary = new RoomReportBoundary();
	}
	public static RoomReports getInstance() {
		
		if (instance == null) {
            instance = new RoomReports();
        }
		return instance;
	}
	
	public void printReports() {
		boolean loop = true;
		while (loop)
		{
			int sel = roomReportBoundary.process();
			loop = false;
			switch (sel)
			{
				case 1:
					//print occupancy report
					printRoomTypeReport();
					break;
				case 2:
					//print room status report
					printRoomStatusReport();
					break;
				case 0:
					//new MainController().processMain();
					return;
				default:
					loop = true;
					roomReportBoundary.invalidInputWarning();
			}
		}
		roomReportBoundary.waitInput();
		//new MainController().processMain();
	}

	private void printRoomTypeReport() {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<RoomEntity>> reportData = new HashMap<String, ArrayList<RoomEntity>>();
		reportData.put("Single:", RoomController.getInstance().listRooms(RoomType.SINGLE));
		reportData.put("Double:",RoomController.getInstance().listRooms(RoomType.DOUBLE));
		reportData.put("Deluxe:",RoomController.getInstance().listRooms(RoomType.DELUXE));
		
		roomReportBoundary.printReport(reportData,true);
	}

	private void printRoomStatusReport() {
		// TODO Auto-generated method stub
	    HashMap<String, ArrayList<RoomEntity>> reportData = new HashMap<String, ArrayList<RoomEntity>>();
		reportData.put("Vacant:", RoomController.getInstance().listRooms(RoomStatus.VACANT));
		reportData.put("Occupied:",RoomController.getInstance().listRooms(RoomStatus.OCCUPIED));
		reportData.put("Reserved:",RoomController.getInstance().listRooms(RoomStatus.RESERVED));
		reportData.put("Maintenance:",RoomController.getInstance().listRooms(RoomStatus.MAINTENANCE));
		roomReportBoundary.printReport(reportData,false);
	}
}
