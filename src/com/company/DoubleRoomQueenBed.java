package com.company;

import com.company.RoomEntity.RoomStatus;
import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

public class DoubleRoomQueenBed extends RoomEntity{
	
	public DoubleRoomQueenBed(String roomId, RoomStatus status, boolean smoking, boolean wifi) {
		
		super(roomId, status, smoking, wifi);
		// TODO Auto-generated constructor stub
		createRoom();
	}

	public void createRoom() {
		this.roomType=RoomType.DOUBLE;
		this.bedType=BedType.QUEEN;
	}
	

}