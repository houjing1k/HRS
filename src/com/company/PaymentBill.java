import java.util.ArrayList;

public class PaymentBill {
	private int roomID;
	private int reservationID;
	private double discount=0;
	private Status status=Status.PENDING;
	private ArrayList<Transaction> transactionList;
	private PaymentDetail payment_detail;

	
	public enum Status{
		PENDING,PAID,CANCELLED;	
	}
	
	//Create a PaymentBill for a customer
	public PaymentBill(int roomID, int reservationID,PaymentDetail payment_detail) {
		this.roomID=roomID;
		this.reservationID=reservationID;
		this.payment_detail= payment_detail;
		transactionList = new ArrayList<Transaction>();
	}

	//Add transaction to the PaymentBill. As of now, we only have room and roomservice
	public void AddTransaction(Transaction item) {
		//Transaction newtrans = new Transaction(item.name, item.description, item.price,quantity, item.date);
		transactionList.add(item);
	}
		
	//print the PaymentBill
	public void printPaymentBill() {
		for(Transaction trans : transactionList) {
			System.out.println(trans.toString());
		}
	}

	//return all the transaction of the PaymentBills
	public ArrayList<Transaction> getTransactions() {
		return transactionList;
	}
	
	//set the status of this PaymentBill
	public void setStatus(String status) {
		this.status=Status.valueOf(status);
	}
	
	// return the status of the PaymentBill
	public String getStatus() {
		return this.status.toString();
	}
	
	public int getroomID() {
		return this.roomID;
	}
	public int getReservationID() {
		return this.reservationID;
	}

	//return the paymentdetail
	public PaymentDetail getPaymentDetail() {
		
		return payment_detail;
	}

	//set discount
	public void setDiscount(double discount) {
		this.discount=discount;
	}
	
	// return the discount
	public String getStatus() {
		return discount;
	}



}
