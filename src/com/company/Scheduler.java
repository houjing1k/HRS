package com.company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    Timer timer2PM = new Timer();
    Timer timer11AM = new Timer();
    Timer testTimer = new Timer();
    Scheduler ()
    {
        Calendar currTime = Calendar.getInstance();
        int hour = currTime.get(Calendar.HOUR_OF_DAY);
        //System.out.println(currTime.get(Calendar.HOUR_OF_DAY));
        Calendar tempCalendar = Calendar.getInstance();
        Date date2pm = new java.util.Date();
        date2pm.setHours(14);
        date2pm.setMinutes(0);
        date2pm.setSeconds(0);
        if(hour > 13)
        {
            //date2pm.set
            tempCalendar.setTime(date2pm);
            tempCalendar.add(Calendar.DATE,1);
            date2pm = tempCalendar.getTime();
        }
        Date date11AM = new java.util.Date();
        date11AM.setHours(11);
        date11AM.setMinutes(00);
        date11AM.setSeconds(0);
        if(hour > 10)
        {
            tempCalendar.setTime(date11AM);
            tempCalendar.add(Calendar.DATE,1);
            date11AM = tempCalendar.getTime();
        }
        timer2PM.schedule(new timerTask2PM(),date2pm,86400000);
        timer11AM.schedule(new timerTask12MN(),date11AM,86400000);
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

