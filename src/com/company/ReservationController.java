package com.company;

import java.lang.reflect.Array;
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

    }

    public boolean isRoomAvailable(String roomId, LocalDate startDateRequest, LocalDate endDateRequest)
    {
        loadReservationsFromFile();
        for (ReservationEntity reservation : reservations) {
            //to check if the reservation has any clashes
            if (reservation.getRoomId().equals(roomId) &&
                    (reservation.getStartDate().isBefore(endDateRequest) ||
                            reservation.getStartDate().equals(endDateRequest)) &&
                    (startDateRequest.isBefore(reservation.getEndDate()) //||
                          //startDateRequest.equals(reservation.getEndDate())
                    )
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
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        GuestController guestController = new GuestController();
        String decision;
        LocalDate startDate,endDate;
        GuestEntity guestEntity=null;
        String selectedRoomId = "";
        ArrayList<RoomEntity> filteredRooms = null;
        ArrayList<String> waitListIds = new ArrayList<>();
        ArrayList<String> filteredRoomIDs = new ArrayList<>();
        int numOfAdults,numOfChildren;
        numOfAdults = numOfChildren = -1;
        boolean waitListDecision;
        boolean reserved = false;
        int guestId = -1;
        String tempString;
        startDate = endDate = null;
        //boolean loop = true;
        do {
            do {
                System.out.println("Has guest registered before?(Y/N)");
                decision = scan.next();
                decision = decision.toUpperCase();
            } while (!decision.equals("Y") && !decision.equals("N"));
            if (decision.equals("Y")) {
                guestEntity = guestController.searchGuest_Hybrid();
                //if(guestEntity == null)continue;
            } else {
                guestId = guestController.addGuest();
            }
        }while(guestEntity == null && guestId == -1);
        if(guestEntity != null)
            guestId = guestEntity.getGuestID();
        System.out.println("Key in the number of adults");
        do{
            numOfAdults = scan.nextInt();
            if(numOfAdults <= 0) System.out.println("Please key in a valid number of adults");
        }while (numOfAdults <= 0);
        System.out.println("Key in the number of children");
        do{
            numOfChildren = scan.nextInt();
            if(numOfChildren <= 0) System.out.println("Please key in a valid number of children");
        }while (numOfChildren <= 0);
        System.out.println("Please type the start date(dd/mm/yyyy):");
        while (startDate == null){
            try {
            	tempString = scan.next();
            	startDate = LocalDate.parse(tempString, dateFormat);

                if(startDate.isBefore(LocalDate.now().plusDays(1)))
                {
                    System.out.println("Please key in a date after today(dd/mm/yyyy):");
                    startDate = null;
                }
            }catch (Exception e)
            {
                //e.printStackTrace();
                System.out.println("Please use the format dd/mm/yyyy for start date");
            }
        }
        System.out.println("Please type the end date(dd/mm/yyyy):");
        while (endDate == null){
            try {
                tempString = scan.next();
                endDate = LocalDate.parse(tempString, dateFormat);
                if(endDate.isBefore(startDate))
                {
                    System.out.println("Please key in a date after the end date");
                    endDate = null;
                }
                if(endDate.equals(startDate))
                {
                    System.out.println("Please book at least one day");
                    endDate = null;
                }
            }catch (Exception e)
            {
                //e.printStackTrace();
                System.out.println("Please use the format 'dd/mm/yyyy' for end date");
            }
        }
        int newReservationId = reservations.size()!=0? reservations.get(reservations.size()-1).getReservationId() + 1:1;
        do {
            filteredRooms = RoomController.getInstance().filterRooms(2);
            if(filteredRooms.size()==0)
            {
                do {
                    System.out.println("Would you like to select a new room filter?(Y/N)");
                    decision = scan.next();
                    decision = decision.toUpperCase();
                } while (!decision.equals("Y") && !decision.equals("N"));
                if(decision.equals("Y"))
                    filteredRooms = null;
                else
                    return;
            }
        }while (filteredRooms == null);
        for (RoomEntity roomEntity: filteredRooms)
        {
            waitListIds.add(roomEntity.getRoomId());
        }
        filteredRooms = filterReservedRooms(filteredRooms,startDate,endDate);
        final LocalDate finalStartDate,finalEndDate;
        finalEndDate = endDate;
        finalStartDate = startDate;
        filteredRooms.removeIf(roomEntity -> !RoomController.getInstance().isRoomAvailable(roomEntity.getRoomId(),finalStartDate,finalEndDate));
        System.out.println(filteredRooms.size());
        if(filteredRooms.size()==0)
        {
            waitListDecision = reservationBoundary.printNoAvailableRooms();
            if(waitListDecision)
            {
                reservations.add(new ReservationEntity(startDate, endDate, newReservationId, guestId, ReservationEntity.ReservationState.WAITLISTED,waitListIds,numOfAdults,numOfChildren));
                saveReservationsToFile();
            }
            return;
        }
        for (RoomEntity roomEntity: filteredRooms)
        {
            filteredRoomIDs.add(roomEntity.getRoomId());
        }
        do {
            RoomVisualiser.showList(filteredRooms);
            selectedRoomId = scan.next();
            boolean invalidId = true;
            for(String filteredRoomID:filteredRoomIDs)
            {
                if (filteredRoomID.equals(selectedRoomId)) {
                    invalidId = false;
                    break;
                }
            }
            if(invalidId)
            {
                selectedRoomId = "";
                System.out.println("Sorry the ID you keyed in is not valid");
            }
        }while (selectedRoomId.equals(""));
        //String[]  = {"02-01","02-02"};
        reservations.add(new ReservationEntity(startDate, endDate, selectedRoomId, newReservationId, guestId, ReservationEntity.ReservationState.CONFIRMED,numOfAdults,numOfChildren));
        saveReservationsToFile();
        reservationBoundary.printRoomHasBeenReserved(getReservationById(newReservationId));

    }

    public ArrayList<RoomEntity> filterReservedRooms(ArrayList<RoomEntity> roomEntities, LocalDate startDateRequest, LocalDate endDateRequest)
    {
        loadReservationsFromFile();
        for(ReservationEntity reservation: reservations)
        {
            roomEntities.removeIf(roomEntity -> reservation.getRoomId().equals(roomEntity.getRoomId()) &&
                    (reservation.getStartDate().isBefore(endDateRequest) ||
                            reservation.getStartDate().equals(endDateRequest)) &&
                    (startDateRequest.isBefore(reservation.getEndDate()) //||
                            //startDateRequest.equals(reservation.getEndDate())
                    )
                    && reservation.getReservationState() == ReservationEntity.ReservationState.CONFIRMED);
        }
        return roomEntities;
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
                            saveReservationsToFile();
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
                saveReservationsToFile();
                waitListUpdate(reservation.getRoomId());
                cancelled = true;
            }
        }
        return cancelled;
    }

    private void saveReservationsToFile()
    {
        toFile(reservations,"data/reservations.ser");
    }

    private void loadReservationsFromFile() {
        reservations = fromFile("data/reservations.ser");
        if(reservations == null)
        {
            reservations = new ArrayList<>();
        }
    }

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
        GuestEntity guestEntity;
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
                    guestEntity = new GuestController().searchGuest_Hybrid();
                    if(guestEntity == null) continue;
                    guestId = guestEntity.getGuestID();
                    reservationBoundary.getReservation(reservations,guestId,guestEntity.getName());
                    System.out.println("Key in the ID of the reservation");
                    reservationId = scan.nextInt();
                    if(cancelReservation(reservationId))
                        reservationBoundary.printReservationCancelled();
                    else
                        reservationBoundary.printReservationCancellationFailed();

                    break;
                case 0: break;
                default:
                    System.out.println("--Invalid Input--");
            }
        }while (choice != 0);
    }

    public void triggerExpiredReservations()
    {
        for (int i = 0; i < reservations.size();i++) {
            if (reservations.get(i).getReservationState() == ReservationEntity.ReservationState.CONFIRMED && (reservations.get(i).getStartDate().isEqual(LocalDate.now()) || reservations.get(i).getStartDate().isBefore(LocalDate.now()))) {
                reservations.get(i).expireReservation();
                saveReservationsToFile();
                RoomController.getInstance().checkOut(reservations.get(i).getRoomId());
                waitListUpdate(reservations.get(i).getRoomId());
            }
        }
    }

    public void getReservationByGuestName()
    {
        int guestId;
        GuestEntity guestEntity;
        guestEntity = new GuestController().searchGuest_Hybrid();
        if(guestEntity==null) return;
        guestId = guestEntity.getGuestID();
        reservationBoundary.getReservation(reservations,guestId,guestEntity.getName());
    }

    public void setRoomReservationStatus()
    {
        RoomController roomController = RoomController.getInstance();
        for (ReservationEntity reservation : reservations) {
            if (reservation.getReservationState() == ReservationEntity.ReservationState.CONFIRMED && (reservation.getStartDate().isEqual(LocalDate.now()) || reservation.getStartDate().isBefore(LocalDate.now()))) {
                roomController.reserve(reservation.getRoomId(),reservation.getGuestId(),reservation.getReservationId());
            }
        }
        saveReservationsToFile();
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

    public boolean checkInReservation(int reservationId)
    {
        for(int i = 0; i < reservations.size();i++)
        {
            if (reservations.get(i).getReservationId()==reservationId)
            {
                reservations.get(i).checkIn();
                saveReservationsToFile();
                return true;
            }
        }
        return false;
    }

    public ArrayList<ReservationEntity> getReservations ()
    {
        return reservations;
    }

    public ReservationEntity getReservationByRoomId(String roomId)
    {
        for (ReservationEntity reservation: reservations)
        {
            if(reservation.getRoomId().equals(roomId))
            {
                return  reservation;
            }
        }
        return null;
    }
}
