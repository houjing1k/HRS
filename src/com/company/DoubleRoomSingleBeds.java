package com.company;


public class DoubleRoomSingleBeds  implements TypesOfRooms{

	public DoubleRoomSingleBeds() {

		}

	public RoomType createRoom() {
		return RoomType.DOUBLE;
	}
	
	public BedType createBed() {
		return BedType.DOUBLESINGLE;
	}
}
