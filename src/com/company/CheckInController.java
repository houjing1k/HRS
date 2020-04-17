package com.company;

import java.time.LocalDate;
import java.util.ArrayList;

import com.company.RoomEntity.RoomStatus;
import com.company.RoomEntity.RoomType;

public class CheckInController extends Controller {
	private static CheckInController instance = null;
	
	private CheckInBoundary checkInBoundary;
	private GuestController guestController;
	private RoomController roomController;
	private MainController mainController;
	private PaymentController paymentController;
	
	private String[] menuMain = {
            "Walk In Check In",
            "Reservation Check In",
            "Check out"
    };
	
	private String[] menuWalkIn = {
			"New Guest",
			"Existing Guest"
	};
	
	private String [] menuGuestId = {
			"Enter Guest Id:"
	};
	
	private String [] menuRoomType = {
            "1. Single room",
            "2. Double room",
            "3. Deluxe room"
    };
	
	
	private CheckInController() {
		checkInBoundary = new CheckInBoundary();
		guestController = new GuestController();
		roomController = RoomController.getInstance();
		mainController = new MainController();
		paymentController = new PaymentController();
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
			checkInBoundary.setMenu(menuMain, "Check In/Out Menu");
			switch (checkInBoundary.process())
			{
				case 1:
					//go to check in
					loop = walkInCheckIn();
					break;
				case 2:
					//go to reserve check in
					loop = reserveCheckIn();
					break;
				case 3:
					//check out
					loop = checkOut();
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
		mainController.processMain(); 
	}
	
	private boolean reserveCheckIn() {
		// TODO Auto-generated method stub
		int reserveId =checkInBoundary.getId();
		RoomEntity room = roomController.getRerservation(reserveId);
		if(room!=null) {
			LocalDate startDate = checkInBoundary.getStartDate();
			LocalDate endDate = checkInBoundary.getStartDate();
			return checkIn(room.getGuestId(),room.getRoomId(),startDate,endDate);
		}
		else {
			System.out.println("Reservation not found");
			return true;
		}
	}

	private boolean checkOut() {
		// TODO Auto-generated method stub
		RoomEntity room = null;
		String roomId =checkInBoundary.getRoomId();
		try {
			room = roomController.getRoom(roomId);
			if(room.getRoomStatus()!= RoomStatus.OCCUPIED) {
				System.out.println("Room is not occupied");
				return true;
			}
		}catch(Exception e) {
			System.out.println("Invalid room Id");
			return true;
		}
		int guestID=room.getGuestId();
		//get the paymentDetail from guest. 
		//PaymentDetail paymentDetail=new PaymentDetail("Cash");
		//get the roomservice that this guest ordered
		GuestEntity guest = guestController.searchGuest(room.getGuestId());
		paymentController.addRoomServiceToPaymentBill(roomId);
		paymentController.makePayment(roomId,guest.getPaymentDetail());
		paymentController.removePaymentAccount(roomId);
		roomController.checkOut(roomId);
		System.out.println("Check out successful");
		return false;
	}

	private boolean walkInCheckIn() {
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
						return true;
					}
					break;
				case 0:
					//return
					return true;
				default:
					System.out.println("Invalid Input");
					loop = true;
			}
		}
		LocalDate startDate = checkInBoundary.getStartDate();
		LocalDate endDate = checkInBoundary.getStartDate();
		String roomId = selectRoom();
		return checkIn(guestId,roomId,startDate,endDate);
	}
	
	private boolean checkIn(int guestId,String roomId, LocalDate startDate, LocalDate endDate) {
		try {
			paymentController.createPaymentAccount(roomId);
			paymentController.addRoomToPaymentBill(roomId, startDate, endDate);
			roomController.checkIn(guestId, roomId);
			System.out.println("Check in successful");
			return false;
		}catch(Exception e) {
			System.out.println("Invalid inputs");
			return true;
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
