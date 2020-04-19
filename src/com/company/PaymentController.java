package com.company;

import java.text.NumberFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @author Kenny Voo
 */

public class PaymentController extends Controller
{
	private ArrayList<PaymentBill> billingAccountList;    // The bills that haven't paid yet
	private ArrayList<PaymentBill> paymentRecords;        // The paid bills
	private ArrayList<Double> chargesList;                // Store rate of gst, service charges
	PaymentBoundary pb = new PaymentBoundary();
	private Scanner sc = new Scanner(System.in);
	String paymentRecordsFile = "./data/paymentRecords.ser";
	String billingAccountsFile = "./data/billingAccount.ser";
	String chargesRateFile = "./data/charges.ser";   // index 0 : gst , index 1: service charge

	public PaymentController()
	{
		billingAccountList = (ArrayList<PaymentBill>) fromFile(billingAccountsFile);
		paymentRecords = (ArrayList<PaymentBill>) fromFile(paymentRecordsFile);
		chargesList = (ArrayList<Double>) fromFile(chargesRateFile);
		if (billingAccountList == null)
			billingAccountList = new ArrayList<PaymentBill>();
		if (paymentRecords == null)
			paymentRecords = new ArrayList<PaymentBill>();

		setCharges(chargesList);
	}


	@Override
	public void processMain()
	{

	}


	//Modify Rate of different Charges
	public void modifyChargesMenu()
	{
		while (true)
		{
			pb.modifyChargesMenu();
			int sel = sc.nextInt();
			switch (sel)
			{
				case 1:
					setGST();                //Modify GST
					break;
				case 2:
					setServiceCharge();        //modify Service charge
					break;
				case 3:
					setDiscount();            //modify discount
					break;
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}
	}


	//make payment Menu
	public void makePaymentMenu(String roomID, PaymentDetail paymentDetail)
	{
		PaymentBill bill = getPaymentBill(roomID);
		//return if bill does not exist or 0 transaction;
		if (bill == null)
		{
			pb.invalidBillingAccount();
			return;
		}
		else if (bill.getTransactions().size() == 0)
		{
			System.out.println("No transactions yet!");
			return;
		}
		//Print the invoice
		printInvoice(roomID);
		boolean loop = true;
		while (loop)
		{
			loop = false;
			pb.makePaymentMenu();
			int sel = sc.nextInt();
			switch (sel)
			{
				case 1:
					//Pay by CASH
					pb.paymentProcess("CASH", calculatePaymentBill(bill));
					break;
				case 2:
					//Pay by Card
					System.out.println(paymentDetail.toString());
					System.out.println();
					pb.paymentProcess("CARD", calculatePaymentBill(bill));
					break;
				default:
					loop = true;
					pb.invalidInputWarning();
			}
		}
		bill.setStatus("PAID");
		bill.setPaymentDate(LocalDateTime.now());
		new RoomServiceController().setPaidCurrentOrdersOfRoom(roomID);
		removebillingAccount(roomID);
		saveBillsToFile();
		pb.waitInput();
	}


	//Create payment account when checked in
	public void createBillingAccount(String roomID)
	{
		//Check if this payment account exist
		if (getPaymentBill(roomID) != null)
		{
			System.out.println("Billing Account already exist!");
			return;
		}
		PaymentBill bill = new PaymentBill();
		bill.setRoomID(roomID);
		billingAccountList.add(bill);
		saveBillsToFile();
	}


	// Remove Payment Account
	public void removebillingAccount(String roomID)
	{
		PaymentBill bill = getPaymentBill(roomID);
		if (bill == null)
		{
			pb.invalidBillingAccount();
			return;
		}
		//Save paid bills to record before removing it
		addToRecord(bill);
		billingAccountList.remove(bill);
	}


	//Save the paid bills to record
	void addToRecord(PaymentBill bill)
	{
		paymentRecords.add(bill);
		saveRecordsToFile();
	}

	//add the room to PaymentBill.
	public void addRoomToPaymentBill(String roomID, LocalDate startDate, LocalDate endDate)
	{
		//Search the bill
		PaymentBill bill = getPaymentBill(roomID);
		if (bill == null) return;

		//get the roomcontroller instance
		RoomController rc = RoomController.getInstance();
		RoomEntity room = rc.getRoom(roomID);

		Transaction newtrans = new Transaction();
		newtrans.setQuantity(1);
		//Fetch the room id,room type and price of the room. 
		newtrans.setName("Room ID " + room.getRoomId());
		newtrans.setDescription(room.getRoomType().toString());
		double price = room.getCost();

		//iterate through date.
		for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
		{
			Transaction tempTrans = new Transaction(newtrans);
			double temp_price = price;
			//If weekdays then the price will drop by 10%
			if (date.getDayOfWeek().name() == "SATURDAY" || date.getDayOfWeek().name() == "SUNDAY")
			{
				tempTrans.setPrice(temp_price);
			}
			else tempTrans.setPrice(temp_price * 0.9);
			tempTrans.setTime(date.atTime(12, 00));
			bill.AddTransaction(tempTrans);
		}
		saveBillsToFile();
    }
    	
    //add room service to PaymentBill.
    public void addRoomServiceToPaymentBill(String roomID, RoomServiceOrder order) {
		PaymentBill bill =getPaymentBill(roomID);
    	if(bill==null) {
    		pb.invalidBillingAccount();
    		return;
    	} 	
        Transaction transaction = null;
        for (RoomServiceItem item: order) {
            if (transaction == null) {
            	transaction = new Transaction(item.getName(), item.getDescription(), item.getPrice(), 1, order.getOrder_date_time());
            	}
            else if (item.getName().equals(transaction.getName())) 
            	transaction.setQuantity( transaction.getQuantity()+1 );
            else {
            	bill.AddTransaction(transaction);
            	transaction = null;
            	}
            }
        if (transaction != null) bill.AddTransaction(transaction);
        saveBillsToFile();
    }

	// Find the PaymentBill based on roomID
	public PaymentBill getPaymentBill(String roomID)
	{
		for (PaymentBill bill : billingAccountList)
		{
			if (bill.getRoomID().equals(roomID))
			{
				return bill;
			}
		}
		return null;
	}

	//print the invoice
	public void printInvoice(String roomID)
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		PaymentBill bill = getPaymentBill(roomID);
		if (bill == null)
		{
			pb.invalidBillingAccount();
			return;
		}

		bill.printPaymentBill();
		calculatePaymentBill(bill);
		String totalPrice = formatter.format(bill.getTotalPrice());
		System.out.printf("%-52s %50s\n", "Total Payable Amount :", totalPrice);
		Boundary.printDivider();
		System.out.println("( Includes GST : " + String.format("%.2f", PaymentBill.getGST() * 100) + " % , Service Charge : "
				+ PaymentBill.getServiceCharge() * 100 + " %, Discount : " + bill.getDiscount() * 100 + " % )");
		Boundary.printDivider();
	}


	//calculate the total of PaymentBill
	public double calculatePaymentBill(PaymentBill paymentBill)
	{
		double sum = 0.00;
		for (Transaction trans : paymentBill.getTransactions())
		{
			sum += trans.getPrice();
		}
		// GST , Discount , service charge	
		sum = sum * (1 + PaymentBill.getGST() - paymentBill.getDiscount() + PaymentBill.getServiceCharge());
		paymentBill.setTotalPrice(Double.valueOf(String.format("%.2f", sum)));
		saveBillsToFile();
		return Double.valueOf(String.format("%.2f", sum));
	}


	//load GST and service charges
	public void setCharges(ArrayList<Double> charges)
	{
		if (charges == null)
		{
			chargesList = new ArrayList<Double>();
			chargesList.add(PaymentBill.getGST());   //Add default GST to list
			chargesList.add(PaymentBill.getServiceCharge()); //add default service charge
		}
		else
		{
			try
			{
				PaymentBill.setGST(chargesList.get(0));
				PaymentBill.setServiceCharge(chargesList.get(1));
			} catch (Exception e)
			{
				System.out.println("Failed to Load Charges!");
			}
		}
	}

	//Change serviceCharge
	public void setServiceCharge()
	{
		System.out.println("Current Service Charge : " + PaymentBill.getServiceCharge());
		double charge = pb.readDouble(sc, "New Service Charge : ");
		PaymentBill.setServiceCharge(charge);
		chargesList.set(1, charge);  //save service charge to file 
		saveChargesToFile();

	}

	//set GST
	public void setGST()
	{
		System.out.println("Current GST : " + PaymentBill.getGST());
		double gst = pb.readDouble(sc, "New GST : ");
		PaymentBill.setGST(Double.valueOf(String.format("%.2f", gst)));
		chargesList.set(0, gst);  //save gst to file
		saveChargesToFile();
	}

	//set Discount 
	public void setDiscount()
	{
		String id = pb.requestRoomID();
		PaymentBill bill = getPaymentBill(id);
		if (bill == null)
		{
			pb.invalidBillingAccount();
			return;
		}
		System.out.println("Current Discount Rate : " + bill.getDiscount());
		double discount = pb.readDouble(sc, "New Discount Rate:");
		bill.setDiscount(discount);
		saveBillsToFile();
	}

	//Print out the paid Bills
	public void generatePaymentReport()
	{
		pb.printSubTitle("Financial Report");

		System.out.println(String.format("%-12s %-15s %-15s", "RoomID", "PaidAmount($)", "PaymentDate") + "\n");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		double sum = 0;
		for (PaymentBill bill : paymentRecords)
		{
			System.out.println(String.format("%-12s %-15s %-15s", bill.getRoomID(),
					bill.getTotalPrice(), bill.getPaymentDate().format(formatter)));
			sum += calculatePaymentBill(bill);
		}
		System.out.println("\nTotal Paid Amount : " + sum);

	}


	//store the billingAccountList to file
	private void saveBillsToFile()
	{
		toFile(billingAccountList, billingAccountsFile);
	}

	//store chargesList to file
	private void saveChargesToFile()
	{
		toFile(chargesList, chargesRateFile);
	}

	//store paymentRecords to file
	private void saveRecordsToFile()
	{
		toFile(paymentRecords, paymentRecordsFile);

	}


}
