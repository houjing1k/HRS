package com.company;

import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

public interface TypesOfRooms {
	/**
	 *
	 *
	 */
	public enum BedType{
			//Bed type: King, Queen, DoubleSingle, Single
			KING,QUEEN,DOUBLESINGLE,SINGLE;
		}
		
	public enum RoomType{
			//Single room 
			SINGLE,
			//Double room 
			DOUBLE,
			//DELUXE room
			DELUXE;

	}

	public RoomType createRoom();
	public BedType createBed() ;
	


	
}