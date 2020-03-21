package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
	Scanner scan = new Scanner(System.in);
	public int handleMenu(String []menu)
	{
		int choice;
		for(int i = 0 ; i < menu.length ; i ++)
		{
			System.out.println(menu[i]);
		}
		choice = scan.nextInt();
		return choice;
	}

	public void toFile(ArrayList<?> arrayList, String fileName)
	{
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(arrayList); // write MenuArray to ObjectOutputStream
			oos.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<?> fromFile(String fileName)
	{
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList <?> arrayList;
			arrayList = (ArrayList <?>)ois.readObject();
			ois.close();
			return arrayList;
		} catch(Exception ex) {
			//ex.printStackTrace();
			return new ArrayList<>();
		}
	}
}