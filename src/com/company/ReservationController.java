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
    String[] editReservationMenu = {"Edit start date","Edit end date","Edit number of children", "Edit number of adults", "Change room"};
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
                    (reservation.getStartDate().isBefore(endDateRequest) //||
                            //reservation.getStartDate().equals(endDateRequest)
                            )
                    &&
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
        GuestController guestController = new GuestController();
        String decision;
        LocalDate startDate,endDate;
        GuestEntity guestEntity=null;
        String selectedRoomId = "";
        ArrayList<RoomEntity> filteredRooms ;
        ArrayList<String> waitListIds = new ArrayList<>();
        ArrayList<String> filteredRoomIDs = new ArrayList<>();
        int numOfAdults,numOfChildren;
        boolean waitListDecision;
        int guestId = -1;
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
        numOfAdults = reservationBoundary.selectNumAdult();
        numOfChildren = reservationBoundary.selectNumChild(numOfAdults);
        startDate = reservationBoundary.getStartDateInput("Please type the start date(dd/mm/yyyy):");
        endDate = reservationBoundary.getEndDateInput("Please type the end date(dd/mm/yyyy):",startDate);
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
        final LocalDate finalStartDate,finalEndDate;
        finalEndDate = endDate;
        finalStartDate = startDate;
        filteredRooms.removeIf(roomEntity -> !isRoomAvailable(roomEntity.getRoomId(),finalStartDate,finalEndDate));
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
        selectedRoomId = reservationBoundary.selectRoom(filteredRooms);
        //String[]  = {"02-01","02-02"};
        reservations.add(new ReservationEntity(startDate, endDate, selectedRoomId, newReservationId, guestId, ReservationEntity.ReservationState.CONFIRMED,numOfAdults,numOfChildren));
        saveReservationsToFile();
        reservationBoundary.printRoomHasBeenReserved(getReservationById(newReservationId));

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
        int sel;
        boolean redecide;
        LocalDate date;
        ReservationEntity reservationEntity;
        ArrayList<RoomEntity> roomEntities;
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
                case 5:
                    guestEntity = new GuestController().searchGuest_Hybrid();
                    if(guestEntity == null) continue;
                    guestId = guestEntity.getGuestID();
                    if(reservationBoundary.getReservation(reservations,guestId,guestEntity.getName())==0)
                    {
                        break;
                    }
                    System.out.println("Key in the ID of the reservation");
                    reservationId = scan.nextInt();
                    reservationEntity = getReservationById(reservationId);
                    reservationBoundary.printMenuList(editReservationMenu);
                    sel = reservationBoundary.getInput(1,5);
                    switch (sel)
                    {
                        case 1:
                            do {
                                date = reservationBoundary.getStartDateInput("Please key in a new start date: ");
                                System.out.println(String.format("Your current start date is on: %s", reservationEntity.getStartDate().toString()));
                                ArrayList<RoomEntity> tempArrayList = new ArrayList<>();
                                tempArrayList.add(RoomController.getInstance().getRoom(reservationEntity.getRoomId()));
                                RoomVisualiser.showSchedule(tempArrayList, reservations, reservationEntity.getStartDate().minusDays(20));
                                reservationBoundary.printMenuList(new String[]{"Use the same room","Change to similar room"});
                                sel = reservationBoundary.getInput(1,2);
                                switch (sel)
                                {
                                    case 1:
                                        if (!isRoomAvailable(reservationEntity.getRoomId(), date, reservationEntity.getStartDate())) {
                                            System.out.println(String.format("Room %s is not available on %s\nWould you to:", reservationEntity.getRoomId(), date));
                                            redecide = true;
                                            //reservationBoundary.printMenuList({"Change to similar room","Change start date"});
                                        }
                                        else
                                        {
                                            reservationEntity.setStartDate(date);
                                            reservations.set(reservations.indexOf(getReservationById(reservationId)),reservationEntity);
                                            saveReservationsToFile();
                                            System.out.println("Your start date has been successfully changed");
                                            redecide = false;
                                        }
                                        break;
                                    case 2:
                                        roomEntities = RoomController.getInstance().filterRooms(2);
                                        final LocalDate startDate,endDate;
                                        final String roomId;
                                        startDate = date;
                                        endDate = reservationEntity.getEndDate();
                                        roomId = reservationEntity.getRoomId();
                                        roomEntities.removeIf(roomEntity -> !isRoomAvailable(roomEntity.getRoomId(),startDate,endDate));
                                        roomEntities.removeIf(roomEntity -> !RoomController.getInstance().isRoomAvailable(roomEntity.getRoomId(),startDate,endDate));
                                        if(roomEntities.size()==0)
                                        {
                                            System.out.println("Sorry there are no rooms that suit your new start date");
                                            redecide = false;
                                        }
                                        else {
                                            RoomVisualiser.showList(roomEntities);
                                            String selectedRoomId = reservationBoundary.selectRoom(roomEntities);
                                            int newReservationId = reservations.size()!=0? reservations.get(reservations.size()-1).getReservationId() + 1:1;
                                            reservations.add(new ReservationEntity(startDate, endDate, selectedRoomId, newReservationId, reservationEntity.getGuestId(), ReservationEntity.ReservationState.CONFIRMED,reservationEntity.getNumOfAdults(),reservationEntity.getNumOfChildren()));
                                            saveReservationsToFile();
                                            cancelReservation(reservationId);
                                            reservationBoundary.printRoomHasBeenReserved(getReservationById(newReservationId));
                                            redecide = false;
                                        }
                                        break;
                                    default:
                                        redecide = false;
                                }

                            }while (redecide);
                            break;
                        case 2:
                            do {
                                date = reservationBoundary.getEndDateInput("Please key in a new end date: ",reservationEntity.getStartDate());
                                System.out.println(String.format("Your current end date is on: %s", reservationEntity.getEndDate().toString()));
                                ArrayList<RoomEntity> tempArrayList = new ArrayList<>();
                                tempArrayList.add(RoomController.getInstance().getRoom(reservationEntity.getRoomId()));
                                RoomVisualiser.showSchedule(tempArrayList, reservations, reservationEntity.getEndDate());
                                reservationBoundary.printMenuList(new String[]{"Use the same room","Change to similar room"});
                                sel = reservationBoundary.getInput(1,2);
                                switch (sel)
                                {
                                    case 1:
                                        if (!isRoomAvailable(reservationEntity.getRoomId(), reservationEntity.getEndDate(), date)) {
                                            System.out.println(String.format("Room %s is not available on %s\nWould you to:", reservationEntity.getRoomId(), date));
                                            redecide = true;
                                            //reservationBoundary.printMenuList({"Change to similar room","Change start date"});
                                        }
                                        else
                                        {
                                            reservationEntity.setEndDate(date);
                                            reservations.set(reservations.indexOf(getReservationById(reservationId)),reservationEntity);
                                            saveReservationsToFile();
                                            System.out.println("Your start date has been successfully changed");
                                            redecide = false;
                                        }
                                        break;
                                    case 2:
                                        roomEntities = RoomController.getInstance().filterRooms(2);
                                        final LocalDate startDate,endDate;
                                        final String roomId;
                                        startDate = reservationEntity.getStartDate();
                                        endDate = date;
                                        roomId = reservationEntity.getRoomId();
                                        roomEntities.removeIf(roomEntity -> !isRoomAvailable(roomEntity.getRoomId(),startDate,endDate));
                                        roomEntities.removeIf(roomEntity -> !RoomController.getInstance().isRoomAvailable(roomEntity.getRoomId(),startDate,endDate));
                                        if(roomEntities.size()==0)
                                        {
                                            System.out.println("Sorry there are no rooms that suit your new end date");
                                            redecide = false;
                                        }
                                        else {
                                            RoomVisualiser.showList(roomEntities);
                                            String selectedRoomId = reservationBoundary.selectRoom(roomEntities);
                                            int newReservationId = reservations.size()!=0? reservations.get(reservations.size()-1).getReservationId() + 1:1;
                                            reservations.add(new ReservationEntity(startDate, endDate, selectedRoomId, newReservationId, reservationEntity.getGuestId(), ReservationEntity.ReservationState.CONFIRMED,reservationEntity.getNumOfAdults(),reservationEntity.getNumOfChildren()));
                                            saveReservationsToFile();
                                            cancelReservation(reservationId);
                                            reservationBoundary.printRoomHasBeenReserved(getReservationById(newReservationId));
                                            redecide = false;
                                        }
                                        break;
                                    default:
                                        redecide = false;
                                }

                            }while (redecide);
                            break;
                        case 3:
                            int numberOfChildren;
                            System.out.println("Key in the new number of children");
                            numberOfChildren = reservationBoundary.selectNumChild(reservationEntity.getNumOfAdults());
                            reservationEntity.setNumOfChildren(numberOfChildren);
                            reservations.set(reservations.indexOf(getReservationById(reservationId)),reservationEntity);
                            saveReservationsToFile();
                            System.out.println("Your start date has been successfully changed");
                            break;
                        case 4:
                            int numberOfAdults;
                            System.out.println("Key in the new number of adults");
                            numberOfAdults = reservationBoundary.selectNumAdult();
                            reservationEntity.setNumOfAdults(numberOfAdults);
                            reservations.set(reservations.indexOf(getReservationById(reservationId)),reservationEntity);
                            saveReservationsToFile();
                            System.out.println("Your start date has been successfully changed");
                            break;
                        case 5:
                            roomEntities = RoomController.getInstance().filterRooms(2);
                            final LocalDate startDate,endDate;
                            startDate = reservationEntity.getStartDate();
                            endDate = reservationEntity.getEndDate();
                            roomEntities.removeIf(roomEntity -> !isRoomAvailable(roomEntity.getRoomId(),startDate,endDate));
                            roomEntities.removeIf(roomEntity -> !RoomController.getInstance().isRoomAvailable(roomEntity.getRoomId(),startDate,endDate));
                            if(roomEntities.size()==0)
                            {
                                System.out.println("Sorry there are no rooms that suit your reservation requirements");
                            }
                            else {
                                RoomVisualiser.showList(roomEntities);
                                String selectedRoomId = reservationBoundary.selectRoom(roomEntities);
                                int newReservationId = reservations.size()!=0? reservations.get(reservations.size()-1).getReservationId() + 1:1;
                                reservations.add(new ReservationEntity(startDate, endDate, selectedRoomId, newReservationId, reservationEntity.getGuestId(), ReservationEntity.ReservationState.CONFIRMED,reservationEntity.getNumOfAdults(),reservationEntity.getNumOfChildren()));
                                saveReservationsToFile();
                                cancelReservation(reservationId);
                                reservationBoundary.printRoomHasBeenReserved(getReservationById(newReservationId));
                                redecide = false;
                            }
                            break;

                    }
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

    private boolean validNumberOfPeople(int numOfChildren,int numOfAdults)
    {
        if(numOfAdults == 0)
        {
            System.out.println("There needs to be at least 1 adult in the room");
            return false;
        }
        if(numOfChildren+numOfAdults > 4)
        {
            System.out.println(String.format("The total number of people is %s which exceed 4",numOfAdults+numOfChildren));
            return false;
        }
        return true;
    }


}
