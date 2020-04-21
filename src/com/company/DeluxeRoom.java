package com.company;

import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

public class DeluxeRoom implements TypesOfRooms {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeluxeRoom() {
		

		}


	
	public RoomType createRoom() {
		return RoomType.DELUXE;

	}
	public BedType createBed() {
		return BedType.KING;

	}

	

}
