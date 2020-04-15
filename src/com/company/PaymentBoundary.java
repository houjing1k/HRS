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
						"Add items to Bill",
						"Print Invoice",
						"Make Payment",
						"Modify charges",
						"Generate Financial report"
				};
		printMenuList(menuList, "Go back to Main Menu");
		System.out.println();
	}
    protected void editAccountMenu()
   	{
   		printMainTitle("Edit Payment Account");
   		String[] menuList =
   				{
   						"Edit Payment method",
   						"Update Credit Card Detail",
   				};
   		printMenuList(menuList, "Go back to Payment System Menu");
   		System.out.println();
   	}
       protected void modifyAccountMenu()
   	{
   		printMainTitle("Add/Edit Payment Account");
   		String[] menuList =
   				{
   						"Add Payment Account",
   						"Edit Payment Account",
   						"View All Payment Account",
   						"Delete Payment Account"
   				};
   		printMenuList(menuList, "Go back to Payment System Menu");
   		System.out.println();
   	}
       
       protected void addItemMenu()
   	{
   		printMainTitle("add Items to Bill");
   		String[] menuList =
   				{
   						"Add Room to Bill",
   						"Add Room Service Records to Bill",

   				};
   		printMenuList(menuList, "Go back to Payment System Menu");
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
   		printMainTitle("Payment");
   		String[] menuList =
   				{
   						"Cash",
   						"Card",
   				};
   		printMenuList(menuList, "Go back to Payment System Menu");
   		System.out.println();
   	}
       
       
       
	protected void CreatePaymentAccount()
	{
		printSubTitle("Create New Payment Account");
	}
	protected void invalidPaymentAccount()
	{
		System.out.println("Payment Account does not exist!");
	}
    
	protected void CreatePaymentDetail()
	{
		printSubTitle("Enter Credit Card");
	}
	
   
    protected String requestRoomID()
	{
		return readString(scan, "Enter Room Id :");

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
				double receive = this.readDouble(scan, "Cash Amount : ");
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
    
    protected String readString(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.next();
            } catch (InputMismatchException e) {
            	scan.nextLine();
                System.out.println("Please enter valid string");
            }
        }
    }
    protected LocalDate readDate(Scanner scanner, String message) {
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println(message);
            try {
        		return LocalDate.parse(scanner.next(),formatter);

            } catch (DateTimeParseException exc) {
                System.out.println("Please enter the date in this format (dd/MM/yyyy)");
            }
        }
    }
    
    protected String readStrictlyString(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            String temp;
            try {
                temp= scanner.next();
                if(!temp.matches("^[ A-Za-z]+$")) {
                	throw new Exception("Invalid input. Only alphabetical accepted");
                }
                else return temp;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
    
	protected String readCreditCardNo(Scanner scanner, String message)
	{
        while (true) {
            System.out.println(message);
            String temp;
            try {
                temp= scanner.next();
                if(!temp.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
                	throw new Exception("Invalid Credit Card Number.Format: xxxx-xxxx-xxxx-xxxx");
                }
                else return temp;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
	}

}
