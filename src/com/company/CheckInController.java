package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.company.ReservationEntity.ReservationState;
import com.company.RoomEntity.RoomStatus;

public class CheckInController extends Controller {
	private static CheckInController instance = null;
	
	private CheckInBoundary checkInBoundary;
	private GuestController guestController;
	private RoomController roomController;
	private MainController mainController;
	
	private String[] menuMain = {
            "Walk In Check In",
            "Reservation Check In",
            "Check out"
    };
	
	private String[] menuWalkIn = {
			"New Guest",
			"Existing Guest"
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
		ReservationEntity reservation = null;
		LocalDate startDate = null;
		LocalDate endDate = null;
		try {
			reservation = new ReservationController().getReservationById(reserveId);
			startDate = reservation.startDate;
			endDate = reservation.endDate;
		}catch(Exception e) {
			System.out.println("Reservation not found");
			return true;
		}
		int numAdult = reservation.getNumOfAdults();
		int numChild = reservation.getNumOfChildren();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if(startDate.compareTo(LocalDate.now())!=0&&reservation.reservationState==ReservationState.CONFIRMED) {
			System.out.println("Your reservation is for "+startDate.format(formatter));
			return true;
		}
		try {
			RoomEntity room = roomController.getRoom(reservation.getRoomId());
			new ReservationController().checkInReservation(reserveId);
			return checkIn(room.getGuestId(),room.getRoomId(),startDate,endDate,numAdult,numChild);
		}catch(Exception e) {
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
		//get the paymentDetail from guest. 
		GuestEntity guest = guestController.searchGuest(room.getGuestId());
		new PaymentController().makePaymentMenu(roomId,guest.getPaymentDetail());
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
		startDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Check In Date : " +startDate.format(formatter));
		endDate = checkInBoundary.getEndDate(startDate);
		String roomId = selectRoom(startDate,endDate);
		int numAdult = checkInBoundary.selectNumAdult();
		int numChild = checkInBoundary.selectNumChild(numAdult);
		return checkIn(guestId,roomId,startDate,endDate,numAdult,numChild);
	}
	
	private boolean checkIn(int guestId,String roomId, LocalDate startDate, LocalDate endDate, int numAdult,int numChild) {
		try {
			new PaymentController().createBillingAccount(roomId);
			new PaymentController().addRoomToPaymentBill(roomId, startDate, endDate);
			roomController.checkIn(guestId, roomId, startDate, endDate,numAdult,numChild);
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
		ReservationController reservationController = new ReservationController();
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
		String id = checkInBoundary.selectRoom(returnArray);
		return id;
	}
}
