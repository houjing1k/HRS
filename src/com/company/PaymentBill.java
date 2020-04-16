package com.company;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentBill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	static private double serviceCharge=0.1;
	static private double GST =0.07;
	private String roomID;
	private double discount=0;
	private Status status=Status.PENDING;
	private LocalDate paymentDate;
	private double totalPrice;
	private ArrayList<Transaction> transactionList;
	
	public enum Status{
		PENDING,PAID,CANCELLED;	
	}
	
	public PaymentBill() {
		transactionList = new ArrayList<Transaction>();
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
	
	//set RoomID
	public void setRoomID(String roomID) {
		 this.roomID=roomID;
	}
	
	//get RoomID
	public String getRoomID() {
		return this.roomID;
	}
	
	//set discount
	public void setDiscount(double discount) {
		this.discount=discount;
	}
	
	// return the discount
	public double getDiscount() {
		return this.discount;
	}
	
	//set totalPrice
	public void setTotalPrice(double total) {
		this.totalPrice=total;
	}
	
	// return the discount
	public double getTotalPrice() {
		return totalPrice;
	}
	
	//get PaymentDate
	public void setPaymentDate(LocalDate date) {
		this.paymentDate=date;
	}
	
	// set PaymentDate
	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}
	
	//return GST
	static public double getGST() {
		return GST;
	}
	//setGST
	static void setGST(double gst) {
		GST=gst;
	}
	
	//return GST
	static public double getServiceCharge() {
		return serviceCharge;
	}
	//set Service Charge
	static void setServiceCharge(double charge) {
		serviceCharge=charge;
	}

}
