package com.company;

import java.util.ArrayList;

public class DisplayRooms
{
	private static final int BOX_HEIGHT = 7;
	private static final int BOX_WIDTH = 13;
	private static char[] design = Boundary.getDesign();

	public static void showList(ArrayList<RoomEntity> roomList)
	{
		Boundary.printSubTitle("View Rooms");
		System.out.println();
		if (roomList == null || roomList.isEmpty())
		{
			System.out.println("No Rooms Found!");
		}
		else printList(roomList);
		//System.out.println();
		System.out.println(centrePadding(design[8] + " SM - Smoking Room" + "   |   " + design[8] + " WIFI - WiFi Enabled", ' ', Boundary.getMenulength()));
		System.out.println(centrePadding("S - Single" + "   |   " + "D - Double Single" + "   |   " + "Q - Queen" + "   |   " + "K - King", ' ', Boundary.getMenulength()));
		Boundary.printDivider();
		System.out.println();
	}

	public static void showRoom(RoomEntity roomEntity)
	{
		ArrayList<RoomEntity> roomList = new ArrayList<RoomEntity>();
		roomList.add(roomEntity);
		printList(roomList);
	}

	private static void printList(ArrayList<RoomEntity> roomList)
	{
		ArrayList<ArrayList<ArrayList<String>>> list = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<RoomEntity>> roomList2D = to2DRoomList(roomList);

		for (ArrayList<RoomEntity> floorList : roomList2D)
		{
			ArrayList<ArrayList<String>> floors = new ArrayList<ArrayList<String>>();

			for (int j = 0; j < BOX_HEIGHT; j++)
			{
				ArrayList<String> rows = new ArrayList<String>();
				for (RoomEntity roomEntity : floorList)
				{
					String rooms = boxBuilder(j, roomEntity);
					rows.add(rooms);
				}
				floors.add(rows);
			}
			list.add(floors);
		}

		for (ArrayList<ArrayList<String>> floors : list)
		{
			for (ArrayList<String> rows : floors)
			{
				for (String rooms : rows)
				{
					System.out.print(rooms);
				}
				System.out.println();
			}
		}
	}

	private static String boxBuilder(int rowNo, RoomEntity roomEntity)
	{
		String str = null;
		int width = BOX_WIDTH - 2;
		switch (rowNo)
		{
			case 0:
				String roomId = String.format("%02d", getRoomLevel(roomEntity)) + "-" + String.format("%02d", getRoomNum(roomEntity));
				str = design[1] + centrePadding(" " + roomId + " ", design[0], width) + design[2];
				break;

			case 1:
				String roomStatus = roomEntity.getRoomStatus().toString();
				str = design[7] + centrePadding(roomStatus, ' ', width) + design[7];
				break;

			case 2:
				str = design[5] + centrePadding("", design[0], width) + design[6];
				break;

			case 3:
				String roomType = roomEntity.getRoomType().toString();
				str = design[7] + centrePadding(roomType, ' ', width) + design[7];
				break;

			case 4:
				char smoking = roomEntity.isSmoking() ? design[8] : ' ';
				char wifi = roomEntity.isWifi() ? design[8] : ' ';
				char bed = convertBedType(roomEntity.getBedType());
				String roomDetail = bed + "   " + smoking + "   " + wifi;
				str = design[7] + centrePadding(roomDetail, ' ', width) + design[7];
				break;

			case 5:
				String roomDetailDescription = "BED SM WIFI";
				str = design[7] + centrePadding(roomDetailDescription, ' ', width) + design[7];
				break;

			case 6:
				str = design[3] + centrePadding("", design[0], width) + design[4];
				break;
		}
		return str;
	}

	private static String centrePadding(String data, char paddingCharacter, int width)
	{
		String left = "";
		String right = "";
		int padWidth = width - data.length();

		if (padWidth < 1)
		{
			return data.substring(0, width);
		}

		for (int i = 0; i < padWidth / 2; i++)
		{
			left = left + paddingCharacter;
		}

		if ((padWidth % 2) == 0) right = left;
		else right = left + paddingCharacter;

		return left + data + right;
	}

	private static ArrayList<ArrayList<RoomEntity>> to2DRoomList(ArrayList<RoomEntity> roomList)
	{
		ArrayList<ArrayList<RoomEntity>> list = new ArrayList<ArrayList<RoomEntity>>();

		for (RoomEntity room : roomList)
		{
			int lastLevelIndex, lastRoomIndex, lastLevel;

			if (list.size() == 0)
			{
				//No rooms exist
				ArrayList<RoomEntity> level = new ArrayList<RoomEntity>();
				level.add(room);
				list.add(level);
			}
			else
			{
				//Some rooms exist
				lastLevelIndex = list.size() - 1;
				lastRoomIndex = list.get(lastLevelIndex).size() - 1;
				lastLevel = getRoomLevel(list.get(lastLevelIndex).get(lastRoomIndex));

				int roomLevel = getRoomLevel(room);

				if (roomLevel == lastLevel)
				{
					list.get(lastLevelIndex).add(room);
				}
				else
				{
					ArrayList<RoomEntity> level = new ArrayList<RoomEntity>();
					level.add(room);
					list.add(level);
				}
			}
		}
		return list;
	}

	private static int getRoomLevel(RoomEntity room)
	{
		return Integer.parseInt(room.getRoomId().substring(0, 2));
	}

	private static int getRoomNum(RoomEntity room)
	{
		return Integer.parseInt(room.getRoomId().substring(2, 4));
	}

	private static char convertBedType(RoomEntity.BedType bedType)
	{
		if (bedType == RoomEntity.BedType.SINGLE)
		{
			return 'S';
		}
		else if (bedType == RoomEntity.BedType.DOUBLESINGLE)
		{
			return 'D';
		}
		else if (bedType == RoomEntity.BedType.QUEEN)
		{
			return 'Q';
		}
		else if (bedType == RoomEntity.BedType.KING)
		{
			return 'K';
		}
		else
		{
			return ' ';
		}
	}
}
