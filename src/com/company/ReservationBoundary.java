package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationBoundary extends Boundary {
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

    protected void printMenu()
    {
        printMainTitle("Manage Reservations");
        String [] menuList = {
                "1. Reserve A Room",
                "2. Print Reservations",
                "3. Get reservation",
                "4. Cancel reservation"
        };
        printMenuList(menuList, "Go back to Main Menu");
        System.out.println();
    }

    public int printReservationMenu()
    {
        printMainTitle("Print Reservations");
        String [] menuList = {
                "1. Print by guest name",
                "2. Print by room id",
        };
        printMenuList(menuList, "Go back to Reservation Menuu");
        System.out.println();
        return 1;
    }

    public void printReservations(ArrayList arrayList)
    {
        printMainTitle("All reservations");
        for(int i = 0 ; i < arrayList.size() ; i ++)
        {
            System.out.println(arrayList.get(i).toString());
        }
    }

    public void getReservation(ArrayList<ReservationEntity> arrayList, int guestId)
    {
        printMainTitle("Reservations for guest");
        for(int i = 0 ; i < arrayList.size() ; i ++)
        {
            if(arrayList.get(i).getGuestId()==guestId)
            {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                System.out.println(String.format("[Reservation ID]: %d \n" +
                                "Room Number: %d \n" +
                                "Start Date: %s \n" +
                                "End Date: %s\n" +
                                "Reservation State: %s",
                        arrayList.get(i).getReservationId(),
                        arrayList.get(i).getRoomId(),
                        dateFormat.format(arrayList.get(i).getStartDate()),
                        dateFormat.format(arrayList.get(i).getEndDate()),
                        arrayList.get(i).getReservationState()
                ));
            }
        }
    }

    public ArrayList<GuestEntity> requestGuestName()
    {
        printMainTitle("Please enter the guest name");
        ArrayList<GuestEntity> matches = new GuestController().searchGuest(scan.next());
        return matches;
    }

    public void listGuests(ArrayList<GuestEntity> guestEntityArrayList)
    {
        new GuestController().printGuestList(guestEntityArrayList);
    }

}
