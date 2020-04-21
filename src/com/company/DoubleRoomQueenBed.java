package com.company;

import java.io.Serializable;

public class DoubleRoomQueenBed implements TypesOfRooms, Serializable
{

	public DoubleRoomQueenBed()
	{

	}

	public RoomType createRoom()
	{
		return RoomType.DOUBLE;
	}

	public BedType createBed()
	{
		return BedType.QUEEN;
	}
}