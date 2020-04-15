package com.company;

import java.util.ArrayList;

public class PaymentBill {
	/**
	 * 
	 */
	private String roomID;
	private double discount=0;
	private Status status=Status.PENDING;
	private ArrayList<Transaction> transactionList;
	private PaymentDetail payment_detail;
	
	public enum Status{
		PENDING,PAID,CANCELLED;	
	}
	public PaymentBill() {
		transactionList = new ArrayList<Transaction>();
	    payment_detail= new PaymentDetail("CASH"); //default by cash
	}

	//Add transaction to the PaymentBill. 
	public void AddTransaction(Transaction item) {
		transactionList.add(item);
	}
		
	//print the PaymentBill
	public void printPaymentBill() {
		System.out.println(String.format("%-10s %-15s %-8s %-5s %-25s", "Name", "Description", "Quantity" ,"Price","Date"));
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
	
	public void setRoomID(String roomID) {
		 this.roomID=roomID;
	}
	
	public String getRoomID() {
		return this.roomID;
	}
	

	public void setPaymentDetail(PaymentDetail payment) {
		 this.payment_detail=payment;
	}
	
	//return the paymentdetail
	public PaymentDetail getPaymentDetail() {
		
		return this.payment_detail;
	}

	//set discount
	public void setDiscount(double discount) {
		this.discount=discount;
	}
	
	// return the discount
	public double getDiscount() {
		return this.discount;
	}



}
