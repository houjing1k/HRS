package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
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
                "2. Print All Reservations",
                "3. Get Reservation By Guest Name",
                "4. Cancel Reservation"
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

    public void printReservations(ArrayList<ReservationEntity> arrayList, Map guestNames)
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        printMainTitle("All reservations");
        for(int i = 0 ; i < arrayList.size() ; i ++)
        {
            System.out.println(String.format("[Reservation ID]: %d \n" +
                            "Guest Name: %s \n" +
                            "Room Number: %s \n" +
                            "Start Date: %s \n" +
                            "End Date: %s\n" +
                            "Reservation State: %s",
                    arrayList.get(i).getReservationId(),
                    guestNames.get(arrayList.get(i).getGuestId()),
                    arrayList.get(i).getRoomId(),
                    arrayList.get(i).getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    arrayList.get(i).getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    arrayList.get(i).getReservationState()
            ));
            printDivider();
        }
    }

    public void printNoAvailableRooms()
    {
        printDivider();
        System.out.println("There are no available rooms");
    }

    public void printRoomHasBeenReserved(String roomID)
    {
        printDivider();
        System.out.println(String.format("The room %s has been reserved",roomID));
    }

    public void getReservation(ArrayList<ReservationEntity> arrayList, int guestId)
    {
        printMainTitle("Reservations for guest");
        if (arrayList.size() == 0)
        {
            System.out.println("This Guest has no reservations");
        }
        else
        for(int i = 0 ; i < arrayList.size() ; i ++)
        {
            if(arrayList.get(i).getGuestId()==guestId && arrayList.get(i).getReservationState() == ReservationEntity.ReservationState.RESERVED)
            {
                System.out.println(String.format("[Reservation ID]: %d \n" +
                                "Room Number: %s \n" +
                                "Start Date: %s \n" +
                                "End Date: %s\n" +
                                "Reservation State: %s",
                        arrayList.get(i).getReservationId(),
                        arrayList.get(i).getRoomId(),
                        arrayList.get(i).getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        arrayList.get(i).getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
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
