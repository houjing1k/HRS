package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Array;
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

	public void processMain()
	{
		int sel = gb.process();
		switch (sel)
		{
			case 1:
				//add new Guest
				addGuest();
				return;
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
				break;
		}
		gb.waitInput();
	}

	public int addGuest()
	{
		int currID = guestList.get(guestList.size() - 1).getGuestID() + 1;
		guestList.add(gb.addGuest(currID));
		saveGuestsToFile();
		return currID;
	}

	public ArrayList<GuestEntity> searchGuest(String name)
	{
		ArrayList<GuestEntity> results = new ArrayList<>();
		for (GuestEntity e : guestList)
		{
			//System.out.println(e.getName());
			if (e.getName().contains(name))
			{
				results.add(e);
			}
		}
		return results;
	}

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

	public void updateGuest()
	{
		GuestEntity guest = null;
		ArrayList<GuestEntity> temp = searchGuest(gb.searchGuest());
		printGuestList(temp);
		if (temp.size() == 0)
		{
			System.out.println("Not Found");
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

		int sel = gb.updateGuest();
		String newVal = gb.updateGuestSub(sel);

		switch (sel)
		{
			case 1:
				guest.setName(newVal);
				break;
			case 2:
				guest.setAddress(newVal);
				break;
			case 3:
				guest.setCountry(newVal);
				break;
			case 4:
				guest.setGender(newVal.charAt(0));
				break;
			case 5:
				guest.setIdentityNo(newVal);
				break;
			case 6:
				guest.setNationality(newVal);
				break;
			case 7:
				guest.setContactNo(newVal);
				break;
			case 8:
				guest.setCreditCardNum(newVal);
				break;
		}
		printGuest(guest);
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
}
