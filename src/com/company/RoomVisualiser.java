package com.company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class RoomVisualiser
{
	private static final int BOX_HEIGHT = 7;
	private static final int BOX_WIDTH = 13;
	private static final int NUM_OF_DAYS = 32;

	private static RoomVisualiserBoundary rvb = new RoomVisualiserBoundary();
	private static char[] design = rvb.getDesign();

	public static void scheduleOverviewMenu()
	{
		Scanner sc=new Scanner(System.in);
		rvb = new RoomVisualiserBoundary();
		LocalDate date = LocalDate.now();
		int sel=0;
		boolean loop = true;
		while (loop)
		{
			loop = false;
		    sel=rvb.scheduleMenu();
			switch (sel)
			{
				case 1:
					break;

				case 2: //2 - Print Occupancy Report
					date = rvb.selectDate();
					break;

				case 0: // 0 - Go Back
					return;

				default:
					rvb.invalidInputWarning();
					loop = true;
			}
		}
		do {
		RoomVisualiser.showSchedule(
				RoomController.getInstance().filterRooms(1),
				new ReservationController().getReservations(),
				date);
				System.out.println("Enter 0 to exit (else continue)");
				try {
				sel=sc.nextInt();
				}
				catch(Exception e) {}
		}while(sel!=0);
	}

	public static void showList(ArrayList<RoomEntity> roomList)
	{
		rvb.printSubTitle("View Rooms");
		System.out.println();
		if (roomList == null || roomList.isEmpty())
		{
			System.out.println("No Rooms Found!");
		}
		else printList(roomList);
		//System.out.println();
		System.out.println(centrePadding(design[8] + " SM - Smoking Room" + "   |   " + design[8] + " WIFI - WiFi Enabled", ' ', rvb.getMenulength()));
		System.out.println(centrePadding("S - Single" + "   |   " + "D - Double Single" + "   |   " + "Q - Queen" + "   |   " + "K - King", ' ', rvb.getMenulength()));
		rvb.printDivider();
		System.out.println();
	}

	public static void showRoom(RoomEntity roomEntity)
	{
		ArrayList<RoomEntity> roomList = new ArrayList<RoomEntity>();
		roomList.add(roomEntity);
		printList(roomList);
	}

	public static void showSchedule(ArrayList<RoomEntity> roomList, ArrayList<ReservationEntity> reservationList, LocalDate startDate)
	{
		ArrayList<ArrayList<RoomEntity>> roomList2D = to2DRoomList(roomList);
		final int DATE_HEADER = 7;
		final int MONTH_HEADER = 8;

		System.out.println();
		rvb.printSubTitle("Schedule Visualiser");
		if (roomList != null && !roomList.isEmpty())
		{
			printMonth(startDate, charRepeater(' ', MONTH_HEADER), NUM_OF_DAYS);
			//printDate(startDate, charRepeater(' ', DATE_HEADER), NUM_OF_DAYS);

			for (ArrayList<RoomEntity> floor : roomList2D)
			{
				printDate(startDate, charRepeater(' ', DATE_HEADER), NUM_OF_DAYS);
				for (RoomEntity room : floor)
				{
					printRoomSchedule(room, scheduleBuilder(room, startDate, reservationList, design[9], design[10]));
				}

			}

			//System.out.println();
			System.out.println(centrePadding(charRepeater(design[10]) + " - Occupied" + charRepeater(' ', 15) + charRepeater(design[9]) + " - Reserved", ' ', rvb.getMenulength()));
		}
		else
		{
			System.out.println();
			System.out.println(centrePadding("- No Rooms To Show -", ' ', rvb.getMenulength()));
			System.out.println();
		}
		rvb.printDivider();
	}

	private static String scheduleBuilder(RoomEntity room, LocalDate startDate, ArrayList<ReservationEntity> reservationList, char checkedInChar, char reservedChar)
	{
		String schedule = "";
		char dayStatus;
		LocalDate date = startDate;
		LocalDate tempDate;

		for (int i = 0; i < NUM_OF_DAYS; i++)
		{
			dayStatus = ' ';

			if (room.isOccupied())
			{
				if (isWithinDate(date, room.getCheckInDate(), room.getCheckOutDate()))
				{
					dayStatus = design[10];
				}
			}

			//System.out.print(room.getRoomId());
			for (ReservationEntity e : reservationList)
			{
				if (e.roomId.equals(room.getRoomId()) && (e.getReservationState() == ReservationEntity.ReservationState.CONFIRMED))
				{
					//dayStatus = design[9];
					if (isWithinDate(date, e.startDate, e.endDate))
					{
						dayStatus = design[9];
						break;
					}
				}
			}
			schedule = schedule + charRepeater(dayStatus);
			date = date.plusDays(1);
		}

		return schedule;
	}


	private static boolean isWithinDate(LocalDate date, LocalDate startDate, LocalDate endDate)
	{
		// (date >= stateDate) && (date < endDate)
		if (date == null || startDate == null || endDate == null)
			return false;
		else
			return (!date.isBefore(startDate) && date.isBefore(endDate));
		//return !(date.isBefore(startDate) || date.isAfter(endDate));
	}

	private static String charRepeater(char character)
	{
		return charRepeater(character, 3);
	}

	private static String charRepeater(char character, int repetition)
	{
		String str = "";
		for (int i = 0; i < repetition; i++)
		{
			str = str + character;
		}
		return str;
	}

	private static void printDate(LocalDate startDate, String header, int numDays)
	{
		LocalDate date = startDate;
		System.out.print(header);
		for (int i = 0; i < numDays; i++)
		{
			System.out.print(String.format("|%02d", date.getDayOfMonth()));
			date = date.plusDays(1);
		}
		System.out.print("|\n");
	}

	private static void printMonth(LocalDate startDate, String header, int numDays)
	{
		LocalDate date = startDate;
		Month month = date.getMonth();
		Month nextMonth;
		String monthStr = month.getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase();

		System.out.print(header);
		System.out.print(monthStr);

		for (int i = 0; i < numDays - 1; i++)
		{
			month = date.getMonth();
			date = date.plusDays(1);
			nextMonth = date.getMonth();
			if (month != nextMonth)
				monthStr = nextMonth.getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase();
			else
				monthStr = "   ";
			System.out.print(monthStr);
		}
		System.out.print("\n");

	}

	private static void printRoomSchedule(RoomEntity room, String scheduleBar)
	{
		String roomId = getRoomID(room);
		System.out.println(roomId + "  " + scheduleBar);
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

	private static String getRoomID(RoomEntity room)
	{
		return String.format("%02d", getRoomLevel(room)) + "-" + String.format("%02d", getRoomNum(room));
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
