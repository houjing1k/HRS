

import java.util.ArrayList;

public class PaymentController extends Controller{
	private ArrayList<PaymentBill> PaymentBillList;
	private double service_charge=10.0;
	private double GST =7.0;
   	PaymentBoundary paymentboundary= new PaymentBoundary();
	

	public PaymentController() {
		PaymentBillList = new ArrayList<PaymentBill>();
	}
	
	//Create payment account when guest and reservation is made.
	public void createPaymentAccount(Reservation reservation, Guest guest) {
		PaymentBill bill =new PaymentBill( reservation.getRoomID, reservation.getReservationID, guest.getPaymentDetail());
		PaymentBillList.add(bill);
	}
	
	//add the room to PaymentBill.
    public void addRoomToPaymentBill(Reservation reservation) {
       /*
        1.get the start and end date,roomID from reservation list
        2. getRoomDetails(roomID); do you have smtg like this that return room based on id?
        3. iterate through the date and add to PaymentBill based on diff rate(weekend)  
        */
    	Transaction newtrans = new Transaction(item.name, item.description, item.price,quantity, item.date);
    	PaymentBill bill= getPaymentBill(reservationID);
    	bill.AddTransaction(item);
    }
    
    //add room service to PaymentBill.
    public void addRoomServiceToPaymentBill(Reservation reservation) {

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
    	double totalPaymentBill= calculatePaymentBill(PaymentBill);
	
    	System.out.println("The total price :" +totalPaymentBill +" ( Include GST :"+ GST*100+"% ,Service Charge:
		+service charge*100+" %, Discount: "+bill.getDiscount()*100+"% )")

    }
    
    //make payment
    public void makePayment(int reservationID) {
    	//print the invoice
    	printInvoice(reservationID);
    	
    	//get the payment method
    	PaymentBill bill=getPaymentBill(reservationID);
    	PaymentDetail payment =PaymentBill.getPaymentDetail();
	
    	if(payment.getPaymentMethod()=="CASH") {
    		
    		System.out.println("PAY BY CASH:");
    		System.out.println("you paid "+ calculatePaymentBill());
    	}
    	else {
			System.out.println(payment.toString());	
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
