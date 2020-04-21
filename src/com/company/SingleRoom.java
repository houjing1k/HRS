package com.company;


import java.io.Serializable;

public class SingleRoom implements TypesOfRooms, Serializable
{

	public SingleRoom()
	{
		// TODO Auto-generated constructor stub
	}

	public RoomType createRoom()
	{
		return RoomType.SINGLE;
	}

	public BedType createBed()
	{
		return BedType.SINGLE;
	}
}
