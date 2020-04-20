package com.company;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import com.company.ReservationEntity.ReservationState;
import com.company.RoomEntity.RoomStatus;
import com.company.RoomEntity.RoomType;

public class CheckInController extends Controller {
	private static CheckInController instance = null;
	
	private CheckInBoundary checkInBoundary;
	private GuestController guestController;
	private RoomController roomController;
	private MainController mainController;
	private PaymentController paymentController;
	private ReservationController reservationController;
	
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
		reservationController  = new ReservationController();
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
		ReservationEntity reservation = reservationController.getReservationById(reserveId);
		if(room!=null) {
			LocalDate startDate = reservation.startDate;
			if(startDate.compareTo(LocalDate.now())!=0) {
				System.out.println("Your reservation is for "+startDate);
				return true;
			}
			LocalDate endDate = reservation.endDate;
			reservation.reservationState = ReservationState.CHECKED_IN;
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
		//get the roomservice that this guest ordered
		GuestEntity guest = guestController.searchGuest(room.getGuestId());
		paymentController.makePaymentMenu(roomId,guest.getPaymentDetail());
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
					GuestEntity guest = guestController.searchGuest_Hybrid();
					try {
						guestId = guest.getGuestID();
					}catch(Exception e) {
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
		LocalDate startDate,endDate;
		 while(true) {
				startDate = LocalDate.now();
				endDate = checkInBoundary.getEndDate(startDate);
		        if (Period.between(startDate, endDate).getDays()<1) 
		            System.out.println("Enter Valid Period of Day!");
		        else break;
		        }
		String roomId = selectRoom(startDate,endDate);
		return checkIn(guestId,roomId,startDate,endDate);
	}
	
	private boolean checkIn(int guestId,String roomId, LocalDate startDate, LocalDate endDate) {
		try {
			paymentController.createBillingAccount(roomId);
			paymentController.addRoomToPaymentBill(roomId, startDate, endDate);
			roomController.checkIn(guestId, roomId, startDate, endDate);
			System.out.println("Check in successful");
			return false;
		}catch(Exception e) {
			System.out.println("Invalid inputs");
			return true;
		}
	}
	
	private String selectRoom(LocalDate startDate, LocalDate endDate) {
		ArrayList<RoomEntity> roomArray;
		ArrayList<RoomEntity> returnArray = new ArrayList<>();
		while(returnArray.isEmpty()) {
			roomArray = roomController.filterRooms(1);
			for(RoomEntity room:roomArray) {
				if(reservationController.isRoomAvailable(room.getRoomId(), startDate, endDate)) {
					returnArray.add(room);
				}
			}
			if(returnArray.isEmpty()) {
				System.out.println("No rooms found");
			}
		}
		String id = checkInBoundary.printRooms(returnArray);
		return id;
	}
}
