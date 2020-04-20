package com.company;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PaymentBoundary extends Boundary{
    Scanner scan = new Scanner(System.in);
	
    protected void printMenu()
	{
		printMainTitle("Payment System");
		String[] menuList =
				{
						"Add/Edit Payment Account",
						"Print Invoice",
						"Make Payment",
						"Modify charges",
						"Generate Financial report"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}

  
       protected void modifyChargesMenu()
   	{
   		printMainTitle("Modify charges");
   		String[] menuList =
   				{
   						"Modify GST",
   						"Modify Service Charge",
   						"Apply/modify Discount",
   				};
   		printMenuList(menuList, "Go back to Payment System Menu");
   		System.out.println();
   	}
       
       protected void makePaymentMenu()
   	{
	    System.out.println();
   		printMainTitle("Payment");
   		String[] menuList =
   				{
   						"Cash",
   						"Card",
   				};
   		printMenuList(menuList);
   		System.out.println();
   	}
 
       
	protected void CreateBillingAccount()
	{
		printSubTitle("Create New Payment Account");
	}
	
	protected void invalidBillingAccount()
	{
		System.out.println("Payment Account does not exist!");
	}

    protected String requestRoomID()
	{
    	
        while (true) {
            String temp;
    		System.out.println("Room ID :");

            try {
                temp= scan.next();
                if(!temp.matches("\\d{4}")) {
                	throw new Exception("Invalid Room ID. Format: xxxx");
                }
                else return temp;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
	}
	 
    public void paymentProcess(String method,double money) {
		if(method=="CASH") {
			System.out.println("Pay By Cash:");
			boolean paying=true;
			while(paying) {
				double receive = this.readDouble(scan, "Cash Amount : ");
				if(receive>=money) {
					System.out.println("Paid Amount: $"+ receive);
					System.out.println("Return Amount: $"+ String.format("%.2f",(receive-money)));
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
    
    protected double readDouble(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
            	scan.nextLine();
                System.out.println("Please enter valid numeric number");
            }
        }
    }
    
    protected int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
            	scan.nextLine();
                System.out.println("Please enter valid integer number");
            }
        }
    }

    

}
