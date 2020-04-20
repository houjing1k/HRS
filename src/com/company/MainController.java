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

				case 4: //5 - Room Overview
					RoomVisualiser.showList(RoomController.getInstance().filterRooms(0));
					mb.waitInput();
					break;

				case 5: //6 - Schedule Overview
					RoomVisualiser.scheduleOverviewMenu();
					mb.waitInput();
					break;

				case 6: //7 - Admin Options
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

