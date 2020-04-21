package com.company;

public class DoubleRoomQueenBed implements TypesOfRooms{
	
	public DoubleRoomQueenBed() {
		
	}

	public RoomType createRoom() {
		return RoomType.DOUBLE;
	}
	
	public BedType createBed() {
		return BedType.QUEEN;
	}
}