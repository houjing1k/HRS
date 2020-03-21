package com.company;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ReservationController extends Controller {
    String [] menu = {
            "1. Reserve A Room",
            "2. Print All Reservations",
            "3. Get reservation"
    };
    Scanner scan = new Scanner(System.in);
    int choice;
    ArrayList<ReservationEntity> reservations = new ArrayList<>();
    ReservationBoundary reservationBoundary = new ReservationBoundary();
    ReservationController()
    {
        reservations = (ArrayList<ReservationEntity>) fromFile("reservationData");
        do {
            choice = reservationBoundary.handleMenu(menu);
            switch (choice)
            {
                case 1:
                    createReservation(1,scan.next(),scan.next());
                    break;
                case 2:
                    printAllReservations();
                    break;
                case 3:
                    reservationBoundary.getReservation(reservations,scan.nextInt());
                    break;
            }
        }while (choice >=0);
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

    public void createReservation(int roomId,String startDateString,String endDateString)
    {
        int newReservationId = reservations.size()!=0? reservations.get(reservations.size()-1).getReservationId() + 1:1;
        Date startDate,endDate;
        startDate = new Date(startDateString);
        endDate = new Date(endDateString);
        if(!isRoomReserved(roomId,startDate.toGMTString(),endDate.toGMTString())) {
            reservations.add(new ReservationEntity(startDate, endDate, 1, newReservationId, 2));
            saveReservationsTofIle();
        }
        else
        {
            System.out.println("Reservation Exists");
        }
    }

    private void saveReservationsTofIle()
    {
        toFile(reservations,"reservationData");
    }

    private void printAllReservations()
    {
        reservationBoundary.printReservations(reservations);
    }



}
