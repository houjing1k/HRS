package com.company;


public class SingleRoom implements TypesOfRooms {

	public SingleRoom() {
				// TODO Auto-generated constructor stub
	}
	
	public RoomType createRoom() {
		return RoomType.SINGLE;
	}
	
	public BedType createBed() {
		return BedType.SINGLE;
	}
}
