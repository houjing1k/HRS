package oodp;

import java.lang.reflect.Array;
import java.util.ArrayList;

import oodp.RoomEntity.RoomType;

public class CheckInController extends Controller {
	private static CheckInController instance = null;
	
	private CheckInBoundary checkInBoundary;
	private GuestController guestController;
	private RoomController roomController;
	
	private String[] menuMain = {
            "1. Walk In Check In",
            "2. Reservation Check In"
    };
	
	private String[] menuWalkIn = {
			"1. New Guest",
			"2. Existing Guest"
	};
	
	String [] menuGuestId = {
			"Enter Guest Id:"
	};
	
	String [] menuRoomType = {
            "Please select the room type:",
            "1. Single room",
            "2. Double room",
            "3. Deluxe room"
    };
	
	String [] menuRoomId = {
			"Enter Room Id:"
	};
	
	
	private CheckInController() {
		checkInBoundary = new CheckInBoundary();
		guestController = new GuestController();
		roomController = RoomController.getInstance();
	}
	
	public static CheckInController getInstance() {
		if (instance == null) {
            instance = new CheckInController();
        }
		return instance;
	}
	
	public void start() {
		switch (checkInBoundary.printMenu(menuMain))
		{
			case 1:
				//go to check in
				walkInCheckIn();
				break;
			case 2:
				//go to reserve check in
				break;
			case 3:
				//check out
				checkOut();
				break;
			default:
				break;
					
			
		}
	}
	
	private void checkOut() {
		// TODO Auto-generated method stub
		int roomId =checkInBoundary.printMenu(menuRoomId);
		roomController.checkOut(roomId);
		
	}

	private void walkInCheckIn() {
		int guestId;
		switch(checkInBoundary.printMenu(menuWalkIn))
		{
			case 1:
				 guestId = guestController.addGuest();
				 break;
				 
			case 2:
				guestId = checkInBoundary.printMenu(menuGuestId);
				GuestEntity guestObj = guestController.searchGuest(guestId);
				if(guestObj==null) {
					System.out.println("Invalid Input");
					return;
				}
				break;
			default:
				System.out.println("Invalid Input");
				return;
		}
		int roomId = selectRoom();
		try {
			roomController.checkIn(guestId, roomId);
		}catch(Exception e) {
			System.out.print("Invalid input");
		}
	}
	
	private int selectRoom() {
		RoomType roomType = null;
		switch(checkInBoundary.printMenu(menuRoomType))
		{
			case 1:
				roomType = RoomType.SINGLE;
				break;
			case 2:
				roomType = RoomType.DOUBLE;
				break;
			case 3:
				roomType = RoomType.DELUXE;
				break;
			default:
				System.out.println("Invalid Input");
				return 0;
		}
		ArrayList<Object> roomArray = roomController.listRooms(roomType);
		roomController.print(roomArray);
		int id = checkInBoundary.printMenu(menuRoomId);
		return id;
	}
}
