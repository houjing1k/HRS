package com.company;

import java.time.LocalDate;
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
		boolean wifi = true;
		for (int j = 0; j < 6; j++)
		{
			int x = 0;
			for (int i = 0; i < 2; i++)
			{
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.SINGLE, RoomStatus.VACANT, BedType.SINGLE, smoking, wifi);
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.DOUBLE, RoomStatus.VACANT, BedType.QUEEN, smoking, wifi);
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.DELUXE, RoomStatus.VACANT, BedType.KING, smoking, wifi);
				room += 1;
				roomId = String.format("%02d" + "%02d", level, room);
				this.loadObject(roomId, RoomType.SINGLE, RoomStatus.VACANT, BedType.DOUBLESINGLE, smoking, wifi);
			}
			level += 1;
			room = 0;
			smoking = !smoking;
			wifi = !wifi;
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
	private void loadObject(String id, RoomType roomType, RoomStatus status, BedType bedType, boolean smoking, boolean wifi)
	{
		RoomEntity rm = new RoomEntity(id, roomType, status, bedType, smoking, wifi);
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

	public RoomEntity getRoom(int guestId)
	{
		for (RoomEntity room : roomList)
		{
			if (room.getGuestId() == guestId)
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

	private void addRoom(String id, RoomType roomType, RoomStatus status, BedType bedType, boolean smoking, boolean wifi)
	{
		loadObject(id, roomType, status, bedType, smoking, wifi);
		saveFile();
	}

	public void deleteRoom(String id)
	{
		Object room = this.getRoom(id);
		if (room == null)
		{
			System.out.println("Room does not exist");
		}
		else
		{
			roomList.remove(room);
			saveFile();
		}
	}

	public void saveFile()
	{
		replaceFile(roomList, roomFile);
	}

	public void roomMaintenace(String roomId)
	{
		try
		{
			this.getRoom(roomId).maintenance();
			saveFile();
		} catch (Exception e)
		{
			System.out.println("Room does not exist");
		}
	}

	public void checkIn(int guestId, String roomId, LocalDate startDate, LocalDate endDate)
	{
		// TODO Auto-generated method stub
		this.getRoom(roomId).checkIn(guestId, startDate, endDate);
		saveFile();
	}

	public void checkOut(String roomId)
	{
		this.getRoom(roomId).checkOut();
		saveFile();
	}

	public void reserve(String roomId, int guestId, int reserveId)
	{
		try
		{
			this.getRoom(roomId).reserve(guestId, reserveId);
			saveFile();
		} catch (Exception e)
		{
			System.out.println("Room does not exist");
		}
	}

	public void changeBedType(String roomId, BedType bedType)
	{
		try
		{
			this.getRoom(roomId).setBedType(bedType);
			System.out.println("bedType changed");
			saveFile();
		} catch (Exception e)
		{
			System.out.println("Room does not exist");
		}
	}

	public void changeSmoking(String roomId, boolean b)
	{
		try
		{
			this.getRoom(roomId).setSmoking(b);
			System.out.println("smoking changed");
			saveFile();
		} catch (Exception e)
		{
			System.out.println("Room does not exist");
		}
	}

	public void changeWifi(String roomId, boolean b)
	{
		try
		{
			this.getRoom(roomId).setWIfi(b);
			System.out.println("wifi changed");
			saveFile();
		} catch (Exception e)
		{
			System.out.println("Room does not exist");
		}
	}

	@Override
	public void processMain()
	{
		int sel = rb.process();
		String roomId;
		boolean b;
		switch (sel)
		{
			case 1: //1 - Add Rooms
				//roomId = rb
				//this.addRoom(id, roomType, status, bedType, smoking, wifi);
				saveFile();
				break;

			case 2: //2 - Delete Rooms
				roomId = rb.getRoomId();
				this.deleteRoom(roomId);
				break;

			case 3: //3 - Change room to maintenance
				roomId = rb.getRoomId();
				this.roomMaintenace(roomId);
				System.out.println(roomId + " is under maintenace");
				break;
			case 4: //4 - Change room bed type
				roomId = rb.getRoomId();
				BedType bedType = rb.getBedType();
				this.changeBedType(roomId, bedType);
				break;
			case 5: //5 - change room smoking
				roomId = rb.getRoomId();
				System.out.println("Smoking");
				b = rb.getBooleanInput();
				this.changeSmoking(roomId, b);
				break;
			case 6: //6 -  change room wifi
				roomId = rb.getRoomId();
				System.out.println("WIFI");
				b = rb.getBooleanInput();
				this.changeWifi(roomId, b);
				break;
			case 7://7 - Find room by room id
				roomId = rb.getRoomId();
				try
				{
					System.out.println(this.getRoom(roomId).toString());
				} catch (Exception e)
				{
					System.out.println("Room does not exist");
				}
			case 8://8 - Find room by guest
				int guest = new GuestController().searchGuest_Hybrid().getGuestID();
				try
				{
					roomId = getRoom(guest).getRoomId();
					System.out.println(this.getRoom(roomId).toString());
				} catch (Exception e)
				{
					System.out.println("Room does not exist");
				}
			case 0: // 0 - Go Back
				break;

			default:
				Boundary.invalidInputWarning();
		}
	}

	public void generateReports()
	{
		roomReports.printReports();
	}

	public ArrayList<RoomEntity> filterRooms(int mode)
	{
		return filterRooms(roomList, mode);
	}

	//isRoomSelection - 0   - Administrative Mode
	//                  1   - Only Vacant Rooms (For walk-in check-in)
	//					2   - Reservation Mode
	public ArrayList<RoomEntity> filterRooms(ArrayList<RoomEntity> list, int mode)
	{
		final int FILTER_SIZE = 15;
		boolean[] filter = new boolean[FILTER_SIZE];
		ArrayList<RoomEntity> filteredList = new ArrayList<RoomEntity>();
		int selectionSize = (mode == 0) ? FILTER_SIZE : FILTER_SIZE - 4;

		switch (mode)
		//Room Selection Mode   - Only Vacant Rooms Selected, all other flags initialised false
		{
			case 0: //Administrative Mode   - All flags initialised true
				for (int i = 0; i < FILTER_SIZE; i++)
				{
					filter[i] = true;
				}
				break;

			case 1: //Only Vacant Rooms pre-selected, all other flags initialised false
				for (int i = 0; i < FILTER_SIZE; i++)
				{
					filter[i] = false;
				}
				filter[11] = true;
				break;

			case 2: // Vacant, Occupied, Reserved Rooms pre-selected
				for (int i = 0; i < FILTER_SIZE; i++)
				{
					filter[i] = false;
				}
				filter[11] = true;
				filter[12] = true;
				filter[13] = true;
				break;
		}


		while (true)
		{
			rb.filterRoom(filter, mode != 0);
			int sel = rb.getInput(0, selectionSize);
			if (sel == 0) break;
			else filter[sel - 1] = !filter[sel - 1];
		}

		for (RoomEntity e : list)
		{
			if ((((e.getRoomType() == RoomType.SINGLE) && filter[0]) ||
					((e.getRoomType() == RoomType.DOUBLE) && filter[1]) ||
					((e.getRoomType() == RoomType.DELUXE) && filter[2]))
					&&
					(((e.getBedType() == BedType.SINGLE) && filter[3]) ||
							((e.getBedType() == BedType.DOUBLESINGLE) && filter[4]) ||
							((e.getBedType() == BedType.QUEEN) && filter[5]) ||
							((e.getBedType() == BedType.KING) && filter[6]))
					&&
					((e.isSmoking() && filter[7]) || (!e.isSmoking() && filter[8]))
					&&
					((e.isWifi() && filter[9]) || (!e.isWifi() && filter[10]))
					&&
					((e.isVacant() && filter[11]) ||
							(e.isOccupied() && filter[12]) ||
							(e.isReserved() && filter[13]) ||
							(e.isMaintenance() && filter[14]))
			)
			{
				filteredList.add(e);
			}
		}
		return filteredList;
	}

}
