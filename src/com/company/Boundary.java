package com.company;

import java.util.Scanner;

public abstract class Boundary
{
	private final int menuLength = 50;

	public int process()
	{
		while (true)
		{
			try
			{
				Scanner sc = new Scanner(System.in);
				printMenu();
				String sel = sc.next();
				if (sel.matches("^[0-9]+$"))
					return Integer.parseInt(sel);
				else
					throw new Exception();
			} catch (Exception e)
			{
				invalidInputWarning();
				System.out.println();
			}
		}
		//return 0;
	}

	protected abstract void printMenu();

	public void printDivider()
	{
		printDivider(menuLength);
		System.out.println();
	}

	public void printDivider(int length)
	{
		for (int i = 0; i < length; i++)
		{
			System.out.print("~");
		}
	}

	public void printMainTitle(String title)
	{
		printDivider();
		printSubTitle(title);
		printDivider();
	}

	public void printSubTitle(String title)
	{
		int titleLen = title.length() + 2;
		int length = (menuLength - titleLen) / 2;
		if ((titleLen % 2) == 1)
			printDivider(length + 1);
		else
			printDivider(length);
		System.out.print(" " + title + " ");
		printDivider(length);
		System.out.println();
	}

	public void printMenuList(String[] menuList, String back)
	{
		int i = 1;
		for (String str : menuList)
		{
			System.out.println(i + " - " + str);
			i++;
		}
		printDivider();
		System.out.println(0 + " - " + back);
		printDivider();
	}

	public void waitInput()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("\nPress Enter to return.");
		sc.nextLine();
	}

	public void invalidInputWarning()
	{
		System.out.println("--Invalid Input--");
	}
}
