package com.company;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @author Kenny Voo
 *
 */

public class PaymentController extends Controller{
	private ArrayList<PaymentBill> paymentBillList; 	// The bills that haven't paid yet
	private ArrayList<PaymentBill> paymentRecords;		// The paid bills
	private ArrayList<Double> chargesList;				// Store rate of gst, service charges
	PaymentBoundary pb= new PaymentBoundary();
	private Scanner sc = new Scanner(System.in);
	String paymentRecordsFile= "./data/paymentRecords.ser";
	String paymentBillsFile= "./data/paymentBills.ser";
	String chargesRateFile ="./data/charges.ser";   // index 0 : gst , index 1: service charge
	
	public PaymentController() {
		paymentBillList = (ArrayList<PaymentBill>) fromFile(paymentBillsFile);
		paymentRecords = (ArrayList<PaymentBill>) fromFile(paymentRecordsFile);
		chargesList= (ArrayList<Double>) fromFile(chargesRateFile);
		if (paymentBillList == null)
			paymentBillList = new ArrayList<PaymentBill>();
		if (paymentRecords == null)
			paymentRecords = new ArrayList<PaymentBill>();
		
		setCharges(chargesList);
	}

	
	@Override
	public void processMain() {
		String roomID;
		while (true)
		{   int sel = pb.process();
			switch (sel)
			{
				case 1:
					PaymentAccountMenu();  	//Add/remove Payment Account
					break;
				case 2:
					addToBillMenu();  				//Add to bill
					break;
				case 3:
					roomID=pb.requestRoomID();
					printInvoice(roomID);			//Print Invoice
					pb.waitInput();
					break;
				case 4:
					roomID=pb.requestRoomID();
					makePaymentMenu(roomID,new PaymentDetail("Dummy", "1212", "1/1/20"));    		//Make Payment
					break;
				case 5:
					modifyChargesMenu();			// Modify gst,service charges, discount
					break;
				case 6:
					generatePaymentReport();    //Generate Financial report for paid bills
					pb.waitInput();

					break;
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}
	} 
	
	
	//create/remove/view all account
	public void PaymentAccountMenu() {
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
					roomID=pb.requestRoomID();
					removePaymentAccount(roomID);		// remove payment account
					break;
				case 3:
					viewAllPaymentAccount();		//View all Payment Account
					pb.waitInput();
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}
	}
	
	//Modify Rate of different Charges
	public void modifyChargesMenu() {
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
	public void addToBillMenu() {
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
												//add dummy to try out the payment system
					addSmtgtoBill();
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}

	}

	
    //make payment Menu
    public void makePaymentMenu(String roomID,PaymentDetail paymentDetail) {
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
					bill.setPaymentDate(LocalDateTime.now());
					removePaymentAccount(roomID);
					break;
				case 2:	
					printInvoice(roomID);					//Pay by Card
					System.out.println(paymentDetail.toString());
					System.out.println();
					pb.paymentProcess("CARD",calculatePaymentBill(bill));
					bill.setStatus("PAID");
					bill.setPaymentDate(LocalDateTime.now());
					removePaymentAccount(roomID);
					break;
				case 0:
					return;
				default:
					loop = true;
					pb.invalidInputWarning();
			}
			
		}
    	saveBillsToFile();
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
		paymentBillList.add(bill);
		saveBillsToFile();
	}

	//Print out all the payment account
	public void viewAllPaymentAccount() {
		if(paymentBillList.size()==0) {
			System.out.println("No Payment Account Exist");
			return;
		}
		pb.printSubTitle("Payment Account");
		for(PaymentBill bill : paymentBillList) {
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
    	addToRecord(bill);
		paymentBillList.remove(bill);
	}
	
	
	//Save the paid bills to record
	void addToRecord(PaymentBill bill){

		paymentRecords.add(bill);
		saveRecordsToFile();
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
    	for(PaymentBill bill : paymentBillList) {
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
    	calculatePaymentBill(bill);
    	System.out.println("\nThe total price :" + bill.getTotalPrice() +" ( Include GST :"+  String.format("%.2f",PaymentBill.getGST()*100)+"% , Service Charge:"
		+PaymentBill.getServiceCharge()*100+" %, Discount: "+bill.getDiscount()*100+"% )");

    }
    

	//calculate the total of PaymentBill
	public double calculatePaymentBill(PaymentBill paymentBill) {
		double sum=0.00;
		for(Transaction trans : paymentBill.getTransactions()) {
			sum+=trans.getPrice();
		}
		// GST , Discount , service charge	
		sum =sum* (1+ PaymentBill.getGST() -  paymentBill.getDiscount() + PaymentBill.getServiceCharge());
		paymentBill.setTotalPrice(Double.valueOf(String.format("%.2f",sum)));
		saveBillsToFile();
		return Double.valueOf(String.format("%.2f",sum));	
	}    	
	

	//load GST and service charges
	public void setCharges(ArrayList<Double> charges) {
		if(charges==null) {
			chargesList = new ArrayList<Double>();
			chargesList.add(PaymentBill.getGST());   //Add default GST to list
			chargesList.add(PaymentBill.getServiceCharge()); //add default service charge
		}
		else {
			try {
			PaymentBill.setGST(chargesList.get(0));
			PaymentBill.setServiceCharge(chargesList.get(1));
			}
			catch(Exception e){
	    		System.out.println("Failed to Load Charges!");
			}
		}		
	}
	
    //Change serviceCharge
	public void setServiceCharge() {
		System.out.println("Current Service Charge : "+ PaymentBill.getServiceCharge());
		double charge = pb.readDouble(sc, "New Service Charge : ");
		PaymentBill.setServiceCharge(charge);
		chargesList.set(1, charge);  //save service charge to file 
		saveChargesToFile();

	} 
	
	//set GST
	public void setGST() {
		System.out.println("Current GST : "+ PaymentBill.getGST());
		double gst = pb.readDouble(sc, "New GST : ");
		PaymentBill.setGST(Double.valueOf(String.format("%.2f",gst)));	
		chargesList.set(0, gst);  //save gst to file
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


	public void generatePaymentReport() {
		pb.printSubTitle("Financial Report");
		
		String str=null;
		System.out.println(String.format("%-10s %-15s %-15s", "RoomID", "PaidAmount($)","PaymentDate")+"\n");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		double sum=0;
		for(PaymentBill bill : paymentRecords) {
			System.out.println(String.format("%-10s %-15s %-15s", bill.getRoomID(), 
					bill.getTotalPrice(),bill.getPaymentDate().format(formatter) ));
				sum+=calculatePaymentBill(bill);
		}
		System.out.println("\nTotal Paid Amount : "+sum);
		
	}
	
	//store the paymentBillList to file
	private void saveBillsToFile()
	{
		toFile(paymentBillList, paymentBillsFile);
	}
	
	//store chargesList to file
	private void saveChargesToFile()
	{
		toFile(chargesList, chargesRateFile);
	}

	private void saveRecordsToFile() {
		toFile(paymentRecords,paymentRecordsFile);
		
	}
	
	//For testing
	void addSmtgtoBill(){
		PaymentBill pay=getPaymentBill(pb.requestRoomID());
		Transaction trans = new Transaction("dummy","dummy", 100,1, LocalDateTime.now());
		pay.AddTransaction(trans);
		saveBillsToFile();

	}

}
