package com.company;

import java.util.ArrayList;

import com.company.RoomEntity.BedType;
import com.company.RoomEntity.RoomStatus;
import com.company.RoomEntity.RoomType;

public class RoomController extends Controller {

	private static RoomController instance = null;
	private ArrayList<RoomEntity> roomList;
	
	//Create only one instance of the object
	@SuppressWarnings("unchecked")
	private RoomController() {
		roomList = (ArrayList<RoomEntity>)fromFile("room.txt");
		if(roomList == null) {
			roomList = new ArrayList<>();
		}
	}
	
	public static RoomController getInstance() {
		
		if (instance == null) {
            instance = new RoomController();
        }
		return instance;
	}
	
	//Load object into the array list
	public void loadObject(int id,RoomType roomType,RoomStatus status,BedType bedType,boolean smoking) {
		
		RoomEntity rm = new RoomEntity(id,roomType,status,bedType,smoking);
		roomList.add(rm);
		saveFile();
	}
	

	//Print function used for testing
	public <T> void print(ArrayList<T> list) {
		if(!list.isEmpty()){
			for(T room:list) {
				System.out.println(room);
			}
		}
		else {
			System.out.println("No availble Rooms");
		}
	}

	//Return the list based on room type
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> listRooms(RoomType roomType){
		ArrayList<T> list = new ArrayList<>();
		for(RoomEntity room:roomList) {
			if(room.getRoomType() == roomType) {
				list.add((T) room);
			}
		}
		return list;
	}
	
	//Return the list based on room status
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> listRooms(RoomStatus status){
		ArrayList<T> list = new ArrayList<>();
		for(RoomEntity room:roomList) {
			if(room.getRoomStatus() == status) {
				list.add((T) room);
			}
		}
		return list;	
	}
	
	//Return the list based on bed type
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> listRooms(BedType bedType){
		ArrayList<T> list = new ArrayList<>();
		for(RoomEntity room:roomList) {
			if(room.getBedType() == bedType) {
				list.add((T) room);
			}
		}
		return list;
	}
	
	//Return the list based on room id
	public RoomEntity listRoom(int roomId){
		for(RoomEntity room:roomList) {
			if(room.getRoomId() == roomId) {
				return room;
			}
		}
		return null;
	}
	
	//Return the list based on smoking
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> listRooms(boolean smoking){
		ArrayList<T> list = new ArrayList<>();
		for(RoomEntity room:roomList) {
			if(room.isSmoking() == smoking) {
				list.add((T) room);
			}
		}
		return list;
	}
	
	
	public void saveFile() {
		toFile(roomList,"room.txt");
	}

	public void checkIn(int guestId, int roomId) {
		// TODO Auto-generated method stub
		this.listRoom(roomId).checkIn(guestId);
		saveFile();
	}
	
	public void checkOut(int roomId) {
		this.listRoom(roomId).checkOut();
		saveFile();
	}
	
	public void reserve(int roomId,String reserveId) {
		this.listRoom(roomId).reserve(reserveId);
		saveFile();
	}


}
