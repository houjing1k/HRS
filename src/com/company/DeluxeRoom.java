package com.company;

public class DeluxeRoom implements TypesOfRooms {
	
	public DeluxeRoom() {
		
		}

	public RoomType createRoom() {
		return RoomType.DELUXE;
	}
	
	public BedType createBed() {
		return BedType.KING;
	}

}
