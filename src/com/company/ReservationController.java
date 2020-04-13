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
        loadReservationsFromFile();
        if(reservations == null)
        {
            reservations = new ArrayList<>();
        }

    }

    private boolean isRoomReserved(String roomId, LocalDate startDateRequest, LocalDate endDateRequest)
    {
        loadReservationsFromFile();
        for (ReservationEntity reservation : reservations) {
            //to check if the reservation has any clashes
            if (reservation.getRoomId().equals(roomId) &&
                    (reservation.getStartDate().isBefore(endDateRequest) ||
                            reservation.getStartDate().equals(endDateRequest)) &&
                    (startDateRequest.isBefore(reservation.getEndDate()) ||
                            startDateRequest.equals(reservation.getEndDate()))
                    && reservation.getReservationState() == ReservationEntity.ReservationState.RESERVED
            ) {
                return true;
            }
        }
        return false;
    }

    public void createReservation()
    {
        loadReservationsFromFile();
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
        String[] tempRoomIDs = {"02-01","02-02","02-03","02-04","02-05"};
        for (String tempRoomID : tempRoomIDs) {
            if (!isRoomReserved(tempRoomID, startDate, endDate)) {
                reservations.add(new ReservationEntity(startDate, endDate, tempRoomID, newReservationId, guestId));
                saveReservationsTofIle();
                reservationBoundary.printRoomHasBeenReserved(tempRoomID);
                break;
            }
            reservationBoundary.printNoAvailableRooms();
        }
    }

    public boolean cancelReservation(int reservationId)
    {
        loadReservationsFromFile();
        boolean cancelled = false;
        for (ReservationEntity reservation : reservations) {
            //to check if the reservation has any clashes
            if (reservation.getReservationId() == reservationId && reservation.getReservationState() == ReservationEntity.ReservationState.RESERVED) {
                reservation.cancelReservation();
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

    private void loadReservationsFromFile() {reservations = fromFile("reservationData");}

    private void printAllReservations()
    {
        loadReservationsFromFile();
        GuestController guestController = new GuestController();
        Map<Integer, String> guestNames = new HashMap<>();
        ArrayList<Integer> addedIds = new ArrayList<>();
        boolean addedNamesBool;
        for (ReservationEntity reservation : reservations) {
            addedNamesBool = false;
            for (Integer addedId : addedIds) {
                if (addedId == reservation.getGuestId()) {
                    addedNamesBool = true;
                    break;
                }
            }
            if (!addedNamesBool) {
                addedIds.add(reservation.getGuestId());
                guestNames.put(reservation.getGuestId(), guestController.searchGuest(reservation.guestId).getName());
            }
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
                    createReservation();
                    break;
                case 2:
                    printAllReservations();
                    break;
                case 3:
                    getReservationByGuestId();
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
                case 5:
                    ArrayList<RoomEntity> tenp = RoomController.getInstance().listRooms(RoomEntity.RoomStatus.VACANT);
                    for(RoomEntity tem:tenp)
                    {
                        System.out.println(tem.getRoomId());
                    }
                    System.out.println("test");

            }
        }while (choice > 0);
    }

    public void cancelExpiredReservations()
    {
        for (ReservationEntity reservation : reservations) {
            if (reservation.getReservationState() == ReservationEntity.ReservationState.RESERVED && (reservation.getStartDate().isEqual(LocalDate.now()) || reservation.getStartDate().isBefore(LocalDate.now()))) {
                reservation.cancelReservation();
            }
        }
        saveReservationsTofIle();
    }

    public void getReservationByGuestId()
    {
        ArrayList<GuestEntity> guestEntityArrayList;
        int guestId;
        guestEntityArrayList = reservationBoundary.requestGuestName();
        reservationBoundary.listGuests(guestEntityArrayList);
        System.out.println("Key in the ID of the guest");
        guestId = scan.nextInt();
        reservationBoundary.getReservation(reservations,guestId);
    }

    public void setRoomReservationStatus()
    {
        RoomController roomController = RoomController.getInstance();
        for (ReservationEntity reservation : reservations) {
            if (reservation.getReservationState() == ReservationEntity.ReservationState.RESERVED && (reservation.getStartDate().isEqual(LocalDate.now()) || reservation.getStartDate().isBefore(LocalDate.now()))) {
                roomController.reserve(reservation.getRoomId(),reservation.getGuestId(),reservation.getReservationId());
            }
        }
        saveReservationsTofIle();
    }
}
