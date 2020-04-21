package com.company;

import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

public class DeluxeRoom extends RoomEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeluxeRoom(String roomId, RoomStatus status, boolean smoking, boolean wifi) {
		
		super(roomId, status, smoking, wifi);
		createRoom();
		}


	public void createRoom() {
		this.roomType=RoomType.DELUXE;
		this.bedType=BedType.KING;
	}
	
	
	

}
