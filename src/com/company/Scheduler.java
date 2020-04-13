package com.company;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    Timer timer2PM = new Timer();
    Scheduler ()
    {
        Date date2pm = new java.util.Date();
        date2pm.setHours(14);
        date2pm.setMinutes(00);
        timer2PM.schedule(new cancelExpiredReservations(),date2pm,86400000);
    }
    class cancelExpiredReservations extends TimerTask {
        ReservationController reservationController = new ReservationController();
        @Override
        public void run() {
            System.out.println("All expired reservations have been cleared");
            reservationController.cancelExpiredReservations();
        }
    }

}

