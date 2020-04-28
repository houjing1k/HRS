package com.company;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PaymentBoundary extends Boundary{
    Scanner scan = new Scanner(System.in);
	
    protected void printMenu()
	{
		
	}
    
    protected void printRoomPriceMenu()
	{
		printMainTitle("Modify Room Price");
		String[] menuList =
				{
						"Single Room",
						"Double Room",
						"Deluxe Room",
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
  
    protected void modifyChargesMenu()
   	{
   		printMainTitle("Modify charges");
   		String[] menuList =
   				{
					    "Modify Room Price",
   						"Modify GST",
   						"Modify Service Charge",
   						"Apply / Modify Discount"
   				};
   		printMenuList(menuList, "Go back to Administrative Control");
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
    
	protected void invalidBillingAccount()
	{
		System.out.println("Bill does not exist!");
	}

    protected String requestRoomID()
	{
    	
        while (true) {
            String temp;
    		System.out.println("Room ID :");

            try {
                temp= scan.next();
                if(!temp.matches("\\d{4}")) {
                	throw new Exception();
                }
                else return temp;
            } catch (Exception e) {
                System.out.println("Invalid Room ID. Format: xxxx");
            }
        }
	}
	 
    public void paymentProcess(String method,double money) {
	    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    	if(method=="CASH") {
			System.out.println("Pay by Cash :");
			boolean paying=true;
			while(paying) { 
				double receive = this.readDouble(scan, "Cash Amount : ");
				if(receive>=money) {
					System.out.println("Paid Amount : "+ formatter.format(receive));
					System.out.println("Return Amount : "+ formatter.format(receive-money));
					paying =false;
				}
				else {
					System.out.println("Insufficient Amount!");
				}
			}
		}
		else {
			System.out.println("Pay by Card : ");
			System.out.println("Paid Amount : "+ formatter.format(money));
		}
		System.out.println("Thank You");
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
