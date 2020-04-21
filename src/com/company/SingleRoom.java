package com.company;

import com.company.RoomEntity.RoomStatus;

public class SingleRoom extends RoomEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SingleRoom(String roomId, RoomStatus status, boolean smoking, boolean wifi) {
		
		super(roomId, status, smoking, wifi);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.SINGLE;
	}
	
	@Override
	public BedType getBedType() {
		return BedType.SINGLE;
	}
	
}
