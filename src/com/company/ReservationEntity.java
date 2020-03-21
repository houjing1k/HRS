package com.company;

import java.io.Serializable;
import java.util.Date;

public class ReservationEntity implements Serializable {
	Date startDate, endDate;
	int roomId, reservationId, guestId;
	ReservationEntity(Date startDate,Date endDate,int roomId,int reservationId, int guestId)
	{
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomId = roomId;
		this.reservationId = reservationId;
		this.guestId = guestId;
	}
	@Override
	public String toString() {
		return "Reservation [id=" + roomId + ", startDate=" + startDate + ", endDate=" + endDate + ", reservationId=" + reservationId +"]";
	}
}
