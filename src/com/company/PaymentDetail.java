
public class PaymentDetail {
	private PaymentType payment_method;
	private String card_name;
	private int card_no;
	private String billing_address;
	private String expiry_date;
	
	public enum PaymentType{
		CARD,CASH;
	}
	//by cash 
	public PaymentDetail(String payment_method) {
		this.payment_method=PaymentType.valueOf(payment_method);
	}
	
	//by card
	public PaymentDetail(String payment_method,String card_name,int card_no, String billing_address,String expiry_date) {
		this.payment_method=PaymentType.valueOf(payment_method);
		this.card_name=card_name;
		this.card_no=card_no;
		this.billing_address=billing_address;
		this.expiry_date=expiry_date;
	
	}
	
	public String getPaymentMethod() {
		return payment_method.toString();
	}
	
	public String toString() {
        return "Card Details \nCard Name: " + card_name + "\nCard No: " + this.card_no + "\nBilling Address: " + this.billing_address + "\nExpiry Date: " + this.expiry_date;
	}
}
