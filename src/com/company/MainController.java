package com.company;

public class MainController extends Controller
{
	private MainBoundary mb;
	private Scheduler scheduler = new Scheduler();
	public MainController()
	{
		mb = new MainBoundary();
	}

	public void processMain()
	{
		while(true)
		{
			int sel = mb.process();

			switch (sel)
			{
				case 1: //1 - Add/Manage Reservations
					new ReservationController().processMain();
					break;

				case 2: //2 - Room Check-in / Check-out
					CheckInController.getInstance().processMain();
					break;

				case 3: //3 - Room Services
					new RoomServiceController().processMain();
					break;

				case 4: //4 - Manage Guests
					new GuestController().processMain();
					break;

				case 5: //5 - Room Overview
					DisplayRooms.showList(RoomController.getInstance().filterRooms(false));
					mb.waitInput();
					break;

				case 6: //6 - Admin Options
					new AdminController().processMain();
					break;

				case 0:
					System.exit(0);
					break;

				default:
					mb.invalidInputWarning();
			}
		}
	}

}

