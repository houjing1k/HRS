package com.company;

import com.company.RoomEntity.RoomStatus;

public class SingleRoom extends RoomEntity implements TypesOfRooms {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SingleRoom(String roomId, RoomStatus status, boolean smoking, boolean wifi) {
		
		super(roomId, status, smoking, wifi);
		createRoom();
		// TODO Auto-generated constructor stub
	}
	

	public void createRoom() {
		this.roomType=RoomType.SINGLE;
		this.bedType=BedType.SINGLE;
	}
	

	
}
