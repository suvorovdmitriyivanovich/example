package com.example.examle.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateManager {

    //---------------------Singleton---------------------------
    private static DateManager dateManager;

    public static DateManager getDateManager()
    {
        if(dateManager == null)
            dateManager = new DateManager();
        return dateManager;
    }
    //---------------------------------------------------------

    public Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public String getCurrentDateString() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
    }

    public String getDateStringFormat(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
    }

    public Date getDateFromString(String s) {
        if(s == null || s.isEmpty()) {
            return null;
        }
        Calendar c = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public Date getStartDate() {
        Date dateStart = new Date();
        dateStart.setTime(315528146444l);
        return dateStart;
    }
}
