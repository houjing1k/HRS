package com.company;


public class PaymentDetail {
	private PaymentType payment_method;
	private String card_name;
	private String card_no;
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
	public PaymentDetail(String payment_method,String card_name,String card_no, String billing_address,String expiry_date) {
		this.payment_method=PaymentType.valueOf(payment_method);
		this.card_name=card_name;
		this.card_no=card_no;
		this.billing_address=billing_address;
		this.expiry_date=expiry_date;
	
	}
	
	public String getPaymentMethod() {
		return payment_method.toString();
	}
	
	public void setPaymentMethod(String method) {
		this.payment_method=PaymentType.valueOf(method);
	}
	
	public void setCardName(String name) {
		this.card_name=name;
	}
	public void setCardNo(String cardNo) {
		this.card_no=cardNo;
	}
	public void setBillingAddress(String address) {
		this.billing_address=address;
	}
	public void setExpiryDate(String date) {
		this.expiry_date=date;
	}
	
	public String toString() {
        return "[Card Details] " +
        		"\nCard Name: " + card_name + 
        		"\nCard No: " + this.card_no + 
        		"\nBilling Address: " + this.billing_address + 
        		"\nExpiry Date: " + this.expiry_date;
	}
}
