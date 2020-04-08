package com.company;

import java.util.Scanner;
import java.util.ArrayList;

public class PaymentController extends Controller{
	private ArrayList<PaymentBill> PaymentBillList;
	private double service_charge=10.0;
	private double GST =7.0;
	PaymentBoundary paymentboundary= new PaymentBoundary();
	private Scanner sc = new Scanner(System.in);
	
	public PaymentController() {
		PaymentBillList = new ArrayList<PaymentBill>();
	}
	
	@Override
	public void processMain() {
		int id;
		boolean loop = true;
		while (loop)
		{
		int sel = paymentboundary.process();
			loop = false;
			switch (sel)
			{
				case 1:
					//Add/Edit Payment Account
					modifyPaymentAccount();
					break;
				case 2:
					//Add to bill
					addToBill();
					break;
				case 3:
					//Print Invoice
					id=paymentboundary.requestReservationID();
					printInvoice(id);
					break;
				case 4:
					//Make Payment
					id=paymentboundary.requestReservationID();
					makePayment(id);
					break;
				case 5:
					//Generate Financial report
					//printAllGuest();
					break;
				case 0:
					return;
				default:
					loop = true;
					paymentboundary.invalidInputWarning();
			}
		}
		paymentboundary.waitInput();
		
	} 
	
	public void modifyPaymentAccount() {
		paymentboundary.modifyAccountMenu();
		boolean loop = true;
		while (loop)
		{
		int sel = sc.nextInt();
			loop = false;
			switch (sel)
			{
				case 1:
					//Add Payment Account
					createPaymentAccount();
					break;
				case 2:
					//modify Payment Account
					updatePaymentAccount();
					break;
				case 0:
					return;
				default:
					loop = true;
					paymentboundary.invalidInputWarning();
			}
		}
		paymentboundary.waitInput();
	}

	//Create payment account when guest and reservation is made.
	public void createPaymentAccount() {
		paymentboundary.CreatePaymentAccount();
		PaymentBill bill =new PaymentBill();
		System.out.println("Room ID :");
		bill.setRoomID(sc.nextInt());
		System.out.println("Reservation ID :");
		bill.setReservationID(sc.nextInt());
		bill.setPaymentDetail(createPaymentDetail());
		PaymentBillList.add(bill);
	}
	
	// Enter Card Detail
	public PaymentDetail createPaymentDetail() {
		paymentboundary.CreatePaymentDetail();
		PaymentDetail card = new PaymentDetail("CARD");
		System.out.println("Card Name :");
		card.setCardName(sc.next());
		System.out.println("Card No :");
		card.setCardNo(sc.next());	
		System.out.println("Billing Address:");
		card.setBillingAddress(sc.next());
		System.out.println("Card Expiry Date :");
		card.setExpiryDate(sc.next());
		return card;
	}
	
	// Update Card Detail
	public void updatePaymentAccount() {
		
	}
	
	public void addToBill() {
		int id=paymentboundary.requestReservationID();
		paymentboundary.addItemMenu();
		boolean loop = true;
		while (loop)
		{
			int sel = sc.nextInt();
			loop = false;
			switch (sel)
			{
				case 1:
					//Add room
					addRoomToPaymentBill(id);
					break;
				case 2:
					//Add roomService
					addRoomServiceToPaymentBill(id);
					break;
				case 0:
					return;
				default:
					loop = true;
					paymentboundary.invalidInputWarning();
			}
		}
		paymentboundary.waitInput();
		
	}
	
	
	//add the room to PaymentBill.
    public void addRoomToPaymentBill(int reservationID) {
       /*
        1.get the start and end date,roomID from reservation list
        2. getRoomDetails(roomID); do you have smtg like this that return room based on id?
        3. iterate through the date and add to PaymentBill based on diff rate(weekend)  
        */
    	Transaction newtrans = new Transaction();
    	PaymentBill bill= getPaymentBill(reservationID);
    	//bill.AddTransaction();
    }
    
    //add room service to PaymentBill.
    public void addRoomServiceToPaymentBill(int reservationID) {

    	//insert code
    }
    
    // Find the PaymentBill based on reservationID
    public PaymentBill getPaymentBill(int reservationID) {
    	for(PaymentBill bill : PaymentBillList) {
    		if(bill.getReservationID()==reservationID) {
    			return bill;
    		}
    	}
		return null;
    }

    //print the invoice
    public void printInvoice(int reservationID) {

    	PaymentBill bill=getPaymentBill(reservationID);
    	bill.printPaymentBill();
    	double totalPaymentBill= calculatePaymentBill(bill);
    	System.out.println("The total price :" +totalPaymentBill +" ( Include GST :"+ GST*100+"% ,Service Charge:"
		+service_charge*100+" %, Discount: "+bill.getDiscount()*100+"% )");

    }
    
    //make payment
    public void makePayment(int reservationID) {
    	//print the invoice
    	printInvoice(reservationID);
    	//get the payment method
    	PaymentBill bill=getPaymentBill(reservationID);
    	if(bill.getPaymentDetail().getPaymentMethod()=="CASH") {
    		
    		System.out.println("Pay By Cash:");
    		System.out.println("Total Amount: "+ calculatePaymentBill(bill));
    	}
    	else {
			System.out.println(bill.getPaymentDetail().toString());	
    	}
		System.out.println("Thank you");
    	bill.setStatus("PAID");
    }
    
    	
	//calculate the total of PaymentBill
	public double calculatePaymentBill(PaymentBill paymentbill) {
		double sum=0;
		for(Transaction trans : paymentbill.getTransactions()) {
			sum+=trans.getPrice();
		}
		// GST , Discount , service charge	
		sum =sum* (1+ this.GST +  paymentbill.getDiscount() + this.service_charge);

		return sum;	
	}    	
    
	public void setServiceCharge(double charge) {
		this.service_charge= charge;	
	} 


	public void setDiscount(int reservationID,double discount){
	PaymentBill bill =getPaymentBill(reservationID);
	bill.setDiscount(discount);
	}

	public void setGST(double gst) {
		this.GST= gst;	
	}



	
	
    /*
     TO-DO
     
     
     generatePaymentReport? which will include only reserveid , status, totalPaymentBill;
     */
    
    
    
    


}
