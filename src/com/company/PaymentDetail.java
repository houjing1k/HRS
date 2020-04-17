package com.company;

import java.io.Serializable;

public class PaymentDetail implements Serializable
{
	private String card_name;
	private String card_no;
	private String card_expiry;

	public PaymentDetail(String card_name, String card_no, String card_expiry)
	{
		this.card_name = card_name;
		this.card_no = card_no;
		this.card_expiry = card_expiry;

	}

	public String toString()
	{
		return "[Card Details] " +
				"\nCard Name       : " + card_name +
				"\nCard No         : " + this.card_no +
				"\nCard Expiry     : " + this.card_expiry+
				"\n";
	}
}
