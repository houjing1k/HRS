package com.company;

import java.util.ArrayList;

import com.company.RoomEntity.RoomStatus;

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
			for (int i = 0; i < 2; i++)
			{
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				singleRoom=new SingleRoom();	//create single room with single bed
				addTolist(new RoomEntity(roomId,singleRoom.createRoom(),RoomStatus.VACANT,singleRoom.createBed(), smoking, wifi));
				
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				doubleRoomQBed = new DoubleRoomQueenBed(); //Double room with queen size bed
				addTolist(new RoomEntity(roomId,doubleRoomQBed.createRoom(),RoomStatus.VACANT,doubleRoomQBed.createBed(), smoking, wifi));
				
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				deluxeRoom = new DeluxeRoom(); //deluxe room with king size bed
				addTolist(new RoomEntity(roomId,deluxeRoom.createRoom(),RoomStatus.VACANT,deluxeRoom.createBed(), smoking, wifi));

				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				doubleRoomSBeds = new DoubleRoomSingleBeds(); //Double Room with single bed
				addTolist(new RoomEntity(roomId,doubleRoomSBeds.createRoom(),RoomStatus.VACANT,doubleRoomSBeds.createBed(), smoking, wifi));
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
