package com.company;

import java.io.Serializable;
import java.time.LocalDate;


import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

public class RoomEntity implements Serializable, Comparable<RoomEntity>{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final String roomId;
	private  RoomStatus status;
	private boolean smoking;
	private boolean wifi;
	private int guestId;
	private int reserveId;
	private int numAdult = 0;
	private int numChild = 0;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	protected RoomType roomType;
	protected BedType bedType;
	

	public RoomEntity(String roomId,RoomStatus status,boolean smoking,boolean wifi) {
		this.roomId = roomId;
		this.status = status;
		this.smoking = smoking;
		this.wifi = wifi;
		this.checkOutDate = null;
	}
	
	public RoomEntity(String roomId,RoomType roomType,RoomStatus status,BedType bedType,boolean smoking,boolean wifi) {
		this.roomId = roomId;
		this.roomType = roomType;
		this.status = status;
		this.bedType = bedType;
		this.smoking = smoking;
		this.wifi = wifi;
		this.checkOutDate = null;
	}
	
	//Method to get room type
	public  RoomType getRoomType() {
		return roomType;
	}
	
	//Method to get bed type
	public  BedType getBedType() {
		return bedType;
	}
	
	//Method to change bed type
	public void setBedType(BedType bedtype) {
		this.bedType = bedtype;
	}
	//Method to change room type
	public void setRoomType(RoomType Roomtype) {
		this.roomType = Roomtype;
	}
	
	//Method to get numGuest
	public int getNumGuest() {return numAdult+numChild;}
	
	//Method to get numGuest
	public int getNumAdult() {return numAdult;}
	
	//Method to get numGuest
	public int getNumChild() {return numChild;}
	//Method to get room id
	public String getRoomId() {return roomId;}


	//Method to get room status
	public RoomStatus getRoomStatus() {return status;}


	//Method to check if room allows smoking
	public boolean isSmoking() {return smoking;}

	//Method to check if the room have wifi
	public boolean isWifi() {return wifi;}

	//Method to get reservation id
	public int getReserveId() {return reserveId;}

	//Method to get guest id
	public int getGuestId() {return guestId;}

	//Method to get Check-out Date
	public LocalDate getCheckInDate()
	{
		return checkInDate;
	}

	//Method to get Check-out Date
	public LocalDate getCheckOutDate()
	{
		return checkOutDate;
	}

	//Method to check if room is reserved
	public boolean isReserved() {return(this.getRoomStatus()==RoomStatus.RESERVED);}

	//Method to check if room is occupied
	public boolean isOccupied() {return(this.getRoomStatus()==RoomStatus.OCCUPIED);}

	//Method to check if room is vacant
	public boolean isVacant() {return(this.getRoomStatus()==RoomStatus.VACANT);}

	//Method to check if room is under maintenance
	public boolean isMaintenance() {return(this.getRoomStatus()==RoomStatus.MAINTENANCE);}



	//Method to change smoking 
	public void setSmoking(boolean b) {
		this.smoking = b;
	}

	//Method to change wifi
	public void setWIfi(boolean b) {
		this.wifi = b;
	}

	//Method to check in
	public void checkIn(int guest, LocalDate checkInDate, LocalDate checkOutDate, int numAdult,int numChild) {
		this.guestId = guest;
		this.status = RoomStatus.OCCUPIED;
		this.checkOutDate = checkOutDate;
		this.checkInDate = checkInDate;
		this.numAdult = numAdult;
		this.numChild = numChild;
	}

	//Method to reserve 
	public void reserve(int guestId,int reserveId) {
		this.guestId = guestId;
		this.status = RoomStatus.RESERVED;
		this.reserveId = reserveId;
	}

	//Method to change the room status to maintenance
	public void maintenance() {
		this.status = RoomStatus.MAINTENANCE;
	}

	//Method to check out
	public void checkOut() {
		this.status = RoomStatus.VACANT;
		checkOutDate = null;
		checkInDate = null;
	}

	//method to print string 
	public String toString() {
		return("\nRoom ID: "+this.getRoomId()+
				"\nGuest Id: "+this.getGuestId()+
				"\nRoom Status: "+this.getRoomStatus()+
				"\nRoom Type: "+this.getRoomType()+
				"\nBed Type: "+this.getBedType()+
				"\nSmoking: "+this.isSmoking())+
				"\nWIFI:"+this.isWifi();
	}


	public enum RoomStatus{
		//Room status: reserved, occupied, vacant, maintenance
		RESERVED,OCCUPIED,VACANT,MAINTENANCE;

	}
	

	
	@Override
	public int compareTo(RoomEntity o)
	{
		return this.roomId.compareTo(o.getRoomId());
	}



	
}