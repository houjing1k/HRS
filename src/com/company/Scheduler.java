package com.company;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    Timer timer2PM = new Timer();
    Timer timer12MN = new Timer();
    Scheduler ()
    {
        Date date2pm = new java.util.Date();
        date2pm.setHours(14);
        date2pm.setMinutes(00);
        Date date12mn = new java.util.Date();
        date12mn.setHours(00);
        date12mn.setMinutes(00);
        timer2PM.schedule(new timerTask2PM(),date2pm,86400000);
        timer12MN.schedule(new timerTask12MN(),date12mn,86400000);
    }
    class timerTask2PM extends TimerTask {
        ReservationController reservationController = new ReservationController();
        @Override
        public void run() {
            System.out.println("All expired reservations have been cleared");
            reservationController.triggerExpiredReservations();
        }
    }
    class timerTask12MN extends TimerTask {
        ReservationController reservationController = new ReservationController();
        @Override
        public void run() {
            System.out.println("All reservations have been set");
            reservationController.setRoomReservationStatus();
        }
    }

}

