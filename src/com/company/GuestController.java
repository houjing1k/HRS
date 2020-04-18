package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class GuestController extends Controller
{
	private GuestBoundary gb;
	private ArrayList<GuestEntity> guestList;
	private final String guestFileName = "./data/GuestList.ser";

	public GuestController()
	{
		gb = new GuestBoundary();
		guestList = (ArrayList<GuestEntity>) fromFile(guestFileName);
		if (guestList == null)
		{
			guestList = new ArrayList<GuestEntity>();
		}
	}

	//Main Process
	public void processMain()
	{
		boolean loop = true;
		while (loop)
		{
			int sel = gb.process();
			loop = false;
			switch (sel)
			{
				case 1:
					//Add New Guest
					if (addGuest() == -1) loop = true;
					break;
				case 2:
					//Remove Guest
					if (!removeGuest()) loop = true;
					break;
				case 3:
					//Update Guest details
					if (!updateGuest()) loop = true;
					break;
				case 4:
					//Search Guest by name
					printGuestList(searchGuest(gb.searchGuest()));
					break;
				case 5:
					//Search Guest by ID
					printGuest(searchGuest(gb.searchGuestID()));
					break;
				case 6:
					//Print All Guests
					printAllGuest();
					break;
					/*
				case 7:
					//Sort All Guests
					guestList.sort(null);
					saveGuestsToFile();
					break;

					 */
				case 0:
					return;
				default:
					loop = true;
					gb.invalidInputWarning();
			}
		}
		gb.waitInput();
	}

	//Add new Guest
	public int addGuest()
	{
		int currID = 0;
		GuestEntity newGuest = new GuestEntity();
		if (guestList.size() != 0)
		{
			if (guestList.get(0).getGuestID() != 0)
			{
				currID = 0;
			}
			else
			{
				for (int i = 0; i < guestList.size(); i++)
				{
					try
					{
						if (guestList.get(i).getGuestID() != (guestList.get(i + 1).getGuestID() - 1))
						{
							//System.out.println(i + " - " + guestList.get(i).getGuestID());
							//System.out.println((i + 1) + " - " + guestList.get(i + 1).getGuestID());
							currID = guestList.get(i).getGuestID() + 1;
							break;
						}
					} catch (Exception e)
					{
						//System.out.println("Exception caught.");
						currID = guestList.get(i).getGuestID() + 1;
					}
				}
			}
			//System.out.println("currID = " + currID);
		}
		else
		{
			currID = 0;
		}
		//System.out.println("[Guest ID] = " + currID);
		newGuest.setGuestID(currID);

		gb.addGuest_head();

		for (int i = 0; i < 7; i++)
		{
			setGuestDetails(i + 1, newGuest, gb.addUpdateGuestSub(i + 1, "Enter"));
		}
		updatePaymentDetails(newGuest, gb.updateCardDetails());

		if (gb.confirmation(newGuest))
		{
			guestList.add(newGuest);
			guestList.sort(null);
			saveGuestsToFile();
			return currID;
		}
		else
		{
			return -1;
		}

	}

	public boolean removeGuest()
	{
		GuestEntity guest;

		gb.removeGuest_head();
		guest = searchGuest_Hybrid();
		if (guest == null)
		{
			return false;
		}
		else
		{
			if (gb.confirmation(guest))
			{
				guestList.remove(guest);
				saveGuestsToFile();
				return true;
			}
			else return false;

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

	public GuestEntity searchGuest_Hybrid()
	{
		ArrayList<GuestEntity> temp = searchGuest(gb.searchGuest());
		GuestEntity result;
		printGuestList(temp);
		if (temp.size() == 0)
		{
			return null;
		}
		else if (temp.size() == 1)
		{
			result = temp.get(0);
		}
		else
		{
			result = searchGuest(gb.searchGuestID());
			printGuest(result);
		}
		return result;
	}


	//Update Guest Details
	public boolean updateGuest()
	{
		GuestEntity guest = null;
		GuestEntity updGuest = null;

		guest = searchGuest_Hybrid();

		if (guest != null)
		{
			updGuest = GuestEntity.copyGuest(guest);
			int sel = gb.updateGuest();
			if (sel == 0) //Back to menu
			{
				return false;
			}
			else if (sel == 8) //Select update card details
			{
				String[] details = gb.updateCardDetails();
				updatePaymentDetails(updGuest, details);
				if (gb.confirmation(updGuest))
				{
					updatePaymentDetails(guest, details);
					saveGuestsToFile();
					return true;
				}
				else return false;
			}
			else
			{
				String newVal = gb.addUpdateGuestSub(sel, "New");
				setGuestDetails(sel, updGuest, newVal);
				if (gb.confirmation(updGuest))
				{
					setGuestDetails(sel, guest, newVal);
					saveGuestsToFile();
					return true;
				}
				else return false;
			}
		}
		return false;
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
				//case 8:
				//guest.setCreditCardNum(val);
				//break;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean updatePaymentDetails(GuestEntity guest, String[] cardDetails)
	{
		if ((guest != null) && (cardDetails != null))
		{
			guest.setPaymentDetail(new PaymentDetail(cardDetails[0], cardDetails[1], cardDetails[2]));
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

	public void printGuestList(ArrayList<GuestEntity> e)
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
