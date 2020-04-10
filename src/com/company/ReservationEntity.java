package com.company;

import java.io.Serializable;
import java.util.Date;

public class ReservationEntity implements Serializable {
    enum ReservationState{
        RESERVED,
        CHECKED_IN,
        CANCELLED
    }
    Date startDate, endDate;
    int roomId, reservationId, guestId;
    ReservationState reservationState;
    ReservationEntity(Date startDate,Date endDate,int roomId,int reservationId, int guestId)
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

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getGuestId() {
        return guestId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getRoomId() {
        return roomId;
    }

    public ReservationState getReservationState() {
        return reservationState;
    }


}
