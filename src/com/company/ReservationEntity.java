package com.company;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class ReservationEntity implements Serializable {
    enum ReservationState{
        CONFIRMED,
        CHECKED_IN,
        WAITLISTED,
        EXPIRED,
        CANCELLED
    }
    LocalDate startDate, endDate;
    String roomId;
    String[] waitListRoomIds;
    int reservationId, guestId;
    ReservationState reservationState;
    ReservationEntity(LocalDate startDate,LocalDate endDate,int reservationId, int guestId,ReservationState reservationState,String[] waitListRoomIds)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomId = "";
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.reservationState = reservationState;
        this.waitListRoomIds = waitListRoomIds;
    }
    ReservationEntity(LocalDate startDate,LocalDate endDate,String roomId,int reservationId, int guestId,ReservationState reservationState)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomId = roomId;
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.reservationState = reservationState;
        this.waitListRoomIds = null;
    }
    @Override
    public String toString() {
        return "Reservation [reservationId=" + reservationId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", roomId=" + roomId +
                ", guestId=" + guestId +
                ", reservationState=" + reservationState +
                "]";
    }

    public void reserve(String roomId)
    {
        this.roomId = roomId;
        this.waitListRoomIds = null;
        reservationState = ReservationState.CONFIRMED;
    }

    public void cancelReservation()
    {
        reservationState = ReservationState.CANCELLED;
    }

    public void expireReservation()
    {
        reservationState = ReservationState.EXPIRED;
    }

    public String[] getWaitListRoomIds() {
        return waitListRoomIds;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getGuestId() {
        return guestId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getRoomId() {
        return roomId;
    }

    public ReservationState getReservationState() {
        return reservationState;
    }


}
