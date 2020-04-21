package com.company;

import com.company.RoomEntity.RoomStatus;
import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

public class DoubleRoomSingleBeds  extends RoomEntity{

	public DoubleRoomSingleBeds(String roomId, RoomStatus status, boolean smoking, boolean wifi) {
		
		super(roomId, status, smoking, wifi);
		createRoom();
		}

	public void createRoom() {
		this.roomType=RoomType.DOUBLE;
		this.bedType=BedType.DOUBLESINGLE;
	}
	
	
	
}
