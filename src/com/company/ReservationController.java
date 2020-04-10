package com.company;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ReservationController extends Controller {
    Scanner scan = new Scanner(System.in);
    int choice;
    ArrayList<ReservationEntity> reservations = new ArrayList<>();
    ReservationBoundary reservationBoundary = new ReservationBoundary();
    ReservationController()
    {
        reservations = (ArrayList<ReservationEntity>) fromFile("reservationData");
        if(reservations == null)
        {
            reservations = new ArrayList<>();
        }

    }

    private boolean isRoomReserved(int roomId, String startDateRequestString, String endDateRequestString)
    {
        Date startDateRequest = new Date(startDateRequestString);
        Date endDateRequest = new Date(endDateRequestString);
        for(int i = 0 ; i < reservations.size() ; i ++)
        {
            //to check if the reservation has any clashes
            if(reservations.get(i).getRoomId() == roomId &&
                    (reservations.get(i).getStartDate().before(endDateRequest) ||
                    reservations.get(i).getStartDate().equals(endDateRequest)) &&
                    (startDateRequest.before(reservations.get(i).getEndDate()) ||
                            startDateRequest.equals(reservations.get(i).getEndDate()))
            )
            {
                return true;
            }
        }
        return false;
    }

    public void createReservation(int roomId,String startDateString,String endDateString,int guestId)
    {
        int newReservationId = reservations.size()!=0? reservations.get(reservations.size()-1).getReservationId() + 1:1;
        Date startDate,endDate;
        startDate = new Date(startDateString);
        endDate = new Date(endDateString);
        if(!isRoomReserved(roomId,startDate.toGMTString(),endDate.toGMTString())) {
            reservations.add(new ReservationEntity(startDate, endDate, 1, newReservationId, guestId));
            saveReservationsTofIle();
        }
        else
        {
            System.out.println("Reservation Exists");
        }
    }

    public boolean cancelReservation(int reservationId)
    {
        boolean cancelled = false;
        for(int i = 0 ; i < reservations.size() ; i ++)
        {
            //to check if the reservation has any clashes
            if(reservations.get(i).getReservationId() == reservationId && reservations.get(i).getReservationState() == ReservationEntity.ReservationState.RESERVED)
            {
                reservations.get(i).cancelReservation();
                saveReservationsTofIle();
                cancelled = true;
            }
        }
        return cancelled;
    }

    private void saveReservationsTofIle()
    {
        toFile(reservations,"reservationData");
    }

    private void printAllReservations()
    {
        reservationBoundary.printReservations(reservations);
    }


    @Override
    public void processMain() {
        int guestId;
        int reservationId;
        do {
            choice = reservationBoundary.process();
            switch (choice)
            {
                case 1:
                    System.out.println("Has guest registered before?(Y/N");
                    String decision;
                    do {
                        decision = scan.next();
                        decision = decision.toUpperCase();
                    }while (!decision.equals("Y") && !decision.equals("N"));
                    if(decision.equals("Y"))
                    {
                        ArrayList<GuestEntity> guestEntityArrayList = reservationBoundary.requestGuestName();
                        reservationBoundary.listGuests(guestEntityArrayList);
                        System.out.println("Key in the ID of the guest");
                        guestId = scan.nextInt();
                    }
                    else
                    {
                        guestId = new GuestController().addGuest();
                    }
                    System.out.println("Please type the start date(mm/dd/yy):");
                    String startDate = scan.next();
                    System.out.println("Please type the end date(mm/dd/yy):");
                    String endDate = scan.next();
                    createReservation(1,startDate,endDate,guestId);
                    break;
                case 2:
                    printAllReservations();
                    break;
                case 3:
                    reservationBoundary.getReservation(reservations,scan.nextInt());
                    break;
                case 4:
                    ArrayList<GuestEntity> guestEntityArrayList = reservationBoundary.requestGuestName();
                    reservationBoundary.listGuests(guestEntityArrayList);
                    System.out.println("Key in the ID of the guest");
                    guestId = scan.nextInt();
                    reservationBoundary.getReservation(reservations,guestId);
                    System.out.println("Key in the ID of the reservation");
                    reservationId = scan.nextInt();
                    if(cancelReservation(reservationId))
                    {

                    }
                    else
                    {

                    }

                    break;
            }
        }while (choice > 0);
    }
}
