package com.company;

import java.io.Serializable;

public class DoubleRoomSingleBeds implements TypesOfRooms, Serializable
{

	public DoubleRoomSingleBeds()
	{

	}

	public RoomType createRoom()
	{
		return RoomType.DOUBLE;
	}

	public BedType createBed()
	{
		return BedType.DOUBLESINGLE;
	}
}
