package com.company;

import java.util.ArrayList;

import com.company.RoomEntity.BedType;
import com.company.RoomEntity.RoomStatus;
import com.company.RoomEntity.RoomType;


public class RoomController extends Controller
{

	private static RoomController instance = null;
	private ArrayList<RoomEntity> roomList = null;
	private RoomReports roomReports = null;
	private String roomFile = "./data/Rooms.ser";

	private RoomBoundary rb;

	//Create only one instance of the object
	@SuppressWarnings ("unchecked")
	private RoomController()
	{
		roomReports = RoomReports.getInstance();
		roomList = (ArrayList<RoomEntity>) fromFile(roomFile);
		if (roomList == null)
		{
			roomList = new ArrayList<>();
			addRoomObjects();
			saveFile();
		}
		rb = new RoomBoundary();
	}

	private void addRoomObjects()
	{
		// TODO Auto-generated method stub
		//level 2 rooms
		int level = 2;
		int room = 0;
		String roomId = "";
		boolean smoking = false;
		for (int j = 0; j < 6; j++)
		{
			int x = 0;
			for (int i = 0; i < 2; i++)
			{
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.SINGLE, RoomStatus.VACANT, BedType.SINGLE, smoking);
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.DOUBLE, RoomStatus.VACANT, BedType.QUEEN, smoking);
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.DELUXE, RoomStatus.VACANT, BedType.KING, smoking);
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.SINGLE, RoomStatus.VACANT, BedType.DOUBLESINGLE, smoking);
			}
			level += 1;
			room = 0;
			smoking = !smoking;
		}
	}

	public static RoomController getInstance()
	{

		if (instance == null)
		{
			instance = new RoomController();
		}
		return instance;
	}

	//Load object into the array list
	private void loadObject(String id, RoomType roomType, RoomStatus status, BedType bedType, boolean smoking)
	{
		RoomEntity rm = new RoomEntity(id, roomType, status, bedType, smoking);
		roomList.add(rm);
	}


	//Return the list based on room type
	@SuppressWarnings ("unchecked")
	public <T> ArrayList<T> listRooms(RoomType roomType)
	{
		ArrayList<T> list = new ArrayList<>();
		for (RoomEntity room : roomList)
		{
			if (room.getRoomType() == roomType)
			{
				list.add((T) room);
			}
		}
		return list;
	}


	//Return the list based on room status
	@SuppressWarnings ("unchecked")
	public <T> ArrayList<T> listRooms(RoomStatus status)
	{
		ArrayList<T> list = new ArrayList<>();
		for (RoomEntity room : roomList)
		{
			if (room.getRoomStatus() == status)
			{
				list.add((T) room);
			}
		}
		return list;
	}

	@SuppressWarnings ("unchecked")
	public <T> ArrayList<T> listRooms(RoomStatus status, ArrayList<RoomEntity> l)
	{
		ArrayList<T> list = new ArrayList<>();
		for (RoomEntity room : l)
		{
			if (room.getRoomStatus() == status)
			{
				list.add((T) room);
			}
		}
		return list;
	}


	//Return the list based on bed type
	@SuppressWarnings ("unchecked")
	public <T> ArrayList<T> listRooms(BedType bedType)
	{
		ArrayList<T> list = new ArrayList<>();
		for (RoomEntity room : roomList)
		{
			if (room.getBedType() == bedType)
			{
				list.add((T) room);
			}
		}
		return list;
	}

	//Return the list based on room id
	public RoomEntity getRoom(String roomId)
	{
		for (RoomEntity room : roomList)
		{
			if (room.getRoomId().equals(roomId))
			{
				return room;
			}
		}
		return null;
	}

	//Return the list based on room id
	public RoomEntity getRerservation(int reserveId)
	{
		for (RoomEntity room : roomList)
		{
			if (room.getReserveId() == reserveId)
			{
				return room;
			}
		}
		return null;
	}

	//Return the list based on smoking
	@SuppressWarnings ("unchecked")
	public <T> ArrayList<T> listRooms(boolean smoking)
	{
		ArrayList<T> list = new ArrayList<>();
		for (RoomEntity room : roomList)
		{
			if (room.isSmoking() == smoking)
			{
				list.add((T) room);
			}
		}
		return list;
	}

	@SuppressWarnings ("unchecked")
	public <T> ArrayList<T> listRooms(RoomStatus roomStatus, RoomType roomType, BedType bedType, boolean smoking)
	{
		ArrayList<T> list = new ArrayList<>();
		for (RoomEntity room : roomList)
		{
			if (room.getRoomStatus() == roomStatus && room.getRoomType() == roomType && room.getBedType() == bedType && room.isSmoking() == smoking)
			{
				list.add((T) room);
			}
		}
		return list;
	}

	public void addRoom(String id, RoomType roomType, RoomStatus status, BedType bedType, boolean smoking)
	{
		loadObject(id, roomType, status, bedType, smoking);
		saveFile();
	}

	public void deleteRoom(String id)
	{
		Object room = getRoom(id);
		roomList.remove(room);
		saveFile();
	}

	public void saveFile()
	{
		replaceFile(roomList, roomFile);
	}

	public void roomMaintenace(String roomId)
	{
		this.getRoom(roomId).maintenance();
		saveFile();
	}

	public void checkIn(int guestId, String roomId)
	{
		// TODO Auto-generated method stub
		this.getRoom(roomId).checkIn(guestId);
		saveFile();
	}

	public void checkOut(String roomId)
	{
		this.getRoom(roomId).checkOut();
		saveFile();
	}

	public void reserve(String roomId, int guestId, int reserveId)
	{
		this.getRoom(roomId).reserve(guestId, reserveId);
		saveFile();
	}

	@Override
	public void processMain()
	{
		int sel = rb.process();

		switch (sel)
		{
			case 1: //1 - Add Rooms

				break;

			case 2: //2 - Delete Rooms
				RoomController.getInstance().generateReports();
				break;

			case 3: //2 - Display All Rooms
				DisplayRooms.showList(roomList);
				rb.waitInput();
				break;
			case 4: //4 - List single Rooms
				DisplayRooms.showList(listRooms(RoomType.SINGLE));
				rb.waitInput();
				break;
			case 5: //4 - List Occupied Rooms
				DisplayRooms.showList(listRooms(RoomStatus.OCCUPIED));
				rb.waitInput();
				break;
			case 6: //4 - List 1 Room
				DisplayRooms.showRoom(roomList.get(10));
				rb.waitInput();
				break;
			case 7: //4 - List Smoking Rooms
				DisplayRooms.showList(listRooms(true));
				rb.waitInput();
				break;
			case 0: // 0 - Go Back
				break;

			default:
				rb.invalidInputWarning();
		}
	}

	public void generateReports()
	{
		roomReports.printReports();
	}

}
