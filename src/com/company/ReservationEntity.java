package com.company;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class ReservationEntity implements Serializable {
    enum ReservationState{
        RESERVED,
        CHECKED_IN,
        CANCELLED
    }
    LocalDate startDate, endDate;
    String roomId;
    int reservationId, guestId;
    ReservationState reservationState;
    ReservationEntity(LocalDate startDate,LocalDate endDate,String roomId,int reservationId, int guestId)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomId = roomId;
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.reservationState = ReservationState.RESERVED;
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

    public void cancelReservation()
    {
        reservationState = ReservationState.CANCELLED;

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
