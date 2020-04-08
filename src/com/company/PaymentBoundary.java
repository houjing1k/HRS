package com.company;


import java.util.Scanner;

public class PaymentBoundary extends Boundary{
    Scanner scan = new Scanner(System.in);
	
    protected void printMenu()
	{
		printMainTitle("Payment System");
		String[] menuList =
				{
						"Add/Edit Payment Account",
						"Add items to Bill",
						"Print Invoice",
						"Make Payment",
						"Generate Financial report"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
    
	public void CreatePaymentAccount()
	{
		printSubTitle("Create New Payment Account");
	}
    
	public void CreatePaymentDetail()
	{
		printSubTitle("Enter Credit Card");
	}
	
    protected void editAccountMenu()
	{
		printMainTitle("Edit Payment Account");
		String[] menuList =
				{
						"Edit Payment method",
						"Update Credit Card Detail",
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
    protected void modifyAccountMenu()
	{
		printMainTitle("Add/Edit Payment Account");
		String[] menuList =
				{
						"Add Payment Account",
						"Edit Payment Account",
						"Delete Payment Account"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
    
    protected void addItemMenu()
	{
		printMainTitle("add Items to Bill");
		String[] menuList =
				{
						"Add Room",
						"Add Room Service",
						"Remove Room",
						"Remove Room service"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
    protected void makePaymentMenu()
	{
		printMainTitle("Payment");
		String[] menuList =
				{
						"Cash",
						"Card",
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
    protected int requestReservationID()
	{
		System.out.println("Reservation ID :");
		return scan.nextInt();
	}

    protected int requestTransactionDetail()
	{
		System.out.println("Reservation ID :");
		return scan.nextInt();
	}
    public void paymentProcess(String method,double money) {
		if(method=="CASH") {
			System.out.println("Pay By Cash:");
			boolean paying=true;
			while(paying) {
				System.out.println("Cash Amount :");
				double receive = scan.nextDouble();
				if(receive>=money) {
					System.out.println("Paid Amount: $"+ receive);
					System.out.println("Return Amount: $"+ (receive-money));
					paying =false;
				}
				else {
					System.out.println("Insufficient Amount!");
				}
			}
		}
		else {
			System.out.println("Pay By Card:");
			System.out.println("Paid Amount:: $"+ money);
		}
		System.out.println("Thank you");
    	
 
    }
    
    
}
