package com.company;

import java.util.ArrayList;

import com.company.RoomEntity.RoomType;

public class CheckInController extends Controller {
	private static CheckInController instance = null;
	
	private CheckInBoundary checkInBoundary;
	private GuestController guestController;
	private RoomController roomController;
	private MainController mainController;
	
	private String[] menuWalkIn = {
			"New Guest",
			"Existing Guest"
	};
	
	String [] menuGuestId = {
			"Enter Guest Id:"
	};
	
	String [] menuRoomType = {
            "1. Single room",
            "2. Double room",
            "3. Deluxe room"
    };
	
	
	private CheckInController() {
		checkInBoundary = new CheckInBoundary();
		guestController = new GuestController();
		roomController = RoomController.getInstance();
		mainController = new MainController();
	}
	
	public static CheckInController getInstance() {
		if (instance == null) {
            instance = new CheckInController();
        }
		return instance;
	}
	
	public void processMain() {
		boolean loop = true;

		while (loop)
		{
			loop = false;
			switch (checkInBoundary.process())
			{
				case 1:
					//go to check in
					walkInCheckIn();
					break;
				case 2:
					//go to reserve check in
					reserveCheckIn();
					break;
				case 3:
					//check out
					checkOut();
					break;
				case 0:
					//return
					break;
				default:
					checkInBoundary.invalidInputWarning();
					loop = true;
					break;
			}
		}
	}
	
	private void reserveCheckIn() {
		// TODO Auto-generated method stub
		int reserveId =checkInBoundary.getId();
		RoomEntity room = roomController.getRerservation(reserveId);
		if(room!=null) {
			checkIn(room.getGuestId(),room.getRoomId());
		}
		else {
			System.out.println("Reservation not found");
			this.processMain();
		}
	}

	private void checkOut() {
		// TODO Auto-generated method stub
		String roomId =checkInBoundary.getRoomId();
		roomController.checkOut(roomId);
		System.out.println("Check out successful");
		mainController.processMain();
	}

	private void walkInCheckIn() {
		int guestId = 0;
		boolean loop = true;
		while(loop) {
			loop = false;
			checkInBoundary.setMenu(menuWalkIn, "Check In");
			switch(checkInBoundary.process())
			{
				case 1:
					guestId = guestController.addGuest();
					break;
				 
				case 2:
					checkInBoundary.setMenu(menuGuestId, "Check In");
					guestId = checkInBoundary.process();
					GuestEntity guestObj = guestController.searchGuest(guestId);
					if(guestObj==null) {
						System.out.println("Invalid Input");
						this.processMain();
						return;
					}
					break;
				case 0:
					//return
					mainController.processMain();
				default:
					System.out.println("Invalid Input");
					loop = true;
					return;
			}
		}
		String roomId = selectRoom();
		checkIn(guestId,roomId);
	}
	
	private void checkIn(int guestId,String roomId) {
		try {
			roomController.checkIn(guestId, roomId);
			System.out.println("Check in successful");
			mainController.processMain();
		}catch(Exception e) {
			System.out.println("Invalid inputs");
			this.processMain();
		}
	}
	
	private String selectRoom() {
		RoomType roomType = null;
		boolean loop = true;
		ArrayList<RoomEntity> roomArray = null;
		while(loop) {
			loop = false;
			checkInBoundary.setMenu(menuRoomType, "Please select the room type");
			switch(checkInBoundary.process())
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
				case 0:
					//return
					this.processMain();
					break;
					
				default:
					loop = true;
					System.out.println("Invalid Input");
			
			}
			roomArray = roomController.listRooms(roomType);	
			if(roomArray==null) {
				System.out.println("Invalid Input");
				loop = true;
			}
		}
		String id = checkInBoundary.printRooms(roomArray);
		return id;
	}
}
