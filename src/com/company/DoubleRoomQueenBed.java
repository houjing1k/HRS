package com.company;

import com.company.RoomEntity.RoomStatus;
import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

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