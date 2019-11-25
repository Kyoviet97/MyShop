package com.gvtechcom.myshop.Utils;

import java.util.Calendar;

public class GetTime {
    Calendar calendar = Calendar.getInstance();
    public long getCalendar() {
        long timeMillis = calendar.getTimeInMillis();
        return timeMillis;
    }
}
