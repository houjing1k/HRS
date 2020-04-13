package com.company;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    Timer timer2PM;
    Scheduler ()
    {
        Date date2pm = new java.util.Date();
        date2pm.setHours(14);
        date2pm.setMinutes(0);
        timer2PM.schedule(new cancelExpiredReservations(),date2pm,86400000);
    }
    class cancelExpiredReservations extends TimerTask {

        @Override
        public void run() {

        }
    }

}

