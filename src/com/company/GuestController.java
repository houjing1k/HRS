package com.company;

import java.util.ArrayList;

public class GuestController extends Controller
{
	private GuestBoundary gb;
	private ArrayList<GuestEntity> guestList;
	private String guestFileName = "GuestList.txt";

	public GuestController()
	{
		gb = new GuestBoundary();
		guestList = (ArrayList<GuestEntity>) fromFile(guestFileName);
	}

	//Main Processs
	public void processMain()
	{
		int sel = gb.process();
		switch (sel)
		{
			case 1:
				//add new Guest
				addGuest();
				break;
			case 2:
				//Update Guest details
				updateGuest();
				break;
			case 3:
				//Search Guest by name
				printGuestList(searchGuest(gb.searchGuest()));
				break;
			case 4:
				//Print All Guests
				printAllGuest();
				break;
			case 0:
				return;

		}
		gb.waitInput();
	}

	//Add new Guest
	public int addGuest()
	{
		int currID;
		GuestEntity newGuest = new GuestEntity();
		if (guestList.size() != 0)
		{
			currID = guestList.get(guestList.size() - 1).getGuestID() + 1;
		}
		else
		{
			currID = 0;
		}
		newGuest.setGuestID(currID + 1);

		gb.addGuestMenu();

		String[] guestDetails = new String[8];
		for (int i = 0; i < 8; i++)
		{
			setGuestDetails(i + 1, newGuest, gb.addUpdateGuestSub(i + 1, false));
		}

		if (gb.confirmation(newGuest))
		{
			guestList.add(newGuest);
			saveGuestsToFile();
			return currID;
		}
		else
		{
			return -1;
		}

	}

	// Search Guest by Name
	public ArrayList<GuestEntity> searchGuest(String name)
	{
		ArrayList<GuestEntity> results = new ArrayList<>();
		for (GuestEntity e : guestList)
		{
			if (containsIgnoreCase(e.getName(), name))
			{
				results.add(e);
			}
		}
		return results;
	}

	//Search Guest by ID
	public GuestEntity searchGuest(int guestID)
	{
		GuestEntity result = null;
		for (GuestEntity e : guestList)
		{
			if (e.getGuestID() == guestID)
			{
				result = e;
			}
		}
		return result;
	}

	//Update Guest Details
	public void updateGuest()
	{
		GuestEntity guest = null;
		ArrayList<GuestEntity> temp = searchGuest(gb.searchGuest());
		printGuestList(temp);
		if (temp.size() == 0)
		{
			return;
		}
		else if (temp.size() == 1)
		{
			guest = temp.get(0);
		}
		else
		{
			guest = searchGuest(gb.searchGuestID());
			printGuest(guest);
		}

		if (guest != null)
		{

			int sel = gb.updateGuest();
			String newVal = gb.addUpdateGuestSub(sel, true);

			if (setGuestDetails(sel, guest, newVal))
			{
				printGuest(guest);
				saveGuestsToFile();
			}
		}
	}

	private boolean setGuestDetails(int index, GuestEntity guest, String val)
	{
		if (val != null)
		{
			switch (index)
			{
				case 1:
					guest.setName(val);
					break;
				case 2:
					guest.setAddress(val);
					break;
				case 3:
					guest.setCountry(val);
					break;
				case 4:
					guest.setGender(val.charAt(0));
					break;
				case 5:
					guest.setIdentityNo(val);
					break;
				case 6:
					guest.setNationality(val);
					break;
				case 7:
					guest.setContactNo(val);
					break;
				case 8:
					guest.setCreditCardNum(val);
					break;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	private void saveGuestsToFile()
	{
		toFile(guestList, guestFileName);
	}

	private void printAllGuest()
	{
		gb.printGuestList(guestList);
	}

	private void printGuestList(ArrayList<GuestEntity> e)
	{
		gb.printGuestList(e);
	}

	private void printGuest(GuestEntity e)
	{
		gb.printGuest(e);
	}

	private boolean containsIgnoreCase(String str, String subString)
	{
		return str.toLowerCase().contains(subString.toLowerCase());
	}
}
