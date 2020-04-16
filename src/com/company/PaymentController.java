package com.company;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

/**
 * @author Kenny Voo
 *
 */

public class PaymentController extends Controller{
	private ArrayList<PaymentBill> PaymentBillList;
	private double service_charge=0.1;
	private double GST =0.07;
	PaymentBoundary pb= new PaymentBoundary();
	private Scanner sc = new Scanner(System.in);
	//String report= fromFile( "PaymentReport.txt");
	public PaymentController() {
		PaymentBillList = new ArrayList<PaymentBill>();
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
					updatePaymentAccount(); 		//modify Payment Account
					break;
				case 3:
					viewAllPaymentAccount();		//View all Payment Account
					pb.waitInput();

					break;
				case 4:
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
					System.out.println("Dummy Room Created for testing");
					//addRoomToPaymentBill(pb.requestRoomID(),"Deluxe",100.0); 
					pb.waitInput();
					break;
				case 2:							//Add roomService
					addRoomServiceToPaymentBill(pb.requestRoomID());
					break;
				case 0:
					return;
				default:
					pb.invalidInputWarning();
			}
		}

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
	
	// Update Card Detail
	public void updatePaymentAccount() {
		
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
    		if(bill.getRoomID()==roomID) {
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
    	System.out.println("\nThe total price :" + totalPaymentBill +" ( Include GST :"+  String.format("%.2f",GST*100)+"% , Service Charge:"
		+service_charge*100+" %, Discount: "+bill.getDiscount()*100+"% )");

    }
    

	//calculate the total of PaymentBill
	public double calculatePaymentBill(PaymentBill paymentbill) {
		double sum=0.00;
		for(Transaction trans : paymentbill.getTransactions()) {
			sum+=trans.getPrice();
		}
		// GST , Discount , service charge	
		sum =sum* (1+ this.GST -  paymentbill.getDiscount() + this.service_charge);
		return Double.valueOf(String.format("%.2f",sum));	
	}    	
	
	
	public void setServiceCharge() {
		System.out.println("Current Service Charge : "+ service_charge);
		double charge = pb.readDouble(sc, "New Service Charge : ");
		this.service_charge= charge;	
	} 

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
	}

	public void setGST() {
		System.out.println("Current GST : "+ this.GST);
		System.out.println("New GST : ");
		double gst = pb.readDouble(sc, "New GST : ");
		this.GST= Double.valueOf(String.format("%.2f",gst));	
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
