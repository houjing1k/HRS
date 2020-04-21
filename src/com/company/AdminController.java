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
		boolean loop = true;
		while (loop)
		{
			int sel = ab.process();
			loop = false;
			switch (sel)
			{
				case 1: //1 - Manage Rooms
					RoomController.getInstance().processMain();
					break;

				case 2: //2 - Mange Guests
					new GuestController().processMain();
					break;

				case 3: //3 - Print Occupancy Report
					RoomController.getInstance().generateReports();
					break;

				case 4: //4 - Print Bill
					String roomID= new PaymentBoundary().requestRoomID();
					new PaymentController().printInvoice(roomID);
					break;

				case 5: //5 - Modify Hotel Charges
					new PaymentController().modifyChargesMenu();
					break;

				case 6: //6 - Generate Financial Report
					new PaymentController().generatePaymentReport();
					ab.waitInput();
					break;

				case 0: // 0 - Go Back
					break;

				default:
					ab.invalidInputWarning();
			}
		}
	}
}
