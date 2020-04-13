package com.company;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ReservationBoundary extends Boundary {
    Scanner scan = new Scanner(System.in);
//    public int handleMenu(String []menu)
//    {
//        int choice;
//        for (String s : menu) {
//            System.out.println(s);
//        }
//        choice = scan.nextInt();
//        return choice;
//    }

    protected void printMenu()
    {
        printMainTitle("Manage Reservations");
        String [] menuList = {
                "1. Reserve A Room",
                "2. Print All Reservations",
                "3. Get Reservation By Guest Name",
                "4. Cancel Reservation",
                "5. tests"
        };
        printMenuList(menuList, "Go back to Main Menu");
        System.out.println();
    }

//    public int printReservationMenu()
//    {
//        printMainTitle("Print Reservations");
//        String [] menuList = {
//                "1. Print by guest name",
//                "2. Print by room id",
//        };
//        printMenuList(menuList, "Go back to Reservation Menuu");
//        System.out.println();
//        return 1;
//    }

    public void printReservations(ArrayList<ReservationEntity> arrayList, Map<Integer,String> guestNames)
    {
        printMainTitle("All reservations");
        for (ReservationEntity reservationEntity : arrayList) {
            System.out.println(String.format("[Reservation ID]: %d \n" +
                            "Guest Name: %s \n" +
                            "Room Number: %s \n" +
                            "Reservation Ids: %s \n" +
                            "Start Date: %s \n" +
                            "End Date: %s\n" +
                            "Reservation State: %s",
                    reservationEntity.getReservationId(),
                    guestNames.get(reservationEntity.getGuestId()),
                    reservationEntity.getRoomId(),
                    reservationEntity.getWaitListRoomIds()!=null?reservationEntity.getWaitListRoomIds()[0]:"",
                    reservationEntity.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    reservationEntity.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    reservationEntity.getReservationState()
            ));
            printDivider();
        }
    }

    public void printReservationCancellationFailed()
    {
        printDivider();
        System.out.println("Failed to cancel reservation");
    }

    public void printReservationCancelled()
    {
        printDivider();
        System.out.println("The reservation has been cancelled");
    }

    public boolean printNoAvailableRooms()
    {
        printDivider();
        System.out.println("There are no available rooms\nWould you like to be wait listed?(Y/N)");
        String decision;
        do {
            decision = scan.next();
            decision = decision.toUpperCase();
        }while (!decision.equals("Y") && !decision.equals("N"));
        return decision.equals("Y");
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
            for (ReservationEntity reservationEntity : arrayList) {
                if (reservationEntity.getGuestId() == guestId && reservationEntity.getReservationState() == ReservationEntity.ReservationState.CONFIRMED) {
                    System.out.println(String.format("[Reservation ID]: %d \n" +
                                    "Room Number: %s \n" +
                                    "Start Date: %s \n" +
                                    "End Date: %s\n" +
                                    "Reservation State: %s",
                            reservationEntity.getReservationId(),
                            reservationEntity.getRoomId(),
                            reservationEntity.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            reservationEntity.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            reservationEntity.getReservationState()
                    ));
                }
            }
    }

    public ArrayList<GuestEntity> requestGuestName()
    {
        printMainTitle("Please enter the guest name");
        return new GuestController().searchGuest(scan.next());
    }

    public void listGuests(ArrayList<GuestEntity> guestEntityArrayList)
    {
        new GuestController().printGuestList(guestEntityArrayList);
    }

}
