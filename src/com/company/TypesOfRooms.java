package com.company;



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

	//Method to get room type
	public RoomType getRoomType();
	
	//Method to get bed type
	public BedType getBedType() ;

	


	
}