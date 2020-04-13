package com.company;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private boolean isRoomReserved(int roomId, LocalDate startDateRequest, LocalDate endDateRequest)
    {
        for(int i = 0 ; i < reservations.size() ; i ++)
        {
            //to check if the reservation has any clashes
            if(reservations.get(i).getRoomId() == roomId &&
                    (reservations.get(i).getStartDate().isBefore(endDateRequest) ||
                    reservations.get(i).getStartDate().equals(endDateRequest)) &&
                    (startDateRequest.isBefore(reservations.get(i).getEndDate()) ||
                            startDateRequest.equals(reservations.get(i).getEndDate()))
                    && reservations.get(i).getReservationState() == ReservationEntity.ReservationState.RESERVED
            )
            {
                return true;
            }
        }
        return false;
    }

    public void createReservation(int roomId)
    {
        ArrayList<GuestEntity> guestEntityArrayList;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Has guest registered before?(Y/N)");
        String decision;
        LocalDate startDate,endDate;
        int guestId;
        do {
            decision = scan.next();
            decision = decision.toUpperCase();
        }while (!decision.equals("Y") && !decision.equals("N"));
        if(decision.equals("Y"))
        {
            guestEntityArrayList = reservationBoundary.requestGuestName();
            reservationBoundary.listGuests(guestEntityArrayList);
            System.out.println("Key in the ID of the guest");
            guestId = scan.nextInt();
        }
        else
        {
            guestId = new GuestController().addGuest();
        }
        String tempString;
        startDate = endDate = null;
        System.out.println("Please type the start date(dd/mm/yyyy):");
        while (startDate == null){
            try {
                tempString = scan.next();
                startDate = LocalDate.parse(tempString, dateFormat);
            }catch (Exception e)
            {
                //e.printStackTrace();
                System.out.println("Please use the format dd/mm/yyyy for end date");
            }
        }
        System.out.println("Please type the end date(mm/dd/yyyy):");
        while (endDate == null){
            try {
                tempString = scan.next();
                endDate = LocalDate.parse(tempString, dateFormat);
                if(endDate.isBefore(startDate))
                {
                    System.out.println("Please key in a date after the start date");
                    endDate = null;
                }
            }catch (Exception e)
            {
                //e.printStackTrace();
                System.out.println("Please use the format 'dd/mm/yyyy' for end date");
            }
        }
        int newReservationId = reservations.size()!=0? reservations.get(reservations.size()-1).getReservationId() + 1:1;
        if(!isRoomReserved(roomId,startDate,endDate)) {
            reservations.add(new ReservationEntity(startDate, endDate, 1, newReservationId, guestId));
            saveReservationsTofIle();
            reservationBoundary.printRoomHasBeenReserved();
        }
        else
        {
            reservationBoundary.printNoAvailableRooms();
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
        GuestController guestController = new GuestController();
        Map<Integer, String> guestNames = new HashMap<>();
        ArrayList<Integer> addedNames = new ArrayList<>();
        boolean addedNamesBool;
        for(int i = 0; i < reservations.size(); i++)
        {
            addedNamesBool = false;
            for(int x = 0; x < addedNames.size();x++){
                if (addedNames.get(x) == reservations.get(i).getGuestId())
                    addedNamesBool = true;
            }
            if(!addedNamesBool)
                guestNames.put(reservations.get(i).getGuestId(),guestController.searchGuest(reservations.get(i).guestId).getName());
        }
        //new GuestController()
        reservationBoundary.printReservations(reservations,guestNames);
    }


    @Override
    public void processMain() {
        int guestId;
        int reservationId;
        ArrayList<GuestEntity> guestEntityArrayList;
        do {
            choice = reservationBoundary.process();
            switch (choice)
            {
                case 1:
                    createReservation(1);
                    break;
                case 2:
                    printAllReservations();
                    break;
                case 3:
                    guestEntityArrayList = reservationBoundary.requestGuestName();
                    reservationBoundary.listGuests(guestEntityArrayList);
                    System.out.println("Key in the ID of the guest");
                    guestId = scan.nextInt();
                    reservationBoundary.getReservation(reservations,guestId);
                    break;
                case 4:
                    guestEntityArrayList = reservationBoundary.requestGuestName();
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
