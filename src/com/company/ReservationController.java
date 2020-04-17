package com.company;

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

    public boolean isRoomAvailable(String roomId, LocalDate startDateRequest, LocalDate endDateRequest)
    {
        loadReservationsFromFile();
        for (ReservationEntity reservation : reservations) {
            //to check if the reservation has any clashes
            if (reservation.getRoomId().equals(roomId) &&
                    (reservation.getStartDate().isBefore(endDateRequest) ||
                            reservation.getStartDate().equals(endDateRequest)) &&
                    (startDateRequest.isBefore(reservation.getEndDate()) ||
                            startDateRequest.equals(reservation.getEndDate()))
                    && reservation.getReservationState() == ReservationEntity.ReservationState.CONFIRMED
            ) {
                return false;
            }
        }
        return true;
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

        ArrayList<RoomEntity> roomEntities = RoomController.getInstance().listRooms( RoomEntity.RoomStatus.VACANT,RoomEntity.RoomType.SINGLE, RoomEntity.BedType.SINGLE,true);
        ArrayList<String> tempRoomIDs = new ArrayList<>();
        for (RoomEntity roomEntity: roomEntities)
        {
            tempRoomIDs.add(roomEntity.getRoomId());
        }
        //String[]  = {"02-01","02-02"};

        boolean waitListDecision;
        boolean reserved = false;
        for (String tempRoomID : tempRoomIDs) {
            if (isRoomAvailable(tempRoomID, startDate, endDate)) {
                reservations.add(new ReservationEntity(startDate, endDate, tempRoomID, newReservationId, guestId, ReservationEntity.ReservationState.CONFIRMED));
                saveReservationsTofIle();
                reservationBoundary.printRoomHasBeenReserved(tempRoomID);
                reserved = true;
                break;
            }
        }
        if(!reserved)
        {
            waitListDecision = reservationBoundary.printNoAvailableRooms();
            if(waitListDecision)
            {
                reservations.add(new ReservationEntity(startDate, endDate, newReservationId, guestId, ReservationEntity.ReservationState.WAITLISTED,tempRoomIDs));
                saveReservationsTofIle();
            }
        }

    }

    public void waitListUpdate(String roomId)
    {
        loadReservationsFromFile();
        for(int i = 0; i < reservations.size();i++)
        {
            if(reservations.get(i).getReservationState()==ReservationEntity.ReservationState.WAITLISTED)
            {
                for(String waitListRoomId:reservations.get(i).getWaitListRoomIds())
                {
                    if(waitListRoomId.equals(roomId))
                    {
                        if (isRoomAvailable(roomId, reservations.get(i).getStartDate(), reservations.get(i).getEndDate())) {
                            System.out.println("Wait List Updated");
                            //reservations.add(new ReservationEntity(reservation.getStartDate(), reservation.getEndDate(), roomId, newReservationId, guestId));
                            reservations.get(i).reserve(roomId);
                            saveReservationsTofIle();
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean cancelReservation(int reservationId)
    {
        loadReservationsFromFile();
        boolean cancelled = false;
        for (ReservationEntity reservation : reservations) {
            //to check if the reservation has any clashes
            if (reservation.getReservationId() == reservationId && reservation.getReservationState() == ReservationEntity.ReservationState.CONFIRMED) {
                reservation.cancelReservation();
                saveReservationsTofIle();
                waitListUpdate(reservation.getRoomId());
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
                //guestNames.put(reservation.getGuestId(), String.valueOf(reservation.getGuestId()));
            }
        }
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
                    getReservationByGuestName();
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
                        reservationBoundary.printReservationCancelled();
                    }
                    else
                    {
                        reservationBoundary.printReservationCancellationFailed();
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

    public void triggerExpiredReservations()
    {
        for (ReservationEntity reservation : reservations) {
            if (reservation.getReservationState() == ReservationEntity.ReservationState.CONFIRMED && (reservation.getStartDate().isEqual(LocalDate.now()) || reservation.getStartDate().isBefore(LocalDate.now()))) {
                reservation.expireReservation();
                RoomController.getInstance().checkOut(reservation.getRoomId());
                waitListUpdate(reservation.getRoomId());
            }
        }
        saveReservationsTofIle();
    }

    public void getReservationByGuestName()
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
            if (reservation.getReservationState() == ReservationEntity.ReservationState.CONFIRMED && (reservation.getStartDate().isEqual(LocalDate.now()) || reservation.getStartDate().isBefore(LocalDate.now()))) {
                roomController.reserve(reservation.getRoomId(),reservation.getGuestId(),reservation.getReservationId());
            }
        }
        saveReservationsTofIle();
    }

    public ReservationEntity getReservationById(int reservationId)
    {
        for (ReservationEntity reservation: reservations)
        {
            if(reservation.getReservationId()==reservationId)
            {
                return  reservation;
            }
        }
        return null;
    }
}
