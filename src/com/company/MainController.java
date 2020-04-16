package com.company;

public class MainController extends Controller
{
	private MainBoundary mb;

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
				case 1: //1 - Add/Manage Guests
					new GuestController().processMain();
					break;

				case 2: //2 - Add/Manage Reservations
					new ReservationController().processMain();
					break;

				case 3: //3 - Room Services
					new RoomServiceController().processMain();
					break;

				case 4: //4 - Room Check-in / Check-out
					CheckInController.getInstance().processMain();
					break;

				case 5: //5 - Payment Options
					// new AdminController().processMain();
					new PaymentController().processMain();
					break;

				case 6: //6 - Print Room Report
					// new AdminController().processMain();
					new PaymentController().processMain();
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

