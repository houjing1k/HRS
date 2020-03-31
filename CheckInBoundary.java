package oodp;

import java.util.Scanner;

public class CheckInBoundary {
	Scanner sc = new Scanner(System.in);
	
	public int printMenu(String []m) {
		for(int i = 0 ; i < m.length ; i ++)
        {
            System.out.println(m[i]);
        }
		int sel = sc.nextInt();
		return sel;
	}
	
}
