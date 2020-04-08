package com.company;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;



public class PaymentController extends Controller{
	private ArrayList<PaymentBill> PaymentBillList;
	private double service_charge=0.1;
	private double GST =0.07;
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
			//loop = false;
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
		boolean loop = true;
		while (loop)
		{
		paymentboundary.modifyAccountMenu();
		int sel = sc.nextInt();
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
		//paymentboundary.waitInput();
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
		boolean loop = true;
		int id;
		while (loop)
		{
			paymentboundary.addItemMenu();
			int sel = sc.nextInt();
			//loop = false;
			switch (sel)
			{
				case 1:
					//Add room
					id=paymentboundary.requestReservationID();
					addRoomToPaymentBill(id);
					break;
				case 2:
					//Add roomService
					id=paymentboundary.requestReservationID();
					addRoomServiceToPaymentBill(id);
					break;
				case 0:
					return;
				default:
					loop = true;
					paymentboundary.invalidInputWarning();
			}
		}
		//paymentboundary.waitInput();
		
	}
	
	
	//add the room to PaymentBill.
    public void addRoomToPaymentBill(int reservationID) {
    	//get the bill
    	PaymentBill bill= getPaymentBill(reservationID);
    	Transaction newtrans = new Transaction();
    	newtrans.setQuantity(1);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    	//get the start date and end date
		System.out.println("Start Date (dd/MM/yyyy ):");
		String getdate = sc.next();
		LocalDate startDate = LocalDate.parse(getdate,formatter);
		System.out.println("End Date (dd/MM/yyyy ):");
		getdate = sc.next();
		LocalDate endDate = LocalDate.parse(getdate,formatter);
		
		//Fetch the room and price of the room. I assume it's 100 first
		System.out.println("Room ID :");
		newtrans.setName("Room ID "+sc.next());
		//fetch room type
    	newtrans.setDescription("Deluxe");
		double price =100;
		
		for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
		{    	
			Transaction tempTrans = new Transaction(newtrans);
			double temp_price=price;
		    if(date.getDayOfWeek().name()=="SATURDAY"|| date.getDayOfWeek().name()=="SUNDAY") {
		    	tempTrans.setPrice(temp_price*0.9);
		    }
		    else {
		    	tempTrans.setPrice(temp_price);
		    }
		    tempTrans.setTime(date.atTime(12, 00));
		    bill.AddTransaction(tempTrans);
		}

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
    	double totalPaymentBill=0;
    	totalPaymentBill=calculatePaymentBill(bill);
    	System.out.println("The total price :" +totalPaymentBill +" ( Include GST :"+ GST*100+"% ,Service Charge:"
		+service_charge*100+" %, Discount: "+bill.getDiscount()*100+"% )");

    }
    
    //make payment
    public void makePayment(int reservationID) {
    	//print the invoice
    	//get the payment method
    	PaymentBill bill=getPaymentBill(reservationID);
    	while (true)
		{
        	paymentboundary.makePaymentMenu();	
			int sel = sc.nextInt();
			//loop = false;
			switch (sel)
			{
				case 1:
						//CASH
			    	printInvoice(reservationID);
			    	paymentboundary.paymentProcess("CASH",calculatePaymentBill(bill));
					bill.setStatus("PAID");
					break;
				case 2:
					//Card
					printInvoice(reservationID);
					bill.getPaymentDetail().toString();
					paymentboundary.paymentProcess("Card",calculatePaymentBill(bill));
					bill.setStatus("PAID");
					break;
				case 0:
					return;
				default:
					paymentboundary.invalidInputWarning();
			}
			
		}
    	
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
