package com.company;

//import com.company.PaymentDetail.PaymentType;

import java.io.Serializable;

public class PaymentDetail implements Serializable
{
	//private PaymentType payment_method;
	private String card_name;
	private String card_no;
	//private String billing_address;
	private String expiry_date;

	/*
	public enum PaymentType
	{
		CARD, CASH;
	}
	//by cash

	public PaymentDetail(String payment_method) {
		this.payment_method=PaymentType.valueOf(payment_method);
	}*/

	//by card

	public PaymentDetail(String card_name, String card_no, String expiry_date)
	{
		//this.payment_method = PaymentType.valueOf(payment_method);
		this.card_name = card_name;
		this.card_no = card_no;
		//this.billing_address = billing_address;
		this.expiry_date = expiry_date;

	}


	public String toString()
	{
		return "[Card Details] " +
				"\nCard Name       : " + card_name +
				"\nCard No         : " + this.card_no +
				"\nExpiry Date     : " + this.expiry_date+
				"\n";
	}
}
