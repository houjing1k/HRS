package com.company;

import java.math.BigDecimal;
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
	private ArrayList<Double> chargesList;                // Store rate of gst, service charges,single room,double room,deluxe room
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
					roomPriceMenu();         //Modify Room Price
					break;
				case 2:
					setGST();                //Modify GST
					break;
				case 3:
					setServiceCharge();        //modify Service charge
					break;
				case 4:
					setDiscount();            //modify discount
					break;
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}
	}
	
	//Modify room Price
	public void roomPriceMenu()
	{
		while (true)
		{
			pb.printRoomPriceMenu();
			int sel = sc.nextInt();
			switch (sel)
			{
				case 1:
					setRoomCharges(1);         //Single Room
					break;
				case 2:
					setRoomCharges(2);        //Double Room
					break;
				case 3:
					setRoomCharges(3);        //Deluxe Room
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
		double price;
		String roomType=room.getRoomType().toString();
		if (roomType=="SINGLE")
			price=chargesList.get(1);
		else if(roomType=="DOUBLE")
			price=chargesList.get(2);
		else price=chargesList.get(3);   //Deluxe room

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
			tempTrans.setTime(date.atTime(14, 00));
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
        	StringBuilder tempName = new StringBuilder(item.getName());
        	StringBuilder tempDescription = new StringBuilder(item.getDescription());
        	BigDecimal tempPrice = BigDecimal.valueOf(item.getPrice());
        	tempName.setLength( (20 > tempName.length()) ? tempName.length() : 20   );
        	tempDescription.setLength( (35 > tempDescription.length()) ? tempDescription.length() : 35); 
        	
            if (transaction == null) {
            	transaction = new Transaction(tempName.toString(), tempDescription.toString(), tempPrice.doubleValue(), 1, order.getOrder_date_time());
            	}
            else if (tempName.toString().equals(transaction.getName())) 
            {
            	transaction.setQuantity( transaction.getQuantity()+1 );
            	transaction.setPrice( tempPrice.add( BigDecimal.valueOf(transaction.getPrice()) ).doubleValue() ); 
            }
            else {
            	bill.AddTransaction(transaction);
            	transaction = null;
            	transaction = new Transaction(tempName.toString(), tempDescription.toString(), tempPrice.doubleValue(), 1, order.getOrder_date_time());
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
		pb.printSubTitle("BILL");
		System.out.println(String.format("%-20s %-39s %-10s %-11s %-20s", "Name", "Description", "Quantity" ,"Price","Date"));
		pb.printDivider();
		for(Transaction trans : bill.getTransactions()) {
			System.out.println(trans.toString());

		}
		pb.printDivider();
		calculatePaymentBill(bill);
		String totalPrice = formatter.format(bill.getTotalPrice());
		System.out.printf("%-52s %50s\n", "Total Payable Amount :", totalPrice);
		pb.printDivider();
		System.out.println("( Includes GST : " + String.format("%.2f", PaymentBill.getGST() * 100) + " % , Service Charge : "
				+ PaymentBill.getServiceCharge() * 100 + " %, Discount : " + bill.getDiscount() * 100 + " % )");
		pb.printDivider();
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


	
	//load GST,service charges and room charges
	public void setCharges(ArrayList<Double> charges)
	{
		if (charges == null)
		{
			chargesList = new ArrayList<Double>();
			chargesList.add(PaymentBill.getGST());   //Add default GST to list
			chargesList.add(100.0);   //single room 
			chargesList.add(150.0);	  //double room 
			chargesList.add(200.0);	  //Deluxe room
			chargesList.add(PaymentBill.getServiceCharge()); //add default service charge
		}
		else
		{
			try
			{
				PaymentBill.setGST(chargesList.get(0));
				PaymentBill.setServiceCharge(chargesList.get(4));
			} catch (Exception e)
			{
				System.out.println("Failed to Load Charges!");
			}
		}
	}
	
	//room charges 1: Single. 2 : Double, 3 : Deluxe
	public void setRoomCharges(int choice) {
		String roomType;
		if (choice==1) 
			roomType="Single";
		else if (choice==2)
			roomType="Double";
		else roomType="Deluxe";
		
		System.out.println("Current "+ roomType + " Room Price : " + chargesList.get(choice));
		double charge = pb.readDouble(sc, "New " +roomType+" Room Price : ");
		chargesList.set(choice, charge);  //set and save new room charge to file 
		saveChargesToFile();
	}
	
	
	
	//Change serviceCharge
	public void setServiceCharge()
	{
		System.out.println("Current Service Charge : " + PaymentBill.getServiceCharge());
		double charge = pb.readDouble(sc, "New Service Charge : ");
		PaymentBill.setServiceCharge(charge);
		chargesList.set(4, charge);  //save service charge to file 
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
		NumberFormat currFormatter = NumberFormat.getCurrencyInstance();
		pb.printSubTitle("Financial Report");
		System.out.println(String.format("%-40s%-39s%24s", "RoomID", "Payment Date","Paid Amount"));
		pb.printDivider();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		double sum = 0;
		for (PaymentBill bill : paymentRecords)
		{
			System.out.println(String.format("%-40s%-39s%24s", bill.getRoomID()
					, bill.getPaymentDate().format(formatter),
					currFormatter.format(bill.getTotalPrice())));
			sum += calculatePaymentBill(bill);
		}
		pb.printDivider();
		System.out.println(String.format("%-52s %50s","Total Paid Amount : ", currFormatter.format(sum)));
		pb.printDivider();

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
