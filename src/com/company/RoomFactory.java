package com.company;

import java.util.ArrayList;

import com.company.RoomEntity.RoomStatus;
import com.company.TypesOfRooms.BedType;
import com.company.TypesOfRooms.RoomType;

public class RoomFactory extends Controller{
	private ArrayList<RoomEntity> roomList;
	SingleRoom singleRoom;
	DoubleRoomQueenBed doubleRoomQBed;
	DoubleRoomSingleBeds doubleRoomSBeds;
	DeluxeRoom deluxeRoom;
	public RoomFactory() {
		System.out.println("Default Rooms Created");
		roomList = new ArrayList<RoomEntity>();
		processMain();   //Create Room
	
	}
	
	public ArrayList<RoomEntity> getRoomList(){
		return roomList;
	}
	
	@Override    //CreateRoom
	public void processMain() {

		//level 2 rooms
		int level = 2;
		int room = 0;
		String roomId = "";
		boolean smoking = false;
		boolean wifi = true;
		for (int j = 0; j < 6; j++)
		{
			if(level>6) {
				wifi = false;
			}
			int x = 0;
			for (int i = 0; i < 2; i++)
			{
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				singleRoom = new SingleRoom(roomId,  RoomStatus.VACANT, smoking, wifi);
				addTolist(singleRoom);
				
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				doubleRoomQBed = new DoubleRoomQueenBed(roomId,  RoomStatus.VACANT, smoking, wifi);
				addTolist(doubleRoomQBed);
				
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				deluxeRoom = new DeluxeRoom(roomId,  RoomStatus.VACANT, smoking, wifi);
				addTolist(deluxeRoom);

				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				doubleRoomSBeds = new DoubleRoomSingleBeds(roomId,  RoomStatus.VACANT, smoking, wifi);
				addTolist(doubleRoomSBeds);
			}
			level += 1;
			room = 0;
			smoking = !smoking;
		}
	}
	
	

	//Load object into the array list
	private void addTolist(RoomEntity rm)
	{	
		roomList.add(rm);
		roomList.sort(null);

	}



}
