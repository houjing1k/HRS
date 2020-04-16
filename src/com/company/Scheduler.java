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
    Timer timer12MN = new Timer();
    Timer testTimer = new Timer();
    Scheduler ()
    {
        Calendar currTime = Calendar.getInstance();
        int hour = currTime.get(Calendar.HOUR_OF_DAY);
        //System.out.println(currTime.get(Calendar.HOUR_OF_DAY));
        Calendar tempCalendar = Calendar.getInstance();
        Date date2pm = new java.util.Date();
        date2pm.setHours(14);
        date2pm.setMinutes(00);
        if(hour > 13)
        {
            //date2pm.set
            tempCalendar.setTime(date2pm);
            tempCalendar.add(Calendar.DATE,1);
            date2pm = tempCalendar.getTime();
        }
        System.out.println(date2pm);
        Date date12mn = new java.util.Date();
        date12mn.setHours(00);
        date12mn.setMinutes(00);
        tempCalendar.setTime(date12mn);
        tempCalendar.add(Calendar.DATE,1);
        date12mn = tempCalendar.getTime();
        timer2PM.schedule(new timerTask2PM(),date2pm,86400000);
        timer12MN.schedule(new timerTask12MN(),date12mn,86400000);
        testTimer.schedule(new testTimerTask(),1000,1000);
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
    class testTimerTask extends TimerTask {
        @Override
        public void run() {
            Date date2pm = new java.util.Date();
            //System.out.println(date2pm);
        }
    }

}

