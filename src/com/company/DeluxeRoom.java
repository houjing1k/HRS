package com.company;


import java.io.Serializable;

public class DeluxeRoom implements TypesOfRooms, Serializable
{

	public DeluxeRoom()
	{

	}

	public RoomType createRoom()
	{
		return RoomType.DELUXE;
	}

	public BedType createBed()
	{
		return BedType.KING;
	}
}
