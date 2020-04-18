package com.company;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ReservationBoundary extends Boundary {
    Scanner scan = new Scanner(System.in);
    private String [] menuRoomType = {
            "1. Single room",
            "2. Double room",
            "3. Deluxe room"
    };

    protected void printMenu()
    {
        printMainTitle("Manage Reservations");
        String [] menuList = {
                "1. Reserve A Room",
                "2. Print All Reservations",
                "3. View Reservations By Guest Name",
                "4. Cancel Reservation"
        };
        printMenuList(menuList, "Go back to Main Menu");
        System.out.println();
    }


    public void printReservations(ArrayList<ReservationEntity> arrayList, Map<Integer,String> guestNames)
    {
        printMainTitle("All reservations");
        StringBuilder waitListRoomIDs = new StringBuilder();
        String waitListRoomIDsString = "";
        for (ReservationEntity reservationEntity : arrayList) {
            if(reservationEntity.getWaitListRoomIds()!=null) {
                for (String waitListRoomID : reservationEntity.getWaitListRoomIds()) {
                    waitListRoomIDs.append(waitListRoomID).append(",");
                }
                if(waitListRoomIDs.length()!=0)
                    waitListRoomIDsString = waitListRoomIDs.substring(0,waitListRoomIDs.length()-1);
            }

            System.out.println(String.format("[Reservation ID]: %d \n" +
                            "Guest Name: %s \n" +
                            "Room Number: %s \n" +
                            "Wait List Room Ids: %s \n" +
                            "Start Date: %s \n" +
                            "End Date: %s\n" +
                            "Reservation State: %s",
                    reservationEntity.getReservationId(),
                    guestNames.get(reservationEntity.getGuestId()),
                    reservationEntity.getRoomId(),
                    waitListRoomIDsString,
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

    public void requestRoomRequirements()
    {
        boolean invalidDecision = false;
        RoomEntity.RoomType roomType ;
        int temp;
        printDivider();
        do {
            invalidDecision = false;
            System.out.println("Please type the room type you would like(single/double/duluxe)");
            temp = scan.nextInt();
            //  this.
            switch(this.process())
            {
                case 1:
                    roomType = RoomEntity.RoomType.SINGLE;
                    break;
                case 2:
                    roomType = RoomEntity.RoomType.DOUBLE;
                    break;
                case 3:
                    roomType = RoomEntity.RoomType.DELUXE;
                    break;
                default:
                    invalidDecision = true;
                    System.out.println("Invalid Input");

            }
        }while (invalidDecision);


    }

    public void getReservation(ArrayList<ReservationEntity> arrayList, int guestId,String guestName)
    {
        printMainTitle("Reservations for "+guestName);
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


}
