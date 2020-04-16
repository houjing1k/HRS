package com.company;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * @author Kenny Voo
 *
 */

public class PaymentController extends Controller{
	private ArrayList<PaymentBill> PaymentBillList;
	private ArrayList<Double> ChargesList;
	PaymentBoundary pb= new PaymentBoundary();
	private Scanner sc = new Scanner(System.in);
	String PaymentBillsFile= "PaymentBills.txt";
	String chargesRateFile ="Charges.txt";   // index 0 : gst , index 1: service charge
	
	public PaymentController() {
		PaymentBillList = (ArrayList<PaymentBill>) fromFile(PaymentBillsFile);
		ChargesList= (ArrayList<Double>) fromFile(chargesRateFile);
		if (PaymentBillList == null)
		{
			PaymentBillList = new ArrayList<PaymentBill>();
		}
		setCharges(ChargesList);
	}

	//load GST and service charges
	public void setCharges(ArrayList<Double> charges) {
		if(charges==null) {
			ChargesList = new ArrayList<Double>();
			ChargesList.add(PaymentBill.getGST());   //Add default GST to list
			ChargesList.add(PaymentBill.getServiceCharge()); //add default service charge
		}
		else {
			try {
			PaymentBill.setGST(ChargesList.get(0));
			PaymentBill.setServiceCharge(ChargesList.get(1));
			}
			catch(Exception e){
	    		System.out.println("Failed to Load Charges!");
			}
		}		
	}
	
	@Override
	public void processMain() {
		String id;
		while (true)
		{   int sel = pb.process();
			switch (sel)
			{
				case 1:
					modifyPaymentAccount();  	//Add/Edit Payment Account
					break;
				case 2:
					addToBill();  				//Add to bill
					break;
				case 3:
					id=pb.requestRoomID();
					printInvoice(id);			//Print Invoice
					pb.waitInput();
					break;
				case 4:
					id=pb.requestRoomID();
					//makePayment(id);    		//Make Payment
					break;
				case 5:
					modifyCharges();			// Modify discount,
					break;
				case 6:
					generatePaymentReport();    //Generate Financial report
					pb.waitInput();

					break;
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}
	} 
	
	public void modifyPaymentAccount() {
		String roomID;
		while (true)
		{
		pb.modifyAccountMenu();
		int sel = sc.nextInt();
			switch (sel)
			{
				case 1:
					pb.CreatePaymentAccount();
					roomID=pb.requestRoomID();
					createPaymentAccount(roomID);   	//Add Payment Account
					pb.waitInput();
					break;
				case 2:
					viewAllPaymentAccount();		//View all Payment Account
					pb.waitInput();
					break;
				case 3:
					roomID=pb.requestRoomID();
					removePaymentAccount(roomID);		// remove payment account
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}
	}
	
	//Modify Rate of diff Charges
	public void modifyCharges() {
		while (true)
		{
		pb.modifyChargesMenu();
		int sel = sc.nextInt();
			switch (sel)
			{
				case 1:
					setGST(); 				//Modify GST
					break;
				case 2:
					setServiceCharge();		//modify Service charge
					break;
				case 3:
					setDiscount(); 			//modify discount
					break;
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}
	}
	
	//Adding Item to bill
	public void addToBill() {
		while (true)
		{
			pb.addItemMenu();
			int sel = sc.nextInt();
			switch (sel)
			{
				case 1:							//Add room to bill
					addRoomToPaymentBill(pb.requestRoomID(),pb.readDate(scan, "Start Date"), pb.readDate(scan, "End Date"));
					pb.waitInput();
					break;
				case 2:							//Add roomService
					addRoomServiceToPaymentBill(pb.requestRoomID());
					break;
				case 3:
					//add dummy
					addSmtgtoBill();
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}

	}
	void addSmtgtoBill(){
		PaymentBill pay=getPaymentBill(pb.requestRoomID());
		Transaction trans = new Transaction("dummy","dummy", 100,1, pb.readDate(scan, "Start Date").atStartOfDay());
		pay.AddTransaction(trans);
	}
	
    //make payment
    public void makePayment(String roomID,PaymentDetail paymentDetail) {
    	PaymentBill bill=getPaymentBill(roomID);
    	//return if bill does not exist or 0 transaction;
    	if(bill==null) {
    		pb.invalidPaymentAccount();
    		return; 	
    	}
    	else if(bill.getTransactions().size()==0) {
    		System.out.println("No transactions yet!");
    		return;
    	}
		boolean loop = true;	
    	while (loop)
		{
			loop = false;
        	pb.makePaymentMenu();	
			int sel = sc.nextInt();
			switch (sel)
			{
				case 1:					
			    	printInvoice(roomID);					//Pay by CASH
			    	pb.paymentProcess("CASH",calculatePaymentBill(bill));
					bill.setStatus("PAID");
					break;
				case 2:	
					
					printInvoice(roomID);					//Pay by Card
					System.out.println(paymentDetail.toString());
					System.out.println();
					pb.paymentProcess("CARD",calculatePaymentBill(bill));
					bill.setStatus("PAID");
					
					break;
				case 0:
					return;
				default:
					loop = true;
					pb.invalidInputWarning();
			}
			
		}
		pb.waitInput();
    }
    
    
	//Create payment account when checked in
	public void createPaymentAccount(String roomID) {
		//Check if this payment account exist
		if(getPaymentBill(roomID)!= null) {
			System.out.println("PaymentAccount already exist!");
			return;
		}
		PaymentBill bill =new PaymentBill();
		bill.setRoomID(roomID);
		PaymentBillList.add(bill);
		saveBillsToFile();
	}
	
	// Enter Card Detail
	public PaymentDetail createPaymentDetail() {
		pb.CreatePaymentDetail();
		PaymentDetail card = new PaymentDetail("CARD");
		card.setCardName(pb.readStrictlyString(sc, "Credit Card Name : "));
		card.setCardNo(pb.readCreditCardNo(sc,"Credit Card No : "));	
		card.setBillingAddress(pb.readString(sc, "Billing Address : "));
		card.setExpiryDate(pb.readDate(sc, "Expiry Date (dd/MM/yyyy ) :").toString());
		return card;
	}
	

	
	//Print out all the payment account
	public void viewAllPaymentAccount() {
		if(PaymentBillList.size()==0) {
			System.out.println("No Payment Account Exist");
			return;
		}
		pb.printSubTitle("Payment Account");
		for(PaymentBill bill : PaymentBillList) {
			System.out.println("RoomID "+bill.getRoomID());
    	}
		
	}
	
	// Remove Payment Account
	public void removePaymentAccount(String roomID) {
		PaymentBill bill =getPaymentBill(roomID);
    	if(bill==null) {
    		pb.invalidPaymentAccount();
    		return;
    	}
		PaymentBillList.remove(bill);
		//System.out.println("Payment Account Succesfully Remove\n");	
	}
	
	//add the room to PaymentBill.
    public void addRoomToPaymentBill(String roomID,LocalDate startDate, LocalDate endDate ) {
    	//Search the bill
    	PaymentBill bill= getPaymentBill(roomID);
    	if(bill==null) return;
    	
    	//get the roomcontroller instance
    	RoomController rc= RoomController.getInstance();
    	RoomEntity room=rc.getRoom(roomID);
    	
    	Transaction newtrans = new Transaction();
    	newtrans.setQuantity(1);
		//Fetch the room id,room type and price of the room. 
		newtrans.setName("Room ID "+room.getRoomId());
    	newtrans.setDescription(room.getRoomType().toString());
    	double price =room.getCost();
    	
		//iterate through date.
		for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
		{    	
			Transaction tempTrans = new Transaction(newtrans);
			double temp_price=price;
			//If weekdays then the price will drop by 10%
		    if(date.getDayOfWeek().name()=="SATURDAY"|| date.getDayOfWeek().name()=="SUNDAY") {
		    	tempTrans.setPrice(temp_price);
		    }
		    else tempTrans.setPrice(temp_price*0.9);
		    tempTrans.setTime(date.atTime(12, 00));
		    bill.AddTransaction(tempTrans);
		}
		saveBillsToFile();

    }
    
    //add room service to PaymentBill.
    public void addRoomServiceToPaymentBill(String roomID) {
		PaymentBill bill =getPaymentBill(roomID);
    	if(bill==null) {
    		pb.invalidPaymentAccount();
    		return;
    	} 	
        Transaction transaction = null;

        ArrayList<RoomServiceOrder> list = new RoomServiceController().getCurrentOrdersOfRoom(roomID);
        for (RoomServiceOrder order : list) {
            for (RoomServiceItem item: order) {
            	if (transaction == null)
            		transaction = new Transaction(
                            new StringBuilder(item.getName()).substring(0,10), new StringBuilder(item.getDescription()).substring(0,15),
                            item.getPrice(), 1, order.getOrder_date_time());
            	else if (item.getName().equals(transaction.getName())) 
            		transaction.setQuantity( transaction.getQuantity()+1 );
            	else
            		bill.AddTransaction(transaction);
            }
            bill.AddTransaction(transaction);
        }

    }

    // Find the PaymentBill based on roomID
    public PaymentBill getPaymentBill(String roomID) {
    	for(PaymentBill bill : PaymentBillList) {
    		if(bill.getRoomID().equals(roomID)) {
    			return bill;
    		}
    	}
		return null;
    }

    //print the invoice
    public void printInvoice(String roomID) {
    	PaymentBill bill=getPaymentBill(roomID);
    	if(bill==null) {
    		pb.invalidPaymentAccount();
    		return;
    		}
    	bill.printPaymentBill();
    	double totalPaymentBill=0;
    	totalPaymentBill=calculatePaymentBill(bill);
    	System.out.println("\nThe total price :" + totalPaymentBill +" ( Include GST :"+  String.format("%.2f",PaymentBill.getGST()*100)+"% , Service Charge:"
		+PaymentBill.getServiceCharge()*100+" %, Discount: "+bill.getDiscount()*100+"% )");

    }
    

	//calculate the total of PaymentBill
	public double calculatePaymentBill(PaymentBill paymentbill) {
		double sum=0.00;
		for(Transaction trans : paymentbill.getTransactions()) {
			sum+=trans.getPrice();
		}
		// GST , Discount , service charge	
		sum =sum* (1+ PaymentBill.getGST() -  paymentbill.getDiscount() + PaymentBill.getServiceCharge());
		return Double.valueOf(String.format("%.2f",sum));	
	}    	
	

    //Change serviceCharge
	public void setServiceCharge() {
		System.out.println("Current Service Charge : "+ PaymentBill.getServiceCharge());
		double charge = pb.readDouble(sc, "New Service Charge : ");
		PaymentBill.setServiceCharge(charge);
		ChargesList.set(1, charge);  //save service charge to file 
		saveChargesToFile();

	} 
	
	//set GST
	public void setGST() {
		System.out.println("Current GST : "+ PaymentBill.getGST());
		double gst = pb.readDouble(sc, "New GST : ");
		PaymentBill.setGST(Double.valueOf(String.format("%.2f",gst)));	
		ChargesList.set(0, gst);  //save gst to file
		saveChargesToFile();
	}
	
	//set Discount 
	public void setDiscount(){
		String id=pb.requestRoomID();
		PaymentBill bill =getPaymentBill(id);
    	if(bill==null) {
    		pb.invalidPaymentAccount();
    		return;
    	}
		System.out.println("Current Discount Rate : "+ bill.getDiscount());
		double discount = pb.readDouble(sc, "New Discount Rate:");
		bill.setDiscount(discount);
		saveBillsToFile();
	}

	private void saveBillsToFile()
	{
		toFile(PaymentBillList, PaymentBillsFile);
	}
	
	private void saveChargesToFile()
	{
		toFile(ChargesList, chargesRateFile);
	}

	public void generatePaymentReport() {
		pb.printSubTitle("Financial Report");
		String str=null;
		str=(String.format("%s %10s %10s", "RoomID", "Status" ,"Price")+"\n");

		double sum=0;
		for(PaymentBill bill : PaymentBillList) {
			if(bill.getStatus()=="PAID")
				sum+=calculatePaymentBill(bill);
			str+=(String.format("%s %14s %14s",bill.getRoomID(),bill.getStatus(),calculatePaymentBill(bill))+"\n");
		}
		System.out.println(str);
		System.out.println("\nTotal Payment : "+sum);
		toFile(str, "PaymentReport.txt");
		
	}
	

    


}
