package com.company;

import com.company.RoomEntity.RoomStatus;

public class DoubleRoomSingleBeds  extends RoomEntity{

	public DoubleRoomSingleBeds(String roomId, RoomStatus status, boolean smoking, boolean wifi) {
		
		super(roomId, status, smoking, wifi);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.DOUBLE;
	}
	
	@Override
	public BedType getBedType() {
		return BedType.DOUBLESINGLE;
	}

	
	
}