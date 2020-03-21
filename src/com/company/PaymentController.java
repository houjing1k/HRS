

import java.util.ArrayList;

public class PaymentController {
	private ArrayList<Bill> billList;
	private double service_charge=10.0;
	private double GST =7.0;
	private double discount=0;

	public PaymentController() {
		billList = new ArrayList<Bill>();
	}
	
	//Create payment account when guest and reservation is made.
	public void createPaymentAccount(Reservation reservation, Guest guest) {
		Bill bill =new Bill( reservation.getRoomID, reservation.getReservationID, guest.getPaymentDetail);
		billList.add(bill);
	}
	

	//add the room to bill.
    public void addRoomToBill(Reservation reservation) {
       /*
        1.get the start and end date,roomID from reservation list
        2. getRoomDetails(roomID); do you have smtg like this that return room based on id?
        3. iterate through the date and add to bill based on diff rate(weekend)  
        */
    	Transaction newtrans = new Transaction(item.name, item.description, item.price,quantity, item.date);
    	Bill bill= getBill(reservationID);
    	bill.AddTransaction(item);
    }
    
    //add room service to bill.
    public void addRoomServiceToBill() {

    	//insert code
    }
    
    // Find the bill based on reservationID
    public Bill getBill(int reservationID) {
    	for(Bill bill : billList) {
    		if(bill.getReservationID()==reservationID) {
    			return bill;
    		}
    	}
		return null;
    }
    //print the invoice
    public void printInvoice(int reservationID) {
    	Bill bill=getBill(reservationID);
    	bill.printBill();
    	
    	// print bla bla bla
    	bill.getTotalBill()
    	double totalBill= bill.getTotalBill();
    	
    	//insert totalBill formula  gst, promotion ,service charge
    }
    
    //make payment
    public void makePayment(int reservationID) {
    	//print the invoice
    	printInvoice(reservationID);
    	
    	//get the payment method
    	Bill bill=getBill(reservationID);
    	PaymentDetail payment =bill.getPaymentDetail();
    	double totalBill= bill.getTotalBill();

    	
    	if(payment.getPaymentMethod()=="CASH") {
    		
    		System.out.println("PAY BY CASH:");
    		System.out.println("you paid "+ totalBill);
    	}
    	else {
			System.out.println(payment.toString());

    		
    	}
		System.out.println("Thank you");
    	bill.setStatus("PAID");
    }
    
    
    
    /*
     TO-DO
     
     
     generatePaymentReport? which will include only reserveid , status, totalBill;
     */
    
    
    
    


}
