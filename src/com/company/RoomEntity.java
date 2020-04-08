package com.company;

import java.io.Serializable;

public class RoomEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int roomId;
	private final RoomType roomType;
	private  RoomStatus status;
	private final BedType bedType;
	private boolean smoking;
	private int guestId;
	private int reserveId;
	
	public RoomEntity(int roomId,RoomType roomType,RoomStatus status,BedType bedType,boolean smoking) {
		this.roomId = roomId;
		this.roomType = roomType; 
		this.status = status;
		this.bedType = bedType;
		this.smoking = smoking;
		
	}
	//Method to get room id
	public int getRoomId() {return roomId;}
	
	//Method to get room type
	public RoomType getRoomType() {return roomType;}
	
	//Method to get room status
	public RoomStatus getRoomStatus() {return status;}
	
	
	//Method to get bed type
	public BedType getBedType() {return bedType;}
	
	//Method to check if room allows smoking
	public boolean isSmoking() {return smoking;}
	
	//Method to get the cost of the room
	public int getCost() {return roomType.cost;}
	
	//Method to get reservation id
	public int getReserveId() {return reserveId;}
	
	//Method to get guest id
	public int getGuestId() {return guestId;}
	
	//Method to check if room is reserved
	public boolean isReserved() {return(this.getRoomStatus()==RoomStatus.RESERVED);}
	
	//Method to check if room is occupied
	public boolean isOccupied() {return(this.getRoomStatus()==RoomStatus.OCCUPIED);}
	
	//Method to check if room is vacant
	public boolean isVacant() {return(this.getRoomStatus()==RoomStatus.VACANT);}
	
	//Method to check if room is under maintenance
	public boolean isMaintenance() {return(this.getRoomStatus()==RoomStatus.MAINTENANCE);}
	
	//Method to check in
	public void checkIn(int guest) {
		this.guestId = guest;
		this.status = RoomStatus.OCCUPIED;
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
	}
	
	//method to print string 
	@Override
	public String toString() {
		return("Room ID: "+this.getRoomId()+", Room Status: "+this.getRoomStatus());
	}
	
	public enum RoomType{
		//Single room , cost = $100
		SINGLE(100),
		//Double room , cost = $150
		DOUBLE(150),
		//DELUXE room, cost = $200
		DELUXE(200);
		
		int cost;
		
		RoomType(int cost){
			this.cost = cost;
		}
	}
	
	public enum RoomStatus{
		//Room status: reserved, occupied, vacant, maintenance
		RESERVED,OCCUPIED,VACANT,MAINTENANCE;
		
	}
	
	public enum BedType{
		//Bed type: King, Queen, DoubleSingle, Single
		KING,QUEEN,DOUBLESINGLE,SINGLE;
	}
}