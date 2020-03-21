import java.util.ArrayList;

public class Bill {
	private int roomID;
	private int reservationID;
	private Status status=Status.PENDING;
	private ArrayList<Transaction> transactionList;
	private PaymentDetail payment_detail;
	private double totalBill=0;
	
	public enum Status{
		PENDING,PAID,CANCELLED;	
	}
	
	//Create a bill for a customer
	public Bill(int roomID, int reservationID,PaymentDetail payment_detail) {
		this.roomID=roomID;
		this.reservationID=reservationID;
		this.payment_detail= payment_detail;
		transactionList = new ArrayList<Transaction>();
	}
	
	//print the bill
	public void printBill() {
		for(Transaction trans : transactionList) {
			System.out.println(trans.toString());
		}
	}
	
	
	//return all the transaction of the bills
	public ArrayList<Transaction> getTransaction() {
		return transactionList;
	}
	
	//Add transaction to the bill. As of now, we only have room and roomservice
	public void AddTransaction(Transaction item) {
		//Transaction newtrans = new Transaction(item.name, item.description, item.price,quantity, item.date);
		transactionList.add(item);
	}
	
	//return the totalBill
	public double getTotalBill() {
		double sum=0;
		for(Transaction trans : transactionList) {
			sum+=trans.getPrice();
		}
		this.totalBill=sum; // update the totalBill of this bill
		return sum;
		
	}
	
	//set the status of this bill
	public void setStatus(String status) {
		this.status=Status.valueOf(status);
	}
	
	// return the status of the bill
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
}
