package com.company;

import sun.applet.Main;

import java.util.Scanner;

public class MainController extends Controller
{
	Scanner sc = new Scanner(System.in);
	int sel = 1;

	public MainController()
	{
	}

	public void start()
	{
		while(true)
		{
			sel = new MainBoundary().process();

			switch (sel)
			{
				case 1: //1 - Add/Manage Guests
					new GuestController().processMain();
					break;

				case 2: //2 - Add/Manage Reservations
					//new ReservationController().processMain();
					break;

				case 3: //3 - Room Services
					//new RoomServiceController().processMain();
					break;

				case 4: //4 - Room Check-in / Check-out
					//new RoomController().processMain();
					break;

				case 5: //5 - Manage Hotel Rooms
					//new RoomController().processManage();
					break;

				case 6: //6 - Other Admin
					// new AdminController().processMain();
					break;

				case 0:
					System.exit(0);
					break;
			}
		}
	}

}

