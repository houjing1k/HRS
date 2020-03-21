package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationBoundary {
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

    public void printReservations(ArrayList arrayList)
    {
        for(int i = 0 ; i < arrayList.size() ; i ++)
        {
            System.out.println(arrayList.get(i).toString());
        }
    }

    public void getReservation(ArrayList<ReservationEntity> arrayList, int guestId)
    {
        for(int i = 0 ; i < arrayList.size() ; i ++)
        {
            if(arrayList.get(i).getGuestId()==guestId)
            {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                System.out.println(String.format("You have a reservation for room number: %d from %s to %s",
                        arrayList.get(i).getRoomId(),
                        dateFormat.format(arrayList.get(i).getStartDate()),
                        dateFormat.format(arrayList.get(i).getEndDate())
                ));
            }
        }
    }

}
