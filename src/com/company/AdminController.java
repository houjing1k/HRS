package com.company;

public class AdminController extends Controller
{
	private AdminBoundary ab;

	public AdminController()
	{
		ab = new AdminBoundary();
	}

	@Override
	public void processMain()
	{
		while (true)
		{
			int sel = ab.process();

			switch (sel)
			{
				case 1: //1 - Manage Rooms

					break;

				case 2: //2 - Print Occupancy Report
					RoomController.getInstance().processMain();
					break;

				case 3: //3 - Modify Hotel Charges
					new PaymentController().modifyCharges();
					break;

				case 4: //4 - Print Bill Invoice

					break;

				case 0:
					System.exit(0);
					break;

				default:
					ab.invalidInputWarning();
			}
		}
	}
}
